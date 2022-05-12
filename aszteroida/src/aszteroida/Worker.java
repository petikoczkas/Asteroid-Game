package aszteroida;

/**
 * Az aszteroidamezõben dolgozni képes dolgokat (Settler, Robot) reprezentáló absztrakt osztály
 * @author Dálnoky
 *
 */
public abstract class Worker extends Character {
	/**
	 * Kostruktor
	 * @param timer a játékban lévõ idõzítõ
	 */
	public Worker() {
		super();
	}
	
	/**
	 * A worker fúrja az aktuális tartozkódási helyéül szolgáló aszteroida
	 * kérgét, az azon lévõ lyukat 1 egységnyivel mélyítve. Ez akkor történik meg, ha az
	 * aszteoroida kérge legalább 1 egységnyi vastag.
	 */
	public boolean Drill() {
		int l=place.GetLayerNum();
		if(l > 0) {
			place.SetLayerNum(l-1);
			return true;
		}
		else {
			GameFrame.Instance().Message("Asteroid is already drilled, no layer left");
			//System.out.println("Error: Asteroid is already drilled, no layer left");
			return false;
		}
	}
	
}
