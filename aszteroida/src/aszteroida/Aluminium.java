package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. Az alumíniumot reprezentálja a játékban.
 * @author Dálnoky
 *
 */
public class Aluminium extends Material {
	public Aluminium() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el az aluminiumot
	 * @param a az adott aszteroida
	 */
	public Aluminium(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * Üres függvény, hiszen ez a nyersanyag nem tesz semmit, ha napközelbe kerül.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
