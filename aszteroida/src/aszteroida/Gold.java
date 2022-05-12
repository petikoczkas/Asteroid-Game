package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. Az aranyat reprezentálja a játékban.
 * @author Dálnoky
 */
public class Gold extends Material {
	public Gold() {}
	/**
	 * konstruktor ami adott aszteroidán helyezi el az aranyat
	 * @param a az adott aszteroida
	 */
	public Gold(Asteroid a){
		asteroid=a;
	}
	
	/**
	 * Üres függvény, hiszen ez a nyersanyag nem tesz semmit, ha napközelbe kerül.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
