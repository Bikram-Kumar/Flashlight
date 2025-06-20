package com.bikram.flashlight;

import org.json.JSONObject;
import org.json.JSONException;
import org.apache.commons.io.IOUtils;
import java.io.IOException;

public abstract class MorseCoder {
 
    public static JSONObject codes;
 
    static {
        try {
            codes = new JSONObject(IOUtils.toString(MainActivity.res.openRawResource(R.raw.morse_codes), "utf-8"));
        } catch(IOException | JSONException e) {
         
        }
    }
    
    public static String textToSymbolic(String text) {
        if (text.equals("")) return "";
        try {
            String appendix = "";
            JSONObject symbolicCodes = codes.getJSONObject("symbolic");
            
            String[] code = text.toLowerCase().split("");
            
            for (int i = 0; i < code.length; i++) {
                
                // ((i != code.length - 1) && (code[i+1] == " ")) only checks the second condition if the first is true, so 
                // ArrayIndexOutOfBoundsException will never be thrown ;)
                if ((code[i] == " ") || (i == code.length - 1) || ((i != code.length - 1) && (code[i+1] == " "))) {
                    appendix = "";
                    
                } else {
                    appendix = "   ";
                }
                code[i] = symbolicCodes.getString(code[i]) + appendix;
            }
            return String.join("", code);
        } catch (JSONException e) {
            return "Error";
        }
    }
    
    
    public static String textToDigital(String text) {
        if (text.equals("")) return "";
        try {
            String appendix;
            JSONObject digitalCodes = codes.getJSONObject("digital");

            String[] code = text.toLowerCase().split("");
            for (int i = 0; i < code.length; i++) {
                if ((code[i] == " ") || (i == code.length - 1) || ((i != code.length - 1) && (code[i+1] == " "))) {
                    appendix = "";
                } else {
                    appendix = "000";
                }
                
                code[i] = digitalCodes.getString(code[i]) + appendix;
            }
            return String.join("", code);
        } catch (JSONException e) {
            return "Error";
        }
    }
    
    
    
}