package aszteroida;
import java.util.ArrayList;
import java.util.Random;

/**
 * A napot reprezent�lja a j�t�kban. A nap napviharokat hozhat l�tre bizonyos
 * id�k�z�nk�nt, illetve jelezheti a vihar k�zeledt�t a Workereknek.
 * @author D�lnoky
 *
 */
public class Sun implements Steppable {
	
	/**
	 * Singleton use-hoz haszn�lt statikus instance
	 */
	private static Sun instance = null;
	
	/**
	 * Elt�rolja, hogy mennyi es�llyel k�vetkezhet be napvihar
	 */
	private double stormChance;
	
	/**
	 * A napviharban l�v� aszteroid�k �s teleportkapuk list�ja
	 */
	private ArrayList<Place> inStorm = new ArrayList<Place>();
	
	private int start = 0;
	
	/**
	 * Priv�t konstruktor, be�ll�tja a kezdeti napviharvesz�lyben l�v� helyek list�j�t
	 */
	private Sun() {
		ReConst();
	}
	
	public void ReConst(){
		stormChance = 0;
		ArrayList<Asteroid> asteroids = AsteroidField.Instance().GetAsteroids();
		Random rand = new Random();
		int start = rand.nextInt(asteroids.size() - 6);
		for(int i = 0; i < 6; i++) {
			AddInStorm(asteroids.get(start + i));
		}
		Timer.Instance().AddSteppable(this);
		
	}
	
	/**
	 * Az oszt�ly instance p�ld�ny�nak el�r�s�t biztos�t� met�dus 
	 * @return Sun objektummal t�r vissza
	 */
	public static Sun Instance() {
		if (instance == null)
            instance = new Sun();
        return instance;
	}

	/**
	 * L�trehozza a napvihart, amikor el�g nagy a napvihar es�lye
	 */
	public void MakeSolarStorm() {
		if(stormChance > 0.7) {
			GameFrame.Instance().MenuBarColor("Storm");
			GameFrame.Instance().Message("A Solar storm occured");
			for(int i = 0; i < inStorm.size(); i++) {
				ArrayList<Character> clist = inStorm.get(i).GetCharacters();
				for(int j = 0; j < clist.size(); j++) {
					if(clist.get(j).GetPlace().GetLayerNum() != 0)
						clist.get(j).Die();
				}
			}
			for(int i=0; i<inStorm.size(); i++) {
				if(inStorm.get(i).GetNeighbours() == null) {
					((TeleportGate)inStorm.get(i)).Storm();
				}
			}
		}
		else {
			GameFrame.Instance().MenuBarColor("Simple");
		}
	}
	
	/**
	 * A napvihar val�sz�n�s�g�t lek�rdez� met�dus
	 * @return a val�sz�n�s�g
	 */
	public double GetStormChance() {
		return stormChance;
	}

	/**
	 * A napvihar el�r egy helyet ez�rt beker�l a vihart �rtek list�j�ba
	 * @param a az aszteroida
	 */
	public void AddInStorm(Place p) {
		inStorm.add(p);
		p.SetInStorm(true);
	}
	
	/**
	 * A napvihar val�sz�n�s�g�t v�ltoztatja, valamint v�ltoztatja a napvihar vesz�ly�ben l�v� helyek list�j�t
	 */
	public void SolarStormUpdate() {
		// A napvihar �j val�sz�n�s�ge
		Random rand = new Random();
		stormChance += -0.05 + (0.15 + 0.05) * rand.nextDouble();
		if(stormChance < 0 || stormChance > 0.8) 
			stormChance = 0;
		
		for(int i=start; i < start + 8;i++) {
			RemInStorm(AsteroidField.Instance().GetAsteroids().get(i));
		}
		start += 8;
		
		if(start >= (AsteroidField.Instance().GetAsteroids().size() - 8)) 
			start -= (AsteroidField.Instance().GetAsteroids().size() - 8);
		
		
		for(int i=start;i< start + 8;i++) {
			AddInStorm(AsteroidField.Instance().GetAsteroids().get(i));
		}
		
	}

	/**
	 * A napvihar es�ly�nek v�ltoztat�sa esetleges vihar l�trehoz�sa minden l�p�sben
	 */
	@Override
	public void Step() {
		MakeSolarStorm();
		SolarStormUpdate();
		
	}
	
	public void SetStormChance(double d) {
		stormChance = d;
	}
	
	/**
	 * A napviharban l�v� helyek k�z�l elt�volit egy adott helyet
	 * @param a az aszteroida
	 */
	public void RemInStorm(Place a) {
		a.SetInStorm(false);
		inStorm.remove(a);
	}
	
	/**
	 * A napviharban l�v� helyeket lek�rdez� met�dus
	 * @return a napviharban l�v� helyek list�ja
	 */
	public ArrayList<Place> GetInStorm() {
		return inStorm;
	}
	
	public void SetInStorm(ArrayList<Place> ins) {
		inStorm = ins;
	}
}