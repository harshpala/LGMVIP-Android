package com.example.lgmvip_facedetection;

import android.app.Application;
import com.google.firebase.FirebaseApp;

import java.util.function.Function;

public class LCOFaceDetection extends Application {
    public final static String RESULT_TEXT = "RESULT_TEXT";
    public final static String RESULT_DIALOG = "RESULT_DIALOG";



    // initializing our firebase
    @Override
    public void onCreate()
    {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
