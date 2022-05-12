package aszteroida;

/**
 * Ez az osztály felelõs a játék menetéért. Elõkészíti a pályát, megállapítja, hogy mikor van vége
 * a játéknak, illetve azt, hogy a telepesek nyertek-e vagy sem.
 * @author Dálnoky
 *
 */
public class Game {
	
	/**
	 * Singleton use-hoz használt statikus instance
	 */
	private static Game instance = null;
	
	/**
	 * Játékosok száma
	 */
	public static int settlernum;
	/**
	 * A játékban lévõ nap
	 */
	private Sun sun;
	
	
	/**
	 * A játékban lévõ aszteroida mezõ 
	 */
	private AsteroidField asteroidField;
	
	/**
	 * Privát konstruktor
	 * @param s a játékosok száma
	 */
	private Game(int s) {
		settlernum=s;
		asteroidField= AsteroidField.Instance();
		sun = Sun.Instance();
		Timer.Instance().AddSteppable(asteroidField);
		
		
	}
	
	public void ReConst(int s) {
		settlernum=s;
		asteroidField.ReConst(50+Game.settlernum*25);
		sun.ReConst();
		Timer.Instance().AddSteppable(asteroidField);
	}
	
	/**
	 * Paraméter nélküli privát konstruktor
	 */
	private Game() {
		asteroidField= AsteroidField.Instance();
		sun = Sun.Instance();
		Timer.Instance().AddSteppable(asteroidField);
	}
	
	/**
	 * A Game singleton példány létrehozására és elérésére használt metódus
	 * @return Game objektummal tér vissza
	 */
	public static Game Instance(int s)
    {
        if (instance == null)
            instance = new Game(s);
        return instance;
    }
	
	public static Game Instance(int s, String str)
    {
        if (str.equals("new"))
            instance = new Game(s);
        return instance;
    }
	
	/**
	 * Az osztály instance példányának elérését biztosító metódus 
	 * @return Game objektummal tér vissza
	 */
	public static Game Instance()
    {
        return instance;
    }
	
	/**
	 * A játékban lévõ napot lekérdezõ metódus
	 * @return A nap
	 */
	public Sun GetSun() {
		return sun;
	}
	
	public void SetSun(Sun s) {
		sun = s;
	}
	
	/**
	 * A játék a telepesek számára gyõzelemmel végetér
	 */
	public void Win() {
		Timer.Instance().isNewGame=true;
		GameFrame.Instance().Disable();
		Main.isactive=false;
		AsteroidField.Instance().GetSettlers().clear();
		GameFrame.Instance().MenuBarColor("Win");
		GameFrame.Instance().Message("The game has ended! The settlers have won! The base has been built!");
	}
	
	/**
	 * A játék a telepesek számára vereséggel végetér
	 */
	public void Lose() {
		GameFrame.Instance().Disable();
		Main.isactive=false;
		GameFrame.Instance().MenuBarColor("Lose");
		GameFrame.Instance().Message("The game has ended! The settlers have lost!");
	}
	
	/**
	 * A játékban lévõ aszteroidamezõt lekérdezõ metódus
	 * @return Az aszteroidamezõ
	 */
	public AsteroidField GetAF() {
		return asteroidField;
	}
	
	public void SetAF(AsteroidField af) {
		 asteroidField = af;
	}
	
	
}
