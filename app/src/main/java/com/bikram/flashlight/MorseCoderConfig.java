package com.bikram.flashlight;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;

import com.google.android.material.slider.Slider;



public class MorseCoderConfig extends DialogFragment {
    static String message = "SOS";
    static int frequency = 1200;
    static int speed = 50;
    static boolean isTransmitting = false;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        View morseCoderConfigLayout = getLayoutInflater().inflate(R.layout.morse_coder_config_layout, null);
        
        EditText morseText = morseCoderConfigLayout.findViewById(R.id.morseText);
        TextView morseSymbolicText = morseCoderConfigLayout.findViewById(R.id.morseSymbolicText);
        
        Slider speedSlider = morseCoderConfigLayout.findViewById(R.id.morseSpeedSlider);
        Slider frequencySlider = morseCoderConfigLayout.findViewById(R.id.morseFrequencySlider);
        
        Button toggleTransmitBtn = morseCoderConfigLayout.findViewById(R.id.toggleTransmitBtn);
        
        morseText.setText(message);
        morseSymbolicText.setText(MorseCoder.textToSymbolic(message));
        
        morseText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}  
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} 
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
                message = s.toString();
                morseSymbolicText.setText(MorseCoder.textToSymbolic(message));
            }  
        });
        
        speedSlider.setValue(speed);
        speedSlider.addOnChangeListener((slider, value, fromUser) -> {
                speed = (int)value;
        });
        
        frequencySlider.setValue(frequency);
        frequencySlider.addOnChangeListener((slider, value, fromUser) -> {
                frequency = (int)value;
        });
        
        
        toggleTransmitBtn.setOnClickListener((View v) -> {
            if (isTransmitting) {
                MorseController.stopTransmit();
                toggleTransmitBtn.setText("Start");
            } else {
                MorseController.startTransmit(MorseCoder.textToDigital(message));
                toggleTransmitBtn.setText("Stop");
            }
            isTransmitting = !isTransmitting;
        });
        
        
        builder.setView(morseCoderConfigLayout);
        
        return builder.create();
        
    }
    
    
    static int getPeriod() {
        return (300 + 17 * (100-speed)); // 0-100 -> 2000-300
    }
    
    
}