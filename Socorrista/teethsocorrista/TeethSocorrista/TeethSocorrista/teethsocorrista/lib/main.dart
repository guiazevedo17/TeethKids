import 'dart:async';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:image_gallery_saver/image_gallery_saver.dart';
import 'package:path_provider/path_provider.dart';


//imports do firebase
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';

//import do firebase auth
import 'package:firebase_auth/firebase_auth.dart';

//import do firebase storage
import 'package:firebase_storage/firebase_storage.dart';

final List<CameraDescription> camerasList = [];

class Globals{
  static UserCredential? userCredential;
  static List<String> imagesPath = [];
  static List<String> downloadURL = [];
  static String? fcmToken = '';
}



void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );

  final cameras = await availableCameras();
  camerasList.add(cameras[0]);

  Globals.userCredential = await FirebaseAuth.instance.signInAnonymously();
  Globals.fcmToken = await FirebaseMessaging.instance.getToken();

  FirebaseMessaging.onMessage.listen((RemoteMessage message) {
    print('Got a message whilst in the foreground!');
    print('Message data: ${message.data}');

    if (message.notification != null) {
      print('Message also contained a notification: ${message.notification}');
    }
  });


  runApp(const MyApp());

}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Minha Aplicação',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: AbrirChamado(), // Definindo a tela inicial
    );
  }
}

class Identificacao extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    const appTitle = 'Solicitar socorro imediato';
    return MaterialApp(
      title: appTitle,
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text(
            appTitle,
          ),
          backgroundColor: Color.fromARGB(255, 255, 140, 0),
        ),
        body: MyCustomForm(),
      ),
    );
  }
}
class AbrirChamado extends StatelessWidget{

  Future<bool> checkDocument() async {
    final db = FirebaseFirestore.instance;
    final docRef = db.collection('emergencies').doc(Globals.userCredential?.user?.uid);
    final docSnapshot = await docRef.get();
    final exists = docSnapshot.exists;
    return exists;
  }

  Future<void> iniciarColecao() async{
   final db = FirebaseFirestore.instance;
   final id = Globals.userCredential?.user?.uid; 
   final user = <String, dynamic>{
     "userId": Globals.userCredential?.user?.uid,
     "fcmToken": Globals.fcmToken,
     "state": 'invalid'
   };

   db.collection('emergencies').doc(id).set(user).whenComplete(() =>  print('Documento Iniciado com sucesso') );

  }

  @override
  Widget build(BuildContext context){
    const appTittle = 'TeethKids - Socorrista';

    void IniciarIdentificacao() {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => Identificacao()),
      );
    }
    return MaterialApp(
        title: appTittle,
        home:Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: const Text(appTittle),
            backgroundColor: Color.fromARGB(255, 255, 140, 0),
          ),
          body: Center(
            child: ElevatedButton(
              onPressed:() async{

                bool docExists = await checkDocument();
                if(!docExists){
                  await iniciarColecao();
                }
                  IniciarIdentificacao();
              },
              child: Text('Iniciar Chamado'),
              style: ElevatedButton.styleFrom(
                  backgroundColor: Color.fromARGB(255, 255, 140, 0)
              ),

            ),
          ),
        )
    );
  }
}

class FirstRoute extends StatelessWidget {
  const FirstRoute({super.key});
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: ElevatedButton(
          child: const Text('Open route'),
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => TakePictureScreen()),
            );
          },
        ),
      ),
    );
  }
}

class MyCustomForm extends StatelessWidget {


  Future<void> uploadImage(List<String> imagePaths) async {
    try {

      final storage = FirebaseStorage.instance;

      for (String path in imagePaths){
        File imageFile = File(path);
        final storageRef = storage.ref().child('${DateTime.now().toIso8601String()}.jpg');
        await storageRef.putFile(imageFile);
        final downloadURL =  await storageRef.getDownloadURL();
        Globals.downloadURL.add(downloadURL);
      }
      Globals.imagesPath.clear();
      print('Imagem enviada com sucesso');
    } catch (e) {

      print('Error $e');
    }
  }
  Future<void> updateColecao(String nomeController, String telefoneController) async {
    final db = FirebaseFirestore.instance;
    final id = Globals.userCredential?.user?.uid;

    if (id != null) {
      Map<String, dynamic> update = {
        'name': nomeController,
        'phone': telefoneController,
        'images': Globals.downloadURL,
      };

      await db.collection('emergencies').doc(id).set(update, SetOptions(merge: true));
      await db.collection('emergencies').doc(id).update({'state':'new'});
    } else {
      print('ID do usuário é nulo');
    }
  }
  @override
  Widget build(BuildContext context) {
    final nomeController = TextEditingController();
    final telefoneController = TextEditingController();
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
          child: TextFormField(
            autofocus: true,
            controller: nomeController,
            decoration: const InputDecoration(
              labelText: 'Nome Solicitante',
              labelStyle: TextStyle(color: Color.fromARGB(255, 255, 140, 0)),
              border: UnderlineInputBorder(),
              focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(
                color: Color.fromARGB(255, 255, 140, 0),
              )),
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
          child: TextFormField(
            controller:telefoneController,
            decoration: const InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Telefone',
              labelStyle: TextStyle(color: Color.fromARGB(255, 255, 140, 0)),
              focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(
                color: Color.fromARGB(255, 255, 140, 0),
              )),
            ),
          ),
        ),
        const Padding(
          padding: EdgeInsets.symmetric(horizontal:90, vertical: 8),
          child: Text('Foto da Boca/Região acidentada'),
        ),
        Container(
          alignment: Alignment.center,
          margin: const EdgeInsets.only(bottom: 40, top: 2),
          child: ElevatedButton(
            child: const Text('Tirar Foto'),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
          ),
        ),
        const Padding(
          padding: EdgeInsets.symmetric(horizontal: 90, vertical: 0),
          child: Text('Foto do Documento do Solicitante'),
        ),
        Container(
          alignment: Alignment.center,
          margin: const EdgeInsets.only(bottom: 40, top: 2),
          child: ElevatedButton(
            child: const Text('Tirar Foto'),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
          ),
        ),
        const Padding(
          padding: EdgeInsets.symmetric(horizontal: 135, vertical: 0),
          child: Text('Foto com a criança'),
        ),
        Container(
          alignment: Alignment.center,
          margin: const EdgeInsets.only(bottom: 40, top: 2),
          child: ElevatedButton(
            child: const Text('Tirar Foto'),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
          ),
        ),
        Container(
          alignment: Alignment.bottomCenter,
          margin:
              const EdgeInsets.only(bottom: 40, left: 10, right: 10, top: 89),
          child: ElevatedButton(
            onPressed: () async {
              if(Globals.imagesPath.length == 3 ){
                await uploadImage(Globals.imagesPath);
                await updateColecao(nomeController.text, telefoneController.text);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                    content: Text('Socorro Solicitado com Sucesso.'),
                  ),
                );
              }else {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                    content: Text('Você precisa tirar todas as fotos antes de solicitar.'),
                  ),
                );
              }

            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
            child: const Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [Text('Solicitar')],
            ),
          ),
        ),
      ],
    );
  }
}

class TelaCamera extends StatefulWidget {
  const TelaCamera({super.key});

  @override
  // ignore: library_private_types_in_public_api, no_logic_in_create_state
  TakePictureScreenState createState() => TakePictureScreenState();
}

class TakePictureScreen extends StatefulWidget {
  @override
  TakePictureScreenState createState() => TakePictureScreenState();
}

class TakePictureScreenState extends State<TakePictureScreen> {
  late CameraController _controller;
  late Future<void> _initializeControllerFuture;

  @override
  void initState() {
    super.initState();
    _controller = CameraController(
      camerasList[0],
      ResolutionPreset.medium,
    );
    _initializeControllerFuture = _controller.initialize();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Future<void> saveImageToGallery(String imagePath) async {
    final appDirectory = await getTemporaryDirectory();
    final fileName = DateTime.now().toIso8601String();
    final path = '${appDirectory.path}/$fileName.jpg';

    final savedFile = await File(imagePath).copy(path);

    await ImageGallerySaver.saveFile(savedFile.path);

    // ignore: use_build_context_synchronously
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Imagem salva na galeria')),
    );
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Tirar uma foto')),
      body: FutureBuilder<void>(
        future: _initializeControllerFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            return CameraPreview(_controller);
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          try {
            await _initializeControllerFuture;
            final image = await _controller.takePicture();

            if (!mounted) return;

            await saveImageToGallery(image.path);
            Globals.imagesPath.add(image.path);

          } catch (e) {
            print(e);
          }
        },
        child: const Icon(Icons.camera_alt),
      ),
    );
  }
}
