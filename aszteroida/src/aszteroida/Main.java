package aszteroida;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Fõosztály, ebben futtatjuk a játékot.
 * @author Dálnoky
 */
public class Main {
	
	/**
	 * Game osztály példányát tárolja.
	 */
	private static Game game;
	
	static GameFrame gf;
	
	public static boolean isactive=false;
	
	/**
	 * Timer osztály példányát tárolja.
	 */
	private static Timer timer;
	/**
	 * Ez  a függvény futtatja a programot.
	 * @param args
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * @throws MalformedURLException 
	 * @throws CannotRealizeException 
	 * @throws NoPlayerException 
	 */
	public static void main(String[] args){
		new MenuFrame();
		while(true) {
			if(isactive) {
				timer.Tick();
			}
			System.out.print("");

		}
		
		
	}

	/**
	 * Ez a metódus inicializálja a játék pályáját. Létrehoz egy Timer és ey Game példányt, amit a Main osztályban eltárolunk.
	 */
	public static void Init(int s, GameFrame g) {
		timer = Timer.Instance();
		game= Game.Instance(s);
		isactive=true;
		gf=g;
	}
	public static void ReInit(int s, GameFrame g) {
		timer.GetSteppable().clear();
		game.GetAF().GetAsteroids().clear();
		game.GetAF().GetPhAsteroids().clear();
		game.GetAF().GetSettlers().clear();
		game.GetAF().SetBase(null);
		//game.SetAF(null);
		game.GetSun().GetInStorm().clear();;
		game.GetSun().SetStormChance(0);
		//game.SetSun(null);
		timer.isNewGame=true;
		game.ReConst(s);
		gf.MenuBarColor("Simple");
		gf.setVisible(true);
		isactive=true;
		
	}
}
