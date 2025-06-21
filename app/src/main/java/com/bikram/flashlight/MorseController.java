package com.bikram.flashlight;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MorseController {
    static MainActivity mainActivity;
    static String code;
    static int counter;
    static Runnable runnable;
    static Timer timer;
    static long period = 1000;

    static {
        runnable = new Runnable () {
            @Override
            public void run() {
                if (MorseController.counter >= MorseController.code.length()) {
                    mainActivity.switchFlashLight(false);
                    return;
                }
                
                mainActivity.switchFlashLight(MorseController.code.charAt(MorseController.counter) == '1');
                MorseController.counter++;
                
                mainActivity.handler.postDelayed(this, MorseController.period);
            }
        };
    }

    static void startTransmit(String code) {
        MorseController.code = code;
        // clear old thread
        mainActivity.handler.removeCallbacks(runnable);
        
        if(timer != null) timer.cancel();
        
        timer = new Timer();
        
        counter = 0;
        mainActivity.handler.postDelayed(runnable, 100);
        timer.schedule(new AudioTask(), 100);
        
    }
    
}

class AudioTask extends TimerTask {
    @Override
    public void run() {
        if (MorseController.counter >= MorseController.code.length()) {
            AudioHandler.stop();
            MorseController.timer.cancel();
            return;
        }
        
        if (MorseController.code.charAt(MorseController.counter) == '1') {
            AudioHandler.play();
        } else {
            AudioHandler.stop();
        }
        
        MorseController.timer.schedule(new AudioTask(), MorseController.period);
    }
};