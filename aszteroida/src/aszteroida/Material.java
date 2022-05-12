package aszteroida;

/**
 * Az aszteroida belsejében lévõ nyersanyagot reprezentáló absztrakt osztály. 
 * @author Dálnoky
 *
 */
public abstract class Material {
	public Material() {}
	/**
	 * Az adott aszteroida, amiben a nyersanyag található.
	 */
	protected Asteroid asteroid;
	
	/**
	 * A nyersanyag éppen aktuális aszteroidáját lekérdezõ metódus
	 * @return az aszteroida
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	/**
	 * A nyersanyag éppen aktuális aszteroidáját beállító metódus
	 * @param az aszteroida
	 */
	public void SetAsteroid(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * Néhany nyersanyag napközelségre megjelenõ tulajdonságát meghívó metódus
	 */
	public abstract void ExtremeHeat();
}
