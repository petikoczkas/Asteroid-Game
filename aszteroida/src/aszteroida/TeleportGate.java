package aszteroida;

import java.util.Random;

/**
 * A saját aszteroidájának, és saját párjának ismerete, hogy lehessen köztük mozogni.
 * Emellett, ha õ, vagy párja elromlik, a hozzá tartozó is elérhetetlenné válik. Napvihar
 * hatására elkezd mozogni az aszteroidák között
 * @author Dálnoky
 *
 */
public class TeleportGate extends Place implements Steppable {
	
	/**
	 * A vele kapcsolatban lévõ másik kapu 
	 */
	private TeleportGate pair;
	
	/**
	 * Az az aszteroida, amire elhelyezték. Errõl lehet használatba venni, és a pár kapun áthaladva erre érkezik a karakter
	 */
	private Asteroid asteroid;
	
	/**
	 *  Annak állapota, hogy érte-e napvihar, amitõl mozogni kezd.
	 */
	private boolean crazed;
	/**
	 * Konstruktor
	 * @param n a teleportkapu neve
	 */
	public TeleportGate()
	{
		crazed = false;
		pair = null;
		asteroid = null;
		Timer.Instance().AddSteppable(this);
	}
	
	/**
	 * Elmromlik a teleportkapu
	 */
	public void Destroy() {
		pair.SetPair(null);
	}
	
	/**
	 * A kapu párját beállító metódus
	 * @param t a párja
	 */
	public void SetPair(TeleportGate t) {
		pair=t;
	}
	
	/**
	 * Beállítja az aszteroidát amivel kapcsolatban van, innen lehet fellépni a kapuhoz
	 * @param a az aszteroida
	 */
	public void SetAsteroid(Asteroid a) {
		this.asteroid = a;
		a.SetTpGate(this);
	}
	
	/**
	 * A teleportkapu aszteroidáját lekérdezõ metódus
	 * @return az aszteroida
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	
	/**
	 * A teleportkaput vihat éri aminek hatására megkergül
	 */
	public void Storm() {
		crazed = true;
	}
	
	/**
	 * A kapu párját lekérdezõ metódus
	 * @return a kapu párja
	 */
	public TeleportGate GetPair() {
		return pair;
	}
	
	/**
	 * A kergültséget lekérdezõ metódus
	 * @return a kergültség állapota
	 */
	public boolean IsCrazed() {
		return crazed;
	}

	/**
	 * A megkergült teleportkapu mozog egy szomszédos aszteroidára
	 */
	@Override
	public void Step() {
		if(crazed) {
			int n = asteroid.GetNeighbours().size();
			int r = new Random().nextInt(n);
			asteroid = asteroid.GetNeighbours().get(r);
			asteroid.SetTpGate(this);
		}
		
	}
}
