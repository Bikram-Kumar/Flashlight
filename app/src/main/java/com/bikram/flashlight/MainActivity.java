package com.bikram.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.support.v7.app.AlertDialog;


import android.os.Bundle;

import android.widget.ToggleButton;
import android.widget.CompoundButton;


public class MainActivity extends AppCompatActivity {
    
    private CameraManager mCameraManager;
    private String mCameraId;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        
        if (!isFlashAvailable) {
            showNoFlashError();
        }
        
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
            
        } catch(CameraAccessException e) {
            e.printStackTrace();
        }
        
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.onCheckedChangeListener() {
            
            @Override 
            public void onCheckedChange(CompoundButton buttonView, boolean isChecked) {
                switchFlashLight(isChecked);
            }
            
        });
    }
    
    public void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this).create;
        alert.setTitle("Alert");
        alert.setMessage("Sorry, your device has no Flashlight. Please use this app on a device with Flashlight.");
        
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Exit", new DialogInterface.onClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        
        alert.show();
    }
    
    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch(CameraAccessException e) {
            e.printStackTrace();
        }
    }
    
    
}