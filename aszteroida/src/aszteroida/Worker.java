package aszteroida;

/**
 * Az aszteroidamez�ben dolgozni k�pes dolgokat (Settler, Robot) reprezent�l� absztrakt oszt�ly
 * @author D�lnoky
 *
 */
public abstract class Worker extends Character {
	/**
	 * Kostruktor
	 * @param timer a j�t�kban l�v� id�z�t�
	 */
	public Worker() {
		super();
	}
	
	/**
	 * A worker f�rja az aktu�lis tartozk�d�si hely��l szolg�l� aszteroida
	 * k�rg�t, az azon l�v� lyukat 1 egys�gnyivel m�ly�tve. Ez akkor t�rt�nik meg, ha az
	 * aszteoroida k�rge legal�bb 1 egys�gnyi vastag.
	 */
	public boolean Drill() {
		int l=place.GetLayerNum();
		if(l > 0) {
			place.SetLayerNum(l-1);
			return true;
		}
		else {
			GameFrame.Instance().Message("Asteroid is already drilled, no layer left");
			//System.out.println("Error: Asteroid is already drilled, no layer left");
			return false;
		}
	}
	
}
