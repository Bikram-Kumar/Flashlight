package com.bikram.flashlight;


public abstract class MorseController {
    static MainActivity mainActivity;
    static String code;
    static int counter;
    static Runnable runnable;
    
    static {
        runnable = new Runnable () {
            @Override
            public void run() {
                if (counter >= MorseController.code.length()) {
                    mainActivity.switchFlashLight(false);
                    return;
                }
                
                mainActivity.switchFlashLight(MorseController.code.charAt(counter) == '1');
                counter++;
                
                mainActivity.handler.postDelayed(this, 1000);
            }
        };
    }

    static void startTransmit(String code) {
        MorseController.code = code;
        // clear old thread
        mainActivity.handler.removeCallbacks(runnable);
        
        counter = 0;
        mainActivity.handler.postDelayed(runnable, 100);
        
    }
    
}