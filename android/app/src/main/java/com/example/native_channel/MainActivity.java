package com.example.native_channel;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor()
                .getBinaryMessenger(), "samples.flutter.dev/sms")
                .setMethodCallHandler(
                        (call, result) ->
                        {
                            Map<String, Object> arguments = call.arguments();
                            String mobile = (String) arguments.get("mobile");
                            String message = (String) arguments.get("message");
                            sendSMS(mobile, message);
                        }
                );
        new MethodChannel(flutterEngine.getDartExecutor()
                .getBinaryMessenger(), "samples.flutter.dev/launcher")
                .setMethodCallHandler(
                        (call, result) ->
                        {
                            Map arguments = call.arguments();
                            String destination = (String) arguments.get("destination");
                            System.out.println("successfully recieved channel with arg" + destination);
                            launchApp(destination);
                        }
                );


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new EventChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), "com.yourcompany.eventchannelsample/stream").setStreamHandler();

    }

    private void sendSMS(String no, String msg) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            pi = PendingIntent.getActivity
                    (getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            // pi = PendingIntent.getActivity
            //         (getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
            pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        }

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(no, null, msg, pi, null);
    }

    private void launchApp(String destination) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(destination);
        System.out.println(launchIntent);
        if (launchIntent != null) {
            System.out.println("sending intent to start activity");

            try {
                startActivity(launchIntent);
            } catch (Exception e) {
                System.out.println("encoutnered exception " + e);
            }
        }
    }

    private void 




}
