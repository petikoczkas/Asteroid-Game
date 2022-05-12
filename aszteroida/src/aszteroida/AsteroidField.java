package aszteroida;

import java.util.ArrayList;
import java.util.Random;
/**
 * Az aszteroid�k �sszess�ge. Sz�mon tartja azokat az aszteroid�kat, melyek napk�zelben
 * vannak, tov�bb� ez az oszt�ly kezeli a j�t�kban l�v� telepeseket is, illetve elt�vol�tja a
 * felrobbant aszteroid�kat a j�t�kb�l.
 * @author D�lnoky
 *
 */
public class AsteroidField implements Steppable {
	
	/**
	 * Singleton use-hoz haszn�lt statikus instance
	 */
	private static AsteroidField instance = null;
	
	/**
	 * T�rolja a j�t�kban l�v� aszteroid�kat, azaz azokat az
	 * aszteroid�kat, melyek nem tudnak, vagy m�g nem robbantak fel. Ha egy aszteroida
	 * felrobban, azt kiveszi a t�mbb�l.
	 */
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	
	/**
	 * A napk�zelben l�v� aszteroid�k. Csak ezekre az
	 * aszteroid�kra kell elv�gezni a napk�zels�g hat�sait.
	 */
	private ArrayList<Asteroid> phAsteroids = new ArrayList<Asteroid>();
	
	/**
	 * A m�g �letben l�v� telepesek. A j�t�k v�g�nek a detekt�l�s�hoz haszn�ljuk
	 */
	private ArrayList<Settler> settlers = new ArrayList<Settler>();
	
	/**
	 * Az aszteroidamez�ben l�v� b�zis
	 */
	private Base base;
	
	private int start = 0;
	
	/**
	 * Priv�t konstruktor adott sz�m� aszteroid�val rendelkez� aszteroidamez�h�z
	 * @param astercount a kezdeti aszteroid�k sz�ma
	 */
	private AsteroidField(int astercount) {
		
			ReConst(astercount);
	}
	
	/**
	 * Az oszt�ly instance p�ld�ny�nak el�r�s�t biztos�t� met�dus 
	 * @return AsteroidField objektummal t�r vissza
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
	 * A napk�zelben l�v� aszteroid�kat lek�rdez� met�dus
	 * @return a napk�zeli aszteroid�k
	 */
	public ArrayList<Asteroid> GetPhAsteroids() {
		return phAsteroids;
	}

	/**
	 * A b�zist lek�rdez� met�dus
	 * @return a b�zis
	 */
	public Base GetBase() {
		return base;
	}
	
	public void SetBase(Base b) {
		base = b;
	}
	
	/**
	 * A nyersanyagok sz�m�t ellen�rz� met�dus, ha nincs elegend� nyersanyag a b�zis meg�p�t�s�hez akkor a j�t�knak v�ge
	 * @return ha elfogytak a kell� nyersanyagok akkor igaz egy�bk�nt hamis
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
	 * A telepesek sz�m�t ellen�rz� met�dus, ha a meghal az �sszes telepes akkor v�ge a j�t�knak
	 * @return ha minden telepes meghalt igaz egy�bk�nt hamis
	 */
	public boolean SettlerCheck() {
		if(settlers.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Az aszteroid�kat lek�rdez� met�dus
	 * @return az aktu�lis aszteroid�k list�ja
	 */
	public ArrayList<Asteroid> GetAsteroids() {
		return asteroids;
	}
	
	/**
	 * Az aszteroidamez�h�z egy aszteroid�t hozz�ad� met�dus
	 * @param a a hozz�adand� aszteroida
	 */
	public void AddAsteroid(Asteroid a) {
		asteroids.add(a);
	}
	
	/**
	 * Aszteroid�t napk�zels�gbe t�v� met�dus
	 * @param a az aszteroida amit napk�zelbe tesz
	 */
	public void AddInPh(Asteroid a) {
		phAsteroids.add(a);
		a.SetInPh(true);
	}
	
	/**
	 * Aszteroid�t napk�zels�gb�l elt�vol�t� met�dus
	 * @param a az aszteroida amit napk�zelb�l kivesz
	 */
	public void RemFromPh(Asteroid a) {
		phAsteroids.remove(a);
	}

	/**
	 * Elt�vol�t egy aszteroid�t a p�ly�r�l.(felrobban�s eset�n)
	 * @param a az aszteroida amit elt�vol�tunk
	 */
	public void RemoveAsteroid(Asteroid a) {
		asteroids.remove(a);
	}
	
	/**
	 *  M�g nem haszn�lt kezdetleges f�ggv�ny, k�s�bb a napk�zelben l�v� aszteroid�k list�j�t l�pteti
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
	 * A telepeseket lek�rdez� met�dus
	 * @return a telepesek list�ja
	 */
	public ArrayList<Settler> GetSettlers(){
		return settlers;
	}
	
	
	/**
	 * Ha meghal egy j�t�kos, akkor elt�vol�tja a j�t�kb�l.
	 * @param s az elt�vol�tand� telepes
	 */
	public void RemoveSettler(Settler s) {
		for(int i=0; i<settlers.size(); i++) {
			if(settlers.get(i).equals(s)) {
				settlers.remove(i);
			}
		}
	}
	
	/**
	 * Ez a met�dus hozza l�tre az aszteroidamez�ben tal�lhat� list�kat, v�ltoz�kat.
	 * @param astercount a kezdeti aszteroid�k sz�ma
	 */
	public void ReConst(int astercount) {
		base=new Base();
		Game.Instance();
		// l�trehozza az aszteroid�kat
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
		// be�ll�tja a szomsz�dokat
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
		// l�trehozza �s elhelyezi a telepeseket
		for(int i=0;i<Game.settlernum;i++) { 
			Settler s=new Settler();
			settlers.add(s);
		}
		for(Settler i:settlers) {
			num=new Random().nextInt(astercount);
			asteroids.get(num).Accept(i);
		}
		// l�trehozza �s elhelyezi az uf�kat
		for(int i=0;i<Game.settlernum*1.5;i++) {
			Ufo u=new Ufo();
			num=new Random().nextInt(astercount);
			asteroids.get(num).Accept(u);
			u.SetPlace(asteroids.get(num));
		}
		// be�ll�tja a kezdei napk�zeli aszteroid�kat
		int phstart=new Random().nextInt(astercount-astercount/10);
		start = phstart;
		for(int i=0;i<astercount/10;i++) {
			phAsteroids.add(asteroids.get(i+phstart));
			asteroids.get(i+phstart).SetInPh(true);
		}

	}
	
	/**
	 * Az aszteroida-mez� mozog a nap k�r�l, ennek hat�s�ra v�ltoznak a napk�zels�gek.
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
