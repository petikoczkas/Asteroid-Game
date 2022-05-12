package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. Az aranyat reprezent�lja a j�t�kban.
 * @author D�lnoky
 */
public class Gold extends Material {
	public Gold() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el az aranyat
	 * @param a az adott aszteroida
	 */
	public Gold(Asteroid a){
		asteroid=a;
	}
	
	/**
	 * �res f�ggv�ny, hiszen ez a nyersanyag nem tesz semmit, ha napk�zelbe ker�l.
	 */
	@Override
	public void ExtremeHeat() {
	}
}
