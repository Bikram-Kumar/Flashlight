package com.bikram.flashlight;

import android.view.View;
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
        
        builder.setView(morseCoderConfigLayout);
       
       
        
            try {
                
        builder.setPositiveButton(MorseCoder.textToDigital("a"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               //
            }
        });
        
        builder.setNegativeButton(MorseCoder.textToSymbolic("bx"), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
            } catch(Exception e) {
                
            }
        
        return builder.create();
        
    }
}