package Sound;

import java.applet.Applet;
import java.applet.AudioClip;

import Main.Game;

public class Sound {
	   private AudioClip clip;
	   public Sound(String name){
	   	/*
	      try
	      {
	         clip = Applet.newAudioClip(Game.class.getResource(name));
	      }catch (Throwable e){
	         e.printStackTrace();
	      }

	   	 */
	   }
	   public void play(){
	   	/*
	      try{
	         new Thread(){
	            public void run(){
	               clip.play();
	            }
	         }.start();
	      }catch(Throwable e){
	         e.printStackTrace();
	      }

	   	 */
	   }
	   
	   public void stop(){
		   //clip.stop();
	   }
	   
	   public void loop(){
	   	/*
		      try{
		         new Thread(){
		            public void run(){
		               clip.loop();
		            }
		         }.start();
		      }catch(Throwable e){
		         e.printStackTrace();
		      }
		      */
	   	 
		   }


	}