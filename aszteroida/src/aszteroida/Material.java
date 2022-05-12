package aszteroida;

/**
 * Az aszteroida belsej�ben l�v� nyersanyagot reprezent�l� absztrakt oszt�ly. 
 * @author D�lnoky
 *
 */
public abstract class Material {
	public Material() {}
	/**
	 * Az adott aszteroida, amiben a nyersanyag tal�lhat�.
	 */
	protected Asteroid asteroid;
	
	/**
	 * A nyersanyag �ppen aktu�lis aszteroid�j�t lek�rdez� met�dus
	 * @return az aszteroida
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	/**
	 * A nyersanyag �ppen aktu�lis aszteroid�j�t be�ll�t� met�dus
	 * @param az aszteroida
	 */
	public void SetAsteroid(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * N�hany nyersanyag napk�zels�gre megjelen� tulajdons�g�t megh�v� met�dus
	 */
	public abstract void ExtremeHeat();
}
