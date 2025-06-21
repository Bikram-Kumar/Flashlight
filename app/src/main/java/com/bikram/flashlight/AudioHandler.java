package com.bikram.flashlight;

import android.media.AudioTrack;
import android.media.AudioManager;
import android.media.AudioFormat;

public abstract class AudioHandler {
    static AudioTrack audioTrack;
    static int sampleRate = 44100;
    static int buffLength = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

    static Thread thread;
    static boolean isPlaying = false;
    
    static {
        audioTrack = new AudioTrack(AudioManager.MODE_NORMAL, sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffLength, AudioTrack.MODE_STREAM);
    }
    
    static void init() {
        if (audioTrack == null) {
            audioTrack = new AudioTrack(AudioManager.MODE_NORMAL, sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buffLength, AudioTrack.MODE_STREAM);
        }
    }
    
    static void play() {
        if(isPlaying) return;
        (new Thread(()->{
            audioTrack.play();
            isPlaying = true;
            generate();
        })).start();
    }
    
    static void generate() {
        short[] frame_out = new short[buffLength];
        int amplitude = (int) (32767);
        int frequency = 440;
        double twopi = 2.0 * Math.PI;
        double phase = 0.0;
        while (isPlaying) {
            for (int i = 0; i < buffLength; i++) {
                frame_out[i] = (short) (amplitude * Math.sin(phase));
                phase += twopi * frequency / sampleRate;
                if (phase > twopi) {
                    phase -= twopi;
                }
            }
            audioTrack.write(frame_out, 0, buffLength);
        }
    }
    
    static void stop() {
        if (!isPlaying) return;
        isPlaying = false;
        audioTrack.pause();
    }
    
    
}

