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

public class MorseCoderConfig extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        View morseCoderConfigLayout = getLayoutInflater().inflate(R.layout.morse_coder_config_layout, null);
        
        EditText morseText = morseCoderConfigLayout.findViewById(R.id.morseText);
        TextView morseDigitalText = morseCoderConfigLayout.findViewById(R.id.morseDigitalText);
        TextView morseSymbolicText = morseCoderConfigLayout.findViewById(R.id.morseSymbolicText);
        
        morseText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}  
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} 
            public void onTextChanged(CharSequence s, int start, int before, int count) {  
                morseDigitalText.setText(MorseCoder.textToDigital(s.toString()));
                morseSymbolicText.setText(MorseCoder.textToSymbolic(s.toString()));
            }  
        });
        
        
        ((Button)morseCoderConfigLayout.findViewById(R.id.startMsgBtn)).setOnClickListener((View v) -> {
                MorseController.startTransmit(MorseCoder.textToDigital(morseText.getText().toString()));
        });
        
        builder.setView(morseCoderConfigLayout);
       
        builder.setPositiveButton(MorseCoder.textToDigital("a"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        
        builder.setNegativeButton(MorseCoder.textToSymbolic("bx"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
        

        return builder.create();
        
    }
}