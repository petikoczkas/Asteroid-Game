package aszteroida;

import java.util.ArrayList;

/**
 * A játékban az idõ múlását vezérlõ osztály.
 * @author Dálnoky
 *
 */
public class Timer {
	
	/**
	 * Singleton use-hoz használt statikus instance
	 */
	private static Timer instance = null;
	/**
	 * Bool változó, ami jelzi az új játék kezdetét a Tick metódusnak
	 */
	public boolean isNewGame=false;
	/**
	 * A léptethetõ objektumokat tároló lista
	 */
	private ArrayList<Steppable> steppable=new ArrayList<Steppable>();
	
	/**
	 * Privát konstruktor
	 */
	private Timer() {
		
	}
	
	/**
	 * Az osztály instance példányának elérését biztosító metódus 
	 * @return Timer objektummal tér vissza
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
	 * Egymás után hívja a játékban lévõ entitások lépéseit. Az önmûködõ
	 * entitások lépései automatikusan futnak le
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
	 * Hozzáadja a listájához egy léptethetõ entitást
	 * @param s a létethetõ entitás
	 */
	public void AddSteppable(Steppable s) {
		steppable.add(s);
	}
	
	/**
	 *  Eltávolítja a listájából a paraméterként átvett léptethetõ entitást
	 * @param s a léptethetõ entitás
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
