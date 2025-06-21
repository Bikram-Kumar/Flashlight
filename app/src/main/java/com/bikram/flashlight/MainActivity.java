package com.bikram.flashlight;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.app.AlertDialog;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.widget.ToggleButton;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.graphics.drawable.Drawable;




public class MainActivity extends AppCompatActivity {

    Handler handler;

    private CameraManager mCameraManager;
    private String mCameraId;
    ToggleButton toggleButton;
    ImageView flashlightImage;

    public static Resources res;
    Configuration config;
    ConstraintLayout.LayoutParams lp;
    Drawable img;

    private double windowWidth, windowHeight, windowAsp;
    private double imgWidth, imgHeight, imgAsp;

    MorseCoderConfig morseCoderConfigDialog = new MorseCoderConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            showNoFlashError();
        }
        setCameraManagerAndId();


        res = getResources();
        img = res.getDrawable(R.drawable.flashlight_off, null);

        toggleButton = findViewById(R.id.toggleButton);
        flashlightImage = findViewById(R.id.flashlightImage);

        fitImageToScreen();

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchFlashLight(isChecked);
            }

        });
        
        handler = new Handler(Looper.getMainLooper());
        
        // MorseController needs a reference for switching the flashlight
        MorseController.mainActivity = this;
        
    }

    public void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Alert");
        alert.setMessage("Sorry, your device has no Flashlight. Please use this app on a device with Flashlight.");

        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.show();
    }

    private void setCameraManagerAndId() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];

        } catch(CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void switchFlashLight(boolean status) {
        if (status) {
            flashlightImage.setImageResource(R.drawable.flashlight_on);
        } else {
            flashlightImage.setImageResource(R.drawable.flashlight_off);
        }


        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch(CameraAccessException e) {
            e.printStackTrace();
        }

    }


    public void showMorseCoderConfigDialog(View view) {
        morseCoderConfigDialog.show(getSupportFragmentManager(), "morseCoderConfigDialog");
    }




    public void fitImageToScreen() {
        config = res.getConfiguration();

        windowWidth = config.screenWidthDp;
        windowHeight = config.screenHeightDp;
        windowAsp = windowWidth / windowHeight;

        imgWidth = img.getIntrinsicWidth();
        imgHeight = img.getIntrinsicHeight();
        imgAsp = imgWidth / imgHeight;

        // flashlightImage is the ImageView and img the Drawable

        lp = (ConstraintLayout.LayoutParams) flashlightImage.getLayoutParams();


        if (imgAsp < windowAsp) {
            lp.width = (int) dpToPx(windowWidth);
            lp.height = (int) dpToPx(windowWidth / imgAsp);
        } else {
            lp.width = (int) dpToPx(windowHeight * imgAsp);
            lp.height = (int) dpToPx(windowHeight);
        }
        flashlightImage.setLayoutParams(lp);
    }











    private double dpToPx(double dp) {
        return dp * res.getDisplayMetrics().density;
    }


}