package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. A vasat reprezentálja a játékban.
 * @author Dálnoky
 *
 */
public class Iron extends Material{
	public Iron() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el a vasat
	 * @param a az adott aszteroida
	 */
	public Iron(Asteroid a) {
		
		asteroid=a;
	}
	
	/**
	 * Üres függvény, hiszen ez a nyersanyag nem tesz semmit, ha napközelbe kerül.
	 */
	@Override
	public void ExtremeHeat() { }
}
