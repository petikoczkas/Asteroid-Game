package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. A vasat reprezent�lja a j�t�kban.
 * @author D�lnoky
 *
 */
public class Iron extends Material{
	public Iron() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el a vasat
	 * @param a az adott aszteroida
	 */
	public Iron(Asteroid a) {
		
		asteroid=a;
	}
	
	/**
	 * �res f�ggv�ny, hiszen ez a nyersanyag nem tesz semmit, ha napk�zelbe ker�l.
	 */
	@Override
	public void ExtremeHeat() { }
}
