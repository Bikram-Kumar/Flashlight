package com.bikram.flashlight;

import java.util.Timer;
import java.util.TimerTask;

public abstract class MorseController {
    static MainActivity mainActivity;
    static String code;
    static int counter, counterAud;
    static Runnable runnable;
    static Timer timer;

    static {
        runnable = new Runnable () {
            @Override
            public void run() {
                if (MorseController.counter >= MorseController.code.length()) {
                    MorseController.stopTransmit();
                    return;
                }
                
                mainActivity.switchFlashLight(MorseController.code.charAt(MorseController.counter) == '1');
                MorseController.counter++;
                
                mainActivity.handler.postDelayed(this, MorseCoderConfig.getPeriod());
            }
        };
    }

    static void startTransmit(String code) {
        
        mainActivity.handler.removeCallbacks(runnable);
        if(timer != null) timer.cancel();
        
        MorseController.code = code;
        timer = new Timer();
        
        counter = 0; counterAud = 0;
        mainActivity.handler.postDelayed(runnable, 10);
        timer.schedule(new AudioTask(), 10);
        
        MorseCoderConfig.isTransmitting = true;
        if (MorseCoderConfig.dialogOpen) MorseCoderConfig.toggleTransmitBtn.setText("Stop");
    }
    
    static void stopTransmit() {
        mainActivity.handler.removeCallbacks(runnable);
        if(timer != null) timer.cancel();
        AudioHandler.stop();
        mainActivity.switchFlashLight(false);
        
        MorseCoderConfig.isTransmitting = false;
        if (MorseCoderConfig.dialogOpen) MorseCoderConfig.toggleTransmitBtn.setText("Start");
    }
    
}

class AudioTask extends TimerTask {
    @Override
    public void run() {
        if (MorseController.counterAud >= MorseController.code.length()) {
            MorseController.stopTransmit();
            return;
        }
        
        if (MorseController.code.charAt(MorseController.counterAud) == '1') {
            AudioHandler.play();
        } else {
            AudioHandler.stop();
        }
        
        MorseController.counterAud++;
        MorseController.timer.schedule(new AudioTask(), MorseCoderConfig.getPeriod());
    }
};