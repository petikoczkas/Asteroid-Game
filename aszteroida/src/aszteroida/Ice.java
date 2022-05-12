package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. A jeget, mint nyersanyagot reprezentálja a játékban.
 * @author Dálnoky
 *
 */
public class Ice extends Material{
	public Ice() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el a jeget
	 * @param a az adott aszteroida
	 */
	public Ice(Asteroid a) {
		asteroid=a;
	}
	/**
	 * Akkor hívódik meg, ha az aszteroida napközelben van.
	 * Amennyiben az aszteroida teljesen ki van fúrva, a metódus hatására belõle a vízjég
	 * elszublimál, azaz üres lesz ezután az aszteroida belseje.
	 */
	public void ExtremeHeat() {
		asteroid.SetMaterial(null);
	}
}
	
