package aszteroida;

import java.util.ArrayList;

/**
 * A j�t�kban egy helyet(aszteroida, teleportkapu) reprezent�l� absztrakt oszt�ly. T�rolja a rajta
 * �ll� karaktereket.
 * @author D�lnoky
 *
 */
public abstract class Place {
	
	/**
	 * T�rolja, hogy a hely viharvesz�lyben van-e
	 */
	protected boolean inStorm = false;
	
	/**
	 * A helyen l�v� karakterek list�ja
	 */
	protected ArrayList<Character> characters=new ArrayList<Character>();
	
	/**
	 * A helyen l�v� karaktereket lek�rdez� met�dus
	 * @return a karakterek list�ja
	 */
	public ArrayList<Character> GetCharacters() {
		return characters;
	}
	
	/**
	 * Visszaadja, hogy viharvesz�lyben van-e az adott hely
	 * @return
	 */
	public boolean GetInStorm() {
		return inStorm;
	}
	
	/**
	 * Be�ll�tja a viharvesz�ly booleanj�t a param�terben kapott �rt�kre
	 * @param is
	 */
	public void SetInStorm(boolean is) {
		inStorm = is;
	}
	
	/**
	 * A hely k�rg�t be�ll�t� met�dus, fel�ldefini�lva a megfelel� oszt�lyban
	 * @param l
	 */
	public void SetLayerNum(int l) {};
	
	/**
	 * A hely k�rg�t lek�rdez� met�dus
	 * @return
	 */
	public int GetLayerNum() {
		return 1;
	}
	
	/**
	 * A helyben l�v� nyersanyagot lek�rdez� met�dus
	 * @return fel�ldefini�lt a megfelel� oszt�jban ez�rt itt null
	 */
	public  Material GetMaterial() {return null;}
	
	/**
	 * A helyre nyersanyagot tesz le egy telepes
	 * @param m a nyersanyag amit a helybe tenn�nk
	 * @return fel�ldefini�lt a megfelel� oszt�jban ez�rt itt false
	 */
	public  boolean AcceptMaterial(Material m) {return false;}
	
	/**
	 * A hely felveszi list�j�ba a param�terk�nt �tvett karaktert
	 * @param c a karakter
	 */
	public void Accept(Character c) {
		characters.add(c);
		c.SetPlace(this);
	}
	
	/**
	 * A hely t�rli a list�j�ban a param�terk�nt �tvett karaktert
	 * @param c a karakter
	 */
	public void Remove(Character c) {
			characters.remove(c);
	}
	
	/**
	 * A helynek a magj�ban l�v� nyersanyagot be�ll�t� met�dus
	 * @param m
	 */
	public void SetMaterial(Material m) {
		
	}
	
	/**
	 * A hely szomsz�dait lke�rdez� met�dus
	 * @return a szomsz�dok list�ja, fel�ldefini�lva a megfelel� oszt�lyban ez�rt itt null
	 */
	public ArrayList<Asteroid> GetNeighbours(){
		return null;
	}
}
