package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. A rezet reprezentálja a játékban.
 * @author Dálnoky
 *
 */
public class Copper extends Material {
	public Copper() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el a rezet
	 * @param a az adott aszteroida
	 */
	public Copper(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * Üres függvény, hiszen ez a nyersanyag nem tesz semmit, ha napközelbe kerül.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
