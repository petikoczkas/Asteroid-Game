package aszteroida;

import java.util.ArrayList;
import java.util.Random;
/**
 * Az aszteroidák összessége. Számon tartja azokat az aszteroidákat, melyek napközelben
 * vannak, továbbá ez az osztály kezeli a játékban lévõ telepeseket is, illetve eltávolítja a
 * felrobbant aszteroidákat a játékból.
 * @author Dálnoky
 *
 */
public class AsteroidField implements Steppable {
	
	/**
	 * Singleton use-hoz használt statikus instance
	 */
	private static AsteroidField instance = null;
	
	/**
	 * Tárolja a játékban lévõ aszteroidákat, azaz azokat az
	 * aszteroidákat, melyek nem tudnak, vagy még nem robbantak fel. Ha egy aszteroida
	 * felrobban, azt kiveszi a tömbbõl.
	 */
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	
	/**
	 * A napközelben lévõ aszteroidák. Csak ezekre az
	 * aszteroidákra kell elvégezni a napközelség hatásait.
	 */
	private ArrayList<Asteroid> phAsteroids = new ArrayList<Asteroid>();
	
	/**
	 * A még életben lévõ telepesek. A játék végének a detektálásához használjuk
	 */
	private ArrayList<Settler> settlers = new ArrayList<Settler>();
	
	/**
	 * Az aszteroidamezõben lévõ bázis
	 */
	private Base base;
	
	private int start = 0;
	
	/**
	 * Privát konstruktor adott számú aszteroidával rendelkezõ aszteroidamezõhöz
	 * @param astercount a kezdeti aszteroidák száma
	 */
	private AsteroidField(int astercount) {
		
			ReConst(astercount);
	}
	
	/**
	 * Az osztály instance példányának elérését biztosító metódus 
	 * @return AsteroidField objektummal tér vissza
	 */
	public static AsteroidField Instance()
    {
        if (instance == null) {
			Game.Instance();
			instance = new AsteroidField(50+Game.settlernum*25);
		}
        return instance;
    }
	
	
	/**
	 * A napközelben lévõ aszteroidákat lekérdezõ metódus
	 * @return a napközeli aszteroidák
	 */
	public ArrayList<Asteroid> GetPhAsteroids() {
		return phAsteroids;
	}

	/**
	 * A bázist lekérdezõ metódus
	 * @return a bázis
	 */
	public Base GetBase() {
		return base;
	}
	
	public void SetBase(Base b) {
		base = b;
	}
	
	/**
	 * A nyersanyagok számát ellenõrzõ metódus, ha nincs elegendõ nyersanyag a bázis megépítéséhez akkor a játéknak vége
	 * @return ha elfogytak a kellõ nyersanyagok akkor igaz egyébként hamis
	 */
	private boolean MaterialCheck() {
		int uran=0;
		int alu=0;
		int gold=0;
		int ice=0;
		int cop=0;
		int iron=0;
		int carbon=0;
		for (int i = 0; i < asteroids.size(); i++) {
			if(asteroids.get(i).GetMaterial()!=null) {
				String type;
				type = asteroids.get(i).GetMaterial().getClass().getSimpleName();
				switch(type) {
					case "Uranium": uran++; break;
					case "Aluminium": alu++; break;
					case "Gold": gold++; break;
					case "Ice": ice++; break;
					case "Copper": cop++; break;
					case "Iron": iron++; break;
					case "Carbon": carbon++; break;
					default: break;
				}
			}
			
		}
		for (int i = 0; i < settlers.size(); i++) {
			for (int j = 0; j < settlers.get(i).GetInventory().size(); j++) {
				String type;
				type = settlers.get(i).GetInventory().get(j).getClass().getSimpleName();
				switch(type) {
					case "Uranium": uran++; break;
					case "Aluminium": alu++; break;
					case "Gold": gold++; break;
					case "Ice": ice++; break;
					case "Copper": cop++; break;
					case "Iron": iron++; break;
					case "Carbon": carbon++; break;
					default: break;
				}
			}
		}
		if(uran<3 || alu<3 || gold<3 || ice<3 || cop<3 || iron<3 || carbon<3) {
			return true;
		}
		return false;
	}
	/**
	 * A telepesek számát ellenõrzõ metódus, ha a meghal az összes telepes akkor vége a játéknak
	 * @return ha minden telepes meghalt igaz egyébként hamis
	 */
	public boolean SettlerCheck() {
		if(settlers.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Az aszteroidákat lekérdezõ metódus
	 * @return az aktuális aszteroidák listája
	 */
	public ArrayList<Asteroid> GetAsteroids() {
		return asteroids;
	}
	
	/**
	 * Az aszteroidamezõhöz egy aszteroidát hozzáadó metódus
	 * @param a a hozzáadandó aszteroida
	 */
	public void AddAsteroid(Asteroid a) {
		asteroids.add(a);
	}
	
	/**
	 * Aszteroidát napközelségbe tévõ metódus
	 * @param a az aszteroida amit napközelbe tesz
	 */
	public void AddInPh(Asteroid a) {
		phAsteroids.add(a);
		a.SetInPh(true);
	}
	
	/**
	 * Aszteroidát napközelségbõl eltávolító metódus
	 * @param a az aszteroida amit napközelbõl kivesz
	 */
	public void RemFromPh(Asteroid a) {
		phAsteroids.remove(a);
	}

	/**
	 * Eltávolít egy aszteroidát a pályáról.(felrobbanás esetén)
	 * @param a az aszteroida amit eltávolítunk
	 */
	public void RemoveAsteroid(Asteroid a) {
		asteroids.remove(a);
	}
	
	/**
	 *  Még nem használt kezdetleges függvény, késõbb a napközelben lévõ aszteroidák listáját lépteti
	 */
	public void PerihelionUpdate() {

		for(int i=start; i<start + asteroids.size()/10;i++) {
			phAsteroids.remove(asteroids.get(i));
			asteroids.get(i).SetInPh(false);
		}
		start += asteroids.size()/10;
		if(start >= asteroids.size() - (asteroids.size()/10)) start -=  (asteroids.size() - (asteroids.size()/10));
		
		
		for(int i=start;i< start + asteroids.size()/10;i++) {
			
			phAsteroids.add(asteroids.get(i));
			asteroids.get(i).SetInPh(true);
		}	
	}
	
	/**
	 * A telepeseket lekérdezõ metódus
	 * @return a telepesek listája
	 */
	public ArrayList<Settler> GetSettlers(){
		return settlers;
	}
	
	
	/**
	 * Ha meghal egy játékos, akkor eltávolítja a játékból.
	 * @param s az eltávolítandó telepes
	 */
	public void RemoveSettler(Settler s) {
		for(int i=0; i<settlers.size(); i++) {
			if(settlers.get(i).equals(s)) {
				settlers.remove(i);
			}
		}
	}
	
	/**
	 * Ez a metódus hozza létre az aszteroidamezõben található listákat, változókat.
	 * @param astercount a kezdeti aszteroidák száma
	 */
	public void ReConst(int astercount) {
		base=new Base();
		Game.Instance();
		// létrehozza az aszteroidákat
		for(int j=0;j<(Game.settlernum+4)*2;j++) {
			for(int i=0;i<7;i++) {
				int l = new Random().nextInt(7) + 1;
				asteroids.add(new Asteroid(i+1, l));
			}
		}
		for (int i = 0; i < astercount-14*Game.settlernum; i++) {
			int l = new Random().nextInt(7) + 1;
			int mat = new Random().nextInt(8);
			asteroids.add(new Asteroid(mat, l));
		}
		// beállítja a szomszédokat
		int num=new Random().nextInt(astercount/10)+5;
		for(int i=0;i<asteroids.size();i++) {
			if(i%num==0) {
				base.SetNeighbour(asteroids.get(i));
			}
		}
		for(int i=0;i<asteroids.size();i++) {
			num=new Random().nextInt(20)+10;
			int j=0;
			j+=new Random().nextInt(num)+1;
			while(i+j<asteroids.size()) {
				asteroids.get(i).SetNeighbour(asteroids.get(i+j));
				j+=new Random().nextInt(num)+1;
			}
		}
		// létrehozza és elhelyezi a telepeseket
		for(int i=0;i<Game.settlernum;i++) { 
			Settler s=new Settler();
			settlers.add(s);
		}
		for(Settler i:settlers) {
			num=new Random().nextInt(astercount);
			asteroids.get(num).Accept(i);
		}
		// létrehozza és elhelyezi az ufókat
		for(int i=0;i<Game.settlernum*1.5;i++) {
			Ufo u=new Ufo();
			num=new Random().nextInt(astercount);
			asteroids.get(num).Accept(u);
			u.SetPlace(asteroids.get(num));
		}
		// beállítja a kezdei napközeli aszteroidákat
		int phstart=new Random().nextInt(astercount-astercount/10);
		start = phstart;
		for(int i=0;i<astercount/10;i++) {
			phAsteroids.add(asteroids.get(i+phstart));
			asteroids.get(i+phstart).SetInPh(true);
		}

	}
	
	/**
	 * Az aszteroida-mezõ mozog a nap körül, ennek hatására változnak a napközelségek.
	 */
	@Override
	public void Step() {
		if(!(base.CheckWin())) {
			PerihelionUpdate();
			for(int i=0; i<settlers.size(); i++) {
				if(settlers.get(i).dead==true) RemoveSettler(settlers.get(i));
			}
			for(int i=0; i<asteroids.size(); i++) {
				if(asteroids.get(i).exploded) {
					if(asteroids.get(i).GetInPh()) {
						RemFromPh(asteroids.get(i));
					}
					RemoveAsteroid(asteroids.get(i));
				}
			}
			
			for(int i=0; i<phAsteroids.size(); i++) {

				if(phAsteroids.get(i).GetLayerNum() == 0) {
					if(phAsteroids.get(i).GetMaterial() != null)
						phAsteroids.get(i).GetMaterial().ExtremeHeat();
				}
					
			}
			for(Asteroid i: asteroids) {
				for(int j=0; j<i.GetNeighbours().size(); j++) {
					if(i.GetNeighbours().get(j).exploded) {
						i.GetNeighbours().remove(j);
					}
				}
				
			}
			if(MaterialCheck()) {
				Game.Instance().Lose();
			}
			else if(SettlerCheck()) {
				Game.Instance().Lose();
			}
		}
		
	}
	
	
}
