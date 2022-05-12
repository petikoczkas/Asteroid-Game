package aszteroida;
import java.util.ArrayList;
import java.util.Random;

/**
 * A napot reprezentálja a játékban. A nap napviharokat hozhat létre bizonyos
 * idõközönként, illetve jelezheti a vihar közeledtét a Workereknek.
 * @author Dálnoky
 *
 */
public class Sun implements Steppable {
	
	/**
	 * Singleton use-hoz használt statikus instance
	 */
	private static Sun instance = null;
	
	/**
	 * Eltárolja, hogy mennyi eséllyel következhet be napvihar
	 */
	private double stormChance;
	
	/**
	 * A napviharban lévõ aszteroidák és teleportkapuk listája
	 */
	private ArrayList<Place> inStorm = new ArrayList<Place>();
	
	private int start = 0;
	
	/**
	 * Privát konstruktor, beállítja a kezdeti napviharveszélyben lévõ helyek listáját
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
	 * Az osztály instance példányának elérését biztosító metódus 
	 * @return Sun objektummal tér vissza
	 */
	public static Sun Instance() {
		if (instance == null)
            instance = new Sun();
        return instance;
	}

	/**
	 * Létrehozza a napvihart, amikor elég nagy a napvihar esélye
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
	 * A napvihar valószínûségét lekérdezõ metódus
	 * @return a valószínûség
	 */
	public double GetStormChance() {
		return stormChance;
	}

	/**
	 * A napvihar elér egy helyet ezért bekerül a vihart értek listájába
	 * @param a az aszteroida
	 */
	public void AddInStorm(Place p) {
		inStorm.add(p);
		p.SetInStorm(true);
	}
	
	/**
	 * A napvihar valószínûségét változtatja, valamint változtatja a napvihar veszélyében lévõ helyek listáját
	 */
	public void SolarStormUpdate() {
		// A napvihar új valószínûsége
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
	 * A napvihar esélyének változtatása esetleges vihar létrehozása minden lépésben
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
	 * A napviharban lévõ helyek közül eltávolit egy adott helyet
	 * @param a az aszteroida
	 */
	public void RemInStorm(Place a) {
		a.SetInStorm(false);
		inStorm.remove(a);
	}
	
	/**
	 * A napviharban lévõ helyeket lekérdezõ metódus
	 * @return a napviharban lévõ helyek listája
	 */
	public ArrayList<Place> GetInStorm() {
		return inStorm;
	}
	
	public void SetInStorm(ArrayList<Place> ins) {
		inStorm = ins;
	}
}