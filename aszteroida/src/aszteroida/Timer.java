package aszteroida;

import java.util.ArrayList;

/**
 * A j�t�kban az id� m�l�s�t vez�rl� oszt�ly.
 * @author D�lnoky
 *
 */
public class Timer {
	
	/**
	 * Singleton use-hoz haszn�lt statikus instance
	 */
	private static Timer instance = null;
	/**
	 * Bool v�ltoz�, ami jelzi az �j j�t�k kezdet�t a Tick met�dusnak
	 */
	public boolean isNewGame=false;
	/**
	 * A l�ptethet� objektumokat t�rol� lista
	 */
	private ArrayList<Steppable> steppable=new ArrayList<Steppable>();
	
	/**
	 * Priv�t konstruktor
	 */
	private Timer() {
		
	}
	
	/**
	 * Az oszt�ly instance p�ld�ny�nak el�r�s�t biztos�t� met�dus 
	 * @return Timer objektummal t�r vissza
	 */
	public static Timer Instance()
    {
        if (instance == null)
            instance = new Timer();
        return instance;
    }
	
	public static Timer Instance(String s)
    {
        if (s.equals("new"))
            instance = new Timer();
        return instance;
    }
	
	
	/**
	 * Egym�s ut�n h�vja a j�t�kban l�v� entit�sok l�p�seit. Az �nm�k�d�
	 * entit�sok l�p�sei automatikusan futnak le
	 */
	public void Tick() {
		for(int i = 0; i < steppable.size(); i++) {
			if(isNewGame) {
				isNewGame=false;
				return;
			}
			steppable.get(i).Step();
			
		}
		
	}
	
	/**
	 * Hozz�adja a list�j�hoz egy l�ptethet� entit�st
	 * @param s a l�tethet� entit�s
	 */
	public void AddSteppable(Steppable s) {
		steppable.add(s);
	}
	
	/**
	 *  Elt�vol�tja a list�j�b�l a param�terk�nt �tvett l�ptethet� entit�st
	 * @param s a l�ptethet� entit�s
	 */
	public void RemoveSteppable(Steppable s) {
		steppable.remove(s);
	}
	
	public  ArrayList<Steppable> GetSteppable() {
		return steppable;
	}
	
	public void SetSteppable(ArrayList<Steppable> l) {
		steppable = l;
	}
}
