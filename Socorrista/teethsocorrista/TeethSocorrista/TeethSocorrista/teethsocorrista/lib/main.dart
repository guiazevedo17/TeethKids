import 'dart:async';

import 'dart:io';

import 'package:camera/camera.dart';

import 'package:flutter/material.dart';
import 'package:image_gallery_saver/image_gallery_saver.dart';
import 'package:path_provider/path_provider.dart';

//import geolocator
import 'package:geolocator/geolocator.dart';

//imports do firebase
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:cloud_functions/cloud_functions.dart';
//import permission
import 'package:permission_handler/permission_handler.dart';

//Import do rating bar
import 'package:flutter_rating_bar/flutter_rating_bar.dart';

//import para http

final List<CameraDescription> camerasList = [];

class Globals{
  static UserCredential? userCredential;
  static List<String> imagesPath = [];
  static List<String> downloadURL = [];
  static String? fcmToken = '';
  static List<String> ids = [];
  static VoidCallback? onIdsUpdated;
  static double? latitude;
  static double? longitude;
  static String? name;
  static void addItem(String item) {
    ids.add(item);
    if (onIdsUpdated != null) {
      onIdsUpdated!();
    }
  }
}
Future<void> callFirestoreFunction(Uri url,Map<String, dynamic> requestBody,String functionName) async {
 
  HttpsCallable callable = FirebaseFunctions.instanceFor(region: 'southamerica-east1').httpsCallable(functionName);

  try {
    await callable.call(requestBody);
    print("mensagem enviada");
  } on FirebaseFunctionsException catch (error) {
    print('erro');
  }
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

  FirebaseMessaging.instance.onTokenRefresh
      .listen((fcmToken) {
    // TODO: If necessary send token to application server.


    final Map<String, dynamic> requestBody = {
      'fcmToken': fcmToken,
      'userId': Globals.userCredential?.user?.uid,
      'collection':'emergencies'
    };
    final uri = Uri.parse('https://southamerica-east1-teethkids-d10a1.cloudfunctions.net/setToken');
    const functionName = 'setToken';
    callFirestoreFunction(uri, requestBody,functionName);
  })
      .onError((err) {
    // Error getting token.
  });



  runApp(MyApp());

}
class PermissionScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Permissões'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text(
              'Permissão requerida não concedida',
              style: TextStyle(fontSize: 18),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              child: const Text('Conceder Permissão'),
              onPressed: () {
                openAppSettings();
              },
            ),
          ],
        ),
      ),
    );
  }
}
void requestPermissions(BuildContext context) async {
  Map<Permission, PermissionStatus> status = await [
    Permission.location,
    Permission.locationAlways,
    Permission.locationWhenInUse,

  ].request();

  if (status[Permission.location]!.isGranted) {
    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);

    Globals.latitude = position.latitude;
    Globals.longitude = position.longitude;
  } else {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => PermissionScreen()),
    );
  }
}


class MyApp extends StatefulWidget {
  static GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  void handleFirebaseMessage(BuildContext context,RemoteMessage message) {
    print("Mensagem recebida");

    if (message.data['text'] == 'accepted') {
      String id = message.data['id'];
      Globals.addItem(id);
      print("ID:${message.data['id']}");
    }
    if (message.data['text'] == 'rating') {
      MyApp.navigatorKey.currentState?.push(
        MaterialPageRoute(builder: (context) => Avaliacao(message.data['id'])),
      );
    }
  }
  @override
  void initState() {
    super.initState();
    // ...

    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      handleFirebaseMessage(context,message);
    });
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: MyApp.navigatorKey,
      title: 'Minha Aplicação',
      theme: ThemeData(
        primarySwatch: Colors.orange
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
          backgroundColor: const Color.fromARGB(255, 255, 140, 0),
        ),
        body: MyCustomForm(),
      ),
    );
  }
}
class Avaliacao extends StatefulWidget {
  final String data;
  Avaliacao(this.data);

  @override
  _AvaliacaoState createState() => _AvaliacaoState();
}

class _AvaliacaoState extends State<Avaliacao> {
  double rating = 0;
  TextEditingController messageController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final id = widget.data;
    return Scaffold(
      appBar: AppBar(
        title: const Text('Avaliação',
        style: TextStyle(color :Colors.white),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Avalie o Atendimento',
              style: TextStyle(fontSize: 18),
            ),
            const SizedBox(height: 16),
            RatingBar.builder(
              initialRating: rating,
              minRating: 1,
              direction: Axis.horizontal,
              allowHalfRating: true,
              itemCount: 5,
              itemSize: 50,
              unratedColor: Colors.grey,
              itemBuilder: (context, _) => const Icon(
                Icons.star,
                color: Colors.amber,
              ),
              onRatingUpdate: (newRating) {
                setState(() {
                  rating = newRating;
                });
              },
            ),
            const SizedBox(height: 16),
            const Text(
              'Deixe uma mensagem:',
              style: TextStyle(fontSize: 18),
            ),
            const SizedBox(height: 8),
            TextField(
              controller: messageController,
              decoration: const InputDecoration(
                hintText: 'Digite sua mensagem (opcional)',
                border: OutlineInputBorder(),
              ),
              maxLines: 3,
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: ()  async{
                 final Map<String, dynamic> requestBody = {
                   'rating': rating,
                   'message': messageController.text,
                   'id' : id,
                   'name': Globals.name,
                 };
                 final uri = Uri.parse('https://southamerica-east1-teethkids-d10a1.cloudfunctions.net/sendRating');
                 const functionName = 'sendRating';
               await callFirestoreFunction(uri, requestBody,functionName);
              },
              child: const Text('Enviar Avaliação',
              style: TextStyle(
                  color: Colors.white
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}


class AbrirChamado extends StatefulWidget {
  @override
  _AbrirChamadoState createState() => _AbrirChamadoState();
}

class _AbrirChamadoState extends  State<AbrirChamado> {

  @override
  void initState() {
    super.initState();
    requestPermissions(context);
  }

  Future<bool> checkDocument() async {
    final db = FirebaseFirestore.instance;
    final docRef = db.collection('emergencies').doc(
        Globals.userCredential?.user?.uid);
    final docSnapshot = await docRef.get();
    var exists = docSnapshot.exists;
    return exists;
  }

  Future<void> iniciarColecao() async {
    final db = FirebaseFirestore.instance;
    final id = Globals.userCredential?.user?.uid;
    var user = <String, dynamic>{
      "userId": id,
      "fcmToken": Globals.fcmToken,
      "state": 'invalid'
    };

    db.collection('emergencies').doc(id).set(user).whenComplete(() =>
        print('Documento Iniciado com sucesso'));

  }

  @override
  Widget build(BuildContext context) {
    const appTitle = 'TeethKids - Socorrista';

    void iniciarIdentificacao() {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => Identificacao()),
      );
    }


    return MaterialApp(
      title: appTitle,
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text(appTitle),
          backgroundColor: const Color.fromARGB(255, 255, 140, 0),
        ),
        body: Builder(
          builder: (BuildContext builderContext) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  ElevatedButton(
                    onPressed: () async {
                      bool docExists = await checkDocument();
                      if (!docExists) {
                        await iniciarColecao();
                        iniciarIdentificacao();
                      } else {
                        ScaffoldMessenger.of(builderContext).showSnackBar(
                          const SnackBar(
                            content: Text(
                                'Você já possui um socorro em aberto'),
                          ),
                        );
                      }
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color.fromARGB(255, 255, 140, 0),
                    ),
                    child: const Text('Iniciar Chamado'),
                  ),
                  const SizedBox(height: 16), // Espaçamento entre os botões
                  ElevatedButton(
                    onPressed: () async {
                      bool docExistis = await checkDocument();
                      if(!docExistis){
                        ScaffoldMessenger.of(builderContext).showSnackBar(
                          const SnackBar(
                            content: Text(
                                'Você não possui socorros em aberto'),
                          ),
                        );
                      }else{
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => EmergenciesList()),
                        );
                      }

                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color.fromARGB(255, 255, 140, 0),
                    ),
                    child: const Text('Vizualizar Socorro'),
                  ),
                ],
              ),
            );
          },
        ),
      ),
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

class MyCustomForm extends StatefulWidget {
  @override
  _MyCustomFormState createState() => _MyCustomFormState();
}
class _MyCustomFormState extends State<MyCustomForm> {
  double uploadProgress = 0.0;

  Future<void> uploadImage(List<String> imagePaths) async {
    try {
      final storage = FirebaseStorage.instance;
      int totalImages = imagePaths.length;
      int uploadedImages = 0;

      for (String path in imagePaths) {
        File imageFile = File(path);
        final storageRef = storage.ref().child('${DateTime.now().toIso8601String()}.jpg');
        final uploadTask = storageRef.putFile(imageFile);

        uploadTask.snapshotEvents.listen((TaskSnapshot snapshot) {
          uploadedImages++;
          uploadProgress = uploadedImages / totalImages * 100;

          // Atualiza a tela para refletir o novo valor do progresso
          setState(() {});
        });

        await uploadTask;
        final downloadURL = await storageRef.getDownloadURL();
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
    Timestamp timeStamp = Timestamp.now();

    if (id != null) {
      Map<String, dynamic> update = {
        'name': nomeController,
        'phone': telefoneController,
        'images': Globals.downloadURL,
        'data' : timeStamp,
        'latitude' : Globals.latitude,
        'longitude':Globals.longitude
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
    return SingleChildScrollView(
        child: Column(
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
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
            child: const Text('Tirar Foto'),
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
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
            child: const Text('Tirar Foto'),
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
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => TakePictureScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
            child: const Text('Tirar Foto'),
          ),
        ),
        Container(
          alignment: Alignment.bottomCenter,
          margin: const EdgeInsets.only(bottom: 40, left: 10, right: 10, top: 89),
          child: Column(
          children:[
            Container(
              alignment: Alignment.center,
              margin: const EdgeInsets.symmetric(horizontal: 40.0),
              height: 16.0,
              decoration: BoxDecoration(
                color: Colors.grey[300],
                borderRadius: BorderRadius.circular(8.0),
              ),
              child: LinearProgressIndicator(
                value: uploadProgress / 100,
                backgroundColor: Colors.transparent,
                valueColor: const AlwaysStoppedAnimation<Color>(Colors.orange),
              ),
            ),
            ElevatedButton(
            onPressed: () async {
              if(Globals.imagesPath.length < 3 || nomeController.text.isEmpty || telefoneController.text.isEmpty ){
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                    content: Text('Prencha todos os dados'),
                  ),
                );
              }else {
                Globals.name = nomeController.text;
                await uploadImage(Globals.imagesPath);
                await updateColecao(nomeController.text, telefoneController.text);

                Navigator.push(context,MaterialPageRoute(builder: (context) => EmergenciesList()) );

              }

            },
            style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0)),
            child: const Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [Text('Solicitar')],
            ),
          ),
          ],
        ),
        ),
      ]
    ),
    );
  }
}

class EmergenciesList extends StatefulWidget{
  @override
  _EmergenciesListState createState() => _EmergenciesListState();
}

class _EmergenciesListState extends State <EmergenciesList> {
  Stream<List<String>>? idsStream;
  List<String> items = [];

  @override
  void initState() {

    super.initState();
    updateItems();
    listenToChanges();
  }

  void listenToChanges() {
    Globals.onIdsUpdated = updateItems;
  }

  void updateItems() {
    setState(() {
      items = List<String>.from(Globals.ids);
    });
  }
  @override
  void dispose() {
    Globals.onIdsUpdated = null;
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (items.isEmpty) {
      return MaterialApp(
        title: 'Socorro Aberto',
        home: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: const Text('Socorro Aberto'),
            backgroundColor: const Color.fromARGB(255, 255, 140, 0),
            leading: IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () {
                Navigator.push(context,MaterialPageRoute(builder: (context) => AbrirChamado()) );

              },
            ),
          ),
          body: const Center(
            child: Text('Aguardando Dentistas'),
          ),
        ),
      );
    } else {
      return MaterialApp(
        title: 'Socorro Aberto',
        home: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            title: const Text('Socorro Aberto'),
            backgroundColor: const Color.fromARGB(255, 255, 140, 0),
            leading: IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () {
                Navigator.push(context,MaterialPageRoute(builder: (context) => AbrirChamado()) );
              },
            ),
          ),
          body: ListView.builder(
            itemCount: items.length,
            itemBuilder: (context, index) {
              return InkWell(
                onTap: () async {
                  String idDentist = items[index];
                  Data data = await getData(idDentist);
                  Navigator.push(context, MaterialPageRoute(builder: (context) => DetalhesScreen(data.name,data.url,index,data.fcmToken),),);
                },
                child: ListTile(
                  title: Text("Dentista:${index+1}"),
                ),
              );
            },
          ),
        ),
      );
    }
  }
}
class Data {
  final String name;
  final String url;
  final String fcmToken;

  Data({required this.name , required this.url,required this.fcmToken});
}

Future<Data> getData(String id) async {
  final db = FirebaseFirestore.instance;

  DocumentSnapshot<Map<String, dynamic>> snapshot = await db.collection('dentists').doc(id).get();
  // print('-----------------------');
  // print("Debug \n");
  // print('String id:${id}');
  // print('SnapshotData:${snapshot.data()}');
  // print('\n-----------------------');

  String fcmToken = snapshot.data()!['fcmToken'];
  String name = snapshot.data()!['name'];
  // String phone = snapshot.data()!['string2'];
  String url = snapshot.data()!['picture'];

  return Data(name: name , url: url , fcmToken:fcmToken);
}
class DetalhesScreen extends StatelessWidget {
  final String string1;
  final String imageUrl;
  final int index;
  final String fcmToken;
  DetalhesScreen(this.string1,this.imageUrl,this.index,this.fcmToken);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Detalhes'),
       backgroundColor: const Color.fromARGB(255, 255, 140, 0),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Nome: $string1'),
            const SizedBox(height: 16),
            Image.network(
              imageUrl,
              width: 300, // Define a largura da imagem
              height: 300, // Define a altura da imagem
              fit: BoxFit.contain, // Define como a imagem será ajustada ao Container
            ),
            const SizedBox(height: 16),
            const Text('Avaliação : 9/10'),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () {
                print("fcmToken: ${fcmToken}");
                final Map<String, dynamic> requestBody = {
                  'fcmToken':fcmToken ,
                  'messageType': 'acceptedDentist',
                  'uid': Globals.userCredential?.user?.uid,
                };
                final uri = Uri.parse('https://southamerica-east1-teethkids-d10a1.cloudfunctions.net/sendMessageToDentist');
                const functionName = 'sendMessageToDentist';
                callFirestoreFunction(uri, requestBody, functionName);

                Navigator.pop(context);
                Navigator.pop(context);
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0),
              ),
              child: const Text('Selecionar Dentista'),
            ),
            ElevatedButton(
              onPressed: (){
                Globals.ids.removeAt(index);
                Globals.onIdsUpdated!();
                Navigator.pop(context);
            },
              style: ElevatedButton.styleFrom(
                backgroundColor: const Color.fromARGB(255, 255, 140, 0),
              ),
              child: const Text('Excluir Dentista'),
            ),
          ],
        ),
      ),
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

  Future<void> saveImageToGallery(BuildContext context,String imagePath) async {
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
      appBar: AppBar(
          title: const Text('Tirar uma foto'),
          backgroundColor: const Color.fromARGB(255, 255, 140, 0),
      ),
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

            await saveImageToGallery(context,image.path);

            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => DisplayImageScreen(imagePath: image.path),
              ),
            );

          } catch (e) {
            print(e);
          }
        },
        child: const Icon(Icons.camera_alt),
      ),
    );
  }
}



class DisplayImageScreen extends StatelessWidget {
  final String imagePath;
  bool isButtonDisabled = false;

  DisplayImageScreen({required this.imagePath});


  Future<void> deleteImage(String imagePath) async {
    try {
      final file = File(imagePath);
      if (await file.exists()) {
        await file.delete();
      }
    } catch (e) {
      print('Erro ao excluir a imagem: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Imagem Capturada'),
        backgroundColor: const Color.fromARGB(255, 255, 140, 0),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.file(
              File(imagePath),
              fit: BoxFit.contain,
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: isButtonDisabled ? null : () {
                isButtonDisabled = true;
                Globals.imagesPath.add(imagePath);

                Navigator.pop(context);
                Navigator.pop(context);

              },
              child: const Text('Usar foto'),
            ),
            const SizedBox(height: 8),
            OutlinedButton(
              onPressed: () {
                deleteImage(imagePath);
                Navigator.pop(context);

              },
              child: const Text('Tirar outra foto'),
            ),
          ],
        ),
      ),
    );
  }
}