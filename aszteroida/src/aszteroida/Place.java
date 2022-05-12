package aszteroida;

import java.util.ArrayList;

/**
 * A játékban egy helyet(aszteroida, teleportkapu) reprezentáló absztrakt osztály. Tárolja a rajta
 * álló karaktereket.
 * @author Dálnoky
 *
 */
public abstract class Place {
	
	/**
	 * Tárolja, hogy a hely viharveszélyben van-e
	 */
	protected boolean inStorm = false;
	
	/**
	 * A helyen lévõ karakterek listája
	 */
	protected ArrayList<Character> characters=new ArrayList<Character>();
	
	/**
	 * A helyen lévõ karaktereket lekérdezõ metódus
	 * @return a karakterek listája
	 */
	public ArrayList<Character> GetCharacters() {
		return characters;
	}
	
	/**
	 * Visszaadja, hogy viharveszélyben van-e az adott hely
	 * @return
	 */
	public boolean GetInStorm() {
		return inStorm;
	}
	
	/**
	 * Beállítja a viharveszély booleanjét a paraméterben kapott értékre
	 * @param is
	 */
	public void SetInStorm(boolean is) {
		inStorm = is;
	}
	
	/**
	 * A hely kérgét beállító metódus, felüldefiniálva a megfelelõ osztályban
	 * @param l
	 */
	public void SetLayerNum(int l) {};
	
	/**
	 * A hely kérgét lekérdezõ metódus
	 * @return
	 */
	public int GetLayerNum() {
		return 1;
	}
	
	/**
	 * A helyben lévõ nyersanyagot lekérdezõ metódus
	 * @return felüldefiniált a megfelelõ osztájban ezért itt null
	 */
	public  Material GetMaterial() {return null;}
	
	/**
	 * A helyre nyersanyagot tesz le egy telepes
	 * @param m a nyersanyag amit a helybe tennénk
	 * @return felüldefiniált a megfelelõ osztájban ezért itt false
	 */
	public  boolean AcceptMaterial(Material m) {return false;}
	
	/**
	 * A hely felveszi listájába a paraméterként átvett karaktert
	 * @param c a karakter
	 */
	public void Accept(Character c) {
		characters.add(c);
		c.SetPlace(this);
	}
	
	/**
	 * A hely törli a listájában a paraméterként átvett karaktert
	 * @param c a karakter
	 */
	public void Remove(Character c) {
			characters.remove(c);
	}
	
	/**
	 * A helynek a magjában lévõ nyersanyagot beállító metódus
	 * @param m
	 */
	public void SetMaterial(Material m) {
		
	}
	
	/**
	 * A hely szomszédait lkeérdezõ metódus
	 * @return a szomszédok listája, felüldefiniálva a megfelelõ osztályban ezért itt null
	 */
	public ArrayList<Asteroid> GetNeighbours(){
		return null;
	}
}
