package aszteroida;

/**
 * Egyike a játékban lévõ nyersanyagoknak, amelyeket a telepesek felvehetnek, és amibõl a
 * bázist fel kell építsék. Az urániumot reprezentálja a játékban. Radioaktív anyag, azaz ha
 * három alkalommal napközelbe kerül egy teljesen kifúrt aszteroidán, felrobban.
 * @author Dálnoky
 *
 */
public class Uranium extends Material {
	
	/**
	 * számontartja, hányszor került az uránium úgy napközelbe, hogy az
	 * aszteroida teljesen át van fúrva, ami magába foglalja. Kiinduló értéke 0.
	 */
	private int exposed;
	
	/**
	 * konstruktor ami adott aszteroidán helyezi el a jeget
	 * @param a az adott aszteroida
	 */
	public Uranium(Asteroid a) {
		asteroid=a;
		exposed = 0;
	}
	
	
	/**
	 * paraméter nálküli konstruktor
	 */
	public Uranium() {
		exposed = 0;
	}
	
	/**
	 * Akkor hívódik meg, ha az aszteroida napközelben van.
	 * Amennyiben az aszteroida teljesen ki van fúrva, a metódus hatására az exposed
	 * számláló eggyel növekszik, ami nyomon követi, hogy hanyadjára került az uránium
	 * napközelbe. Ha ez a számláló eléri a 3-at, meghívódik az aszteroida Explode
	 * metódusa
	 */
	public void ExtremeHeat() {
		exposed++;
		if(exposed >= 3) {
			asteroid.Explode();
		}
	}
}
