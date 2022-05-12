package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. A rezet reprezent�lja a j�t�kban.
 * @author D�lnoky
 *
 */
public class Copper extends Material {
	public Copper() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el a rezet
	 * @param a az adott aszteroida
	 */
	public Copper(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * �res f�ggv�ny, hiszen ez a nyersanyag nem tesz semmit, ha napk�zelbe ker�l.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
