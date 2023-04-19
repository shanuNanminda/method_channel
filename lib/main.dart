import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const smsPlatform = MethodChannel('samples.flutter.dev/sms');
  static const launcherPlatform = MethodChannel('samples.flutter.dev/launcher');
  int _counter = 0;

  void _incrementCounter() async {
    int level = await smsPlatform.invokeMethod(
      'sms',
      {
        'mobile': '+91${numberController.text.trim()}',
        'message': messageController.text,
      },
    );
    // print(level);
  }

  void launcApp(){
    launcherPlatform.invokeMethod('launcher',{'destination':'com.instagram.android'});
  }

  TextEditingController messageController = TextEditingController();
  TextEditingController numberController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                controller: messageController,
                decoration: const InputDecoration(
                    border: OutlineInputBorder(), label: Text('message')),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                keyboardType: TextInputType.number,
                controller: numberController,
                decoration: const InputDecoration(
                    border: OutlineInputBorder(), label: Text('number')),
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          FloatingActionButton(
            onPressed: _incrementCounter,
            tooltip: 'Increment',
            child: const Icon(Icons.add),
          ),
          FloatingActionButton(onPressed: (){
           launcApp(); 
          })
        ],
      ),
    );
  }
}
