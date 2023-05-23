import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:image_gallery_saver/image_gallery_saver.dart';
import 'package:path_provider/path_provider.dart';



final List<CameraDescription> camerasList = [];

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  final cameras = await availableCameras();
  camerasList.add(cameras[0]);

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
      home: Identificacao(), // Definindo a tela inicial
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
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 8),
          child: TextFormField(
            autofocus: true,
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
            onPressed: () {},
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
          } catch (e) {
            print(e);
          }
        },
        child: const Icon(Icons.camera_alt),
      ),
    );
  }
}
