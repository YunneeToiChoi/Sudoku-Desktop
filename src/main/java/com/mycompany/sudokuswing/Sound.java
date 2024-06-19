
package com.mycompany.sudokuswing;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JSlider;

public class Sound {
    
    Clip clip;
    float previousVolume = 0;
    float currentVolume = -17;
    FloatControl fc;
    boolean mute = false;
    
    public void setFile(String path){
        try {
            File file = new File(path);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
    public void play(){
        
        fc.setValue(currentVolume);
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
    
    public void volumeUp(){
        currentVolume += 1.0f;
        if (currentVolume > 6.0f) {
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }
    
    public void volumeDown(){
        currentVolume -= 1.0f;
        if (currentVolume < -80.0f) {
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }
    
    public void volumeMute(JSlider slider){
        if (mute==false) {
            previousVolume = currentVolume;
            currentVolume = -80.0f;
            fc.setValue(currentVolume);
            mute = true;
            
            slider.setValue(slider.getMinimum());
        }
        else if (mute==true) {
            currentVolume = previousVolume;
            slider.setValue((int) currentVolume);
            fc.setValue(currentVolume);
            mute = false;
        }
    }

    public boolean isMute() {
        return mute;
    }
    
    
}
