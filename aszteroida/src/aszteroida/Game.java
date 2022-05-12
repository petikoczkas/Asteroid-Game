package aszteroida;

/**
 * Ez az oszt�ly felel�s a j�t�k menet��rt. El�k�sz�ti a p�ly�t, meg�llap�tja, hogy mikor van v�ge
 * a j�t�knak, illetve azt, hogy a telepesek nyertek-e vagy sem.
 * @author D�lnoky
 *
 */
public class Game {
	
	/**
	 * Singleton use-hoz haszn�lt statikus instance
	 */
	private static Game instance = null;
	
	/**
	 * J�t�kosok sz�ma
	 */
	public static int settlernum;
	/**
	 * A j�t�kban l�v� nap
	 */
	private Sun sun;
	
	
	/**
	 * A j�t�kban l�v� aszteroida mez� 
	 */
	private AsteroidField asteroidField;
	
	/**
	 * Priv�t konstruktor
	 * @param s a j�t�kosok sz�ma
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
	 * Param�ter n�lk�li priv�t konstruktor
	 */
	private Game() {
		asteroidField= AsteroidField.Instance();
		sun = Sun.Instance();
		Timer.Instance().AddSteppable(asteroidField);
	}
	
	/**
	 * A Game singleton p�ld�ny l�trehoz�s�ra �s el�r�s�re haszn�lt met�dus
	 * @return Game objektummal t�r vissza
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
	 * Az oszt�ly instance p�ld�ny�nak el�r�s�t biztos�t� met�dus 
	 * @return Game objektummal t�r vissza
	 */
	public static Game Instance()
    {
        return instance;
    }
	
	/**
	 * A j�t�kban l�v� napot lek�rdez� met�dus
	 * @return A nap
	 */
	public Sun GetSun() {
		return sun;
	}
	
	public void SetSun(Sun s) {
		sun = s;
	}
	
	/**
	 * A j�t�k a telepesek sz�m�ra gy�zelemmel v�get�r
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
	 * A j�t�k a telepesek sz�m�ra veres�ggel v�get�r
	 */
	public void Lose() {
		GameFrame.Instance().Disable();
		Main.isactive=false;
		GameFrame.Instance().MenuBarColor("Lose");
		GameFrame.Instance().Message("The game has ended! The settlers have lost!");
	}
	
	/**
	 * A j�t�kban l�v� aszteroidamez�t lek�rdez� met�dus
	 * @return Az aszteroidamez�
	 */
	public AsteroidField GetAF() {
		return asteroidField;
	}
	
	public void SetAF(AsteroidField af) {
		 asteroidField = af;
	}
	
	
}
