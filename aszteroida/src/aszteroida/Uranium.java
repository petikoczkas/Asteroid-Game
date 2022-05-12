package aszteroida;

/**
 * Egyike a j�t�kban l�v� nyersanyagoknak, amelyeket a telepesek felvehetnek, �s amib�l a
 * b�zist fel kell �p�ts�k. Az ur�niumot reprezent�lja a j�t�kban. Radioakt�v anyag, azaz ha
 * h�rom alkalommal napk�zelbe ker�l egy teljesen kif�rt aszteroid�n, felrobban.
 * @author D�lnoky
 *
 */
public class Uranium extends Material {
	
	/**
	 * sz�montartja, h�nyszor ker�lt az ur�nium �gy napk�zelbe, hogy az
	 * aszteroida teljesen �t van f�rva, ami mag�ba foglalja. Kiindul� �rt�ke 0.
	 */
	private int exposed;
	
	/**
	 * konstruktor ami adott aszteroid�n helyezi el a jeget
	 * @param a az adott aszteroida
	 */
	public Uranium(Asteroid a) {
		asteroid=a;
		exposed = 0;
	}
	
	
	/**
	 * param�ter n�lk�li konstruktor
	 */
	public Uranium() {
		exposed = 0;
	}
	
	/**
	 * Akkor h�v�dik meg, ha az aszteroida napk�zelben van.
	 * Amennyiben az aszteroida teljesen ki van f�rva, a met�dus hat�s�ra az exposed
	 * sz�ml�l� eggyel n�vekszik, ami nyomon k�veti, hogy hanyadj�ra ker�lt az ur�nium
	 * napk�zelbe. Ha ez a sz�ml�l� el�ri a 3-at, megh�v�dik az aszteroida Explode
	 * met�dusa
	 */
	public void ExtremeHeat() {
		exposed++;
		if(exposed >= 3) {
			asteroid.Explode();
		}
	}
}
