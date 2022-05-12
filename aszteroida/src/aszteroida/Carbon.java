package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. A szenet reprezent�lja a j�t�kban.
 * @author D�lnoky
 *
 */
public class Carbon extends Material {
	public Carbon() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el a szenet
	 * @param a az adott aszteroida
	 */
	public Carbon(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * �res f�ggv�ny, hiszen ez a nyersanyag nem tesz semmit, ha napk�zelbe ker�l.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
