package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. A jeget, mint nyersanyagot reprezent�lja a j�t�kban.
 * @author D�lnoky
 *
 */
public class Ice extends Material{
	public Ice() {}
	/**
	 * konstruktor ami adott aszteroid�n helyezi el a jeget
	 * @param a az adott aszteroida
	 */
	public Ice(Asteroid a) {
		asteroid=a;
	}
	/**
	 * Akkor h�v�dik meg, ha az aszteroida napk�zelben van.
	 * Amennyiben az aszteroida teljesen ki van f�rva, a met�dus hat�s�ra bel�le a v�zj�g
	 * elszublim�l, azaz �res lesz ezut�n az aszteroida belseje.
	 */
	public void ExtremeHeat() {
		asteroid.SetMaterial(null);
	}
}
	
