package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. Az alum�niumot reprezent�lja a j�t�kban.
 * @author D�lnoky
 *
 */
public class Aluminium extends Material {
	public Aluminium() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el az aluminiumot
	 * @param a az adott aszteroida
	 */
	public Aluminium(Asteroid a) {
		asteroid=a;
	}
	
	/**
	 * �res f�ggv�ny, hiszen ez a nyersanyag nem tesz semmit, ha napk�zelbe ker�l.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
