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
 * F�oszt�ly, ebben futtatjuk a j�t�kot.
 * @author D�lnoky
 */
public class Main {
	
	/**
	 * Game oszt�ly p�ld�ny�t t�rolja.
	 */
	private static Game game;
	
	static GameFrame gf;
	
	public static boolean isactive=false;
	
	/**
	 * Timer oszt�ly p�ld�ny�t t�rolja.
	 */
	private static Timer timer;
	/**
	 * Ez  a f�ggv�ny futtatja a programot.
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
	 * Ez a met�dus inicializ�lja a j�t�k p�ly�j�t. L�trehoz egy Timer �s ey Game p�ld�nyt, amit a Main oszt�lyban elt�rolunk.
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
