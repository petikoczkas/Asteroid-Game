package aszteroida;

import java.util.Random;

/**
 * A saj�t aszteroid�j�nak, �s saj�t p�rj�nak ismerete, hogy lehessen k�zt�k mozogni.
 * Emellett, ha �, vagy p�rja elromlik, a hozz� tartoz� is el�rhetetlenn� v�lik. Napvihar
 * hat�s�ra elkezd mozogni az aszteroid�k k�z�tt
 * @author D�lnoky
 *
 */
public class TeleportGate extends Place implements Steppable {
	
	/**
	 * A vele kapcsolatban l�v� m�sik kapu 
	 */
	private TeleportGate pair;
	
	/**
	 * Az az aszteroida, amire elhelyezt�k. Err�l lehet haszn�latba venni, �s a p�r kapun �thaladva erre �rkezik a karakter
	 */
	private Asteroid asteroid;
	
	/**
	 *  Annak �llapota, hogy �rte-e napvihar, amit�l mozogni kezd.
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
	 * A kapu p�rj�t be�ll�t� met�dus
	 * @param t a p�rja
	 */
	public void SetPair(TeleportGate t) {
		pair=t;
	}
	
	/**
	 * Be�ll�tja az aszteroid�t amivel kapcsolatban van, innen lehet fell�pni a kapuhoz
	 * @param a az aszteroida
	 */
	public void SetAsteroid(Asteroid a) {
		this.asteroid = a;
		a.SetTpGate(this);
	}
	
	/**
	 * A teleportkapu aszteroid�j�t lek�rdez� met�dus
	 * @return az aszteroida
	 */
	public Asteroid GetAsteroid() {
		return asteroid;
	}
	
	/**
	 * A teleportkaput vihat �ri aminek hat�s�ra megkerg�l
	 */
	public void Storm() {
		crazed = true;
	}
	
	/**
	 * A kapu p�rj�t lek�rdez� met�dus
	 * @return a kapu p�rja
	 */
	public TeleportGate GetPair() {
		return pair;
	}
	
	/**
	 * A kerg�lts�get lek�rdez� met�dus
	 * @return a kerg�lts�g �llapota
	 */
	public boolean IsCrazed() {
		return crazed;
	}

	/**
	 * A megkerg�lt teleportkapu mozog egy szomsz�dos aszteroid�ra
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
