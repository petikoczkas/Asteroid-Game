package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. A szenet reprezentálja a játékban.
 * @author Dálnoky
 *
 */
public class Carbon extends Material {
	public Carbon() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el a szenet
	 * @param a az adott aszteroida
	 */
	public Carbon(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * Üres függvény, hiszen ez a nyersanyag nem tesz semmit, ha napközelbe kerül.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
