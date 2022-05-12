package aszteroida;

import java.util.ArrayList;
/**
 * Azt az aszteroidát reprezentálja, amire a bázist meg lehet építeni.
 * @author Dálnoky
 *
 */
public class Base extends Asteroid{

	/**
	 * Egy materialokat tartalmazó lista. Ebbe gyûjthetik a játékosok a
	 * bázis megépítéséhez szükséges nyersanyagokat.
	 */
	private ArrayList <Material> container=new ArrayList<Material>();
	
	/**
	 * Konstruktor
	 */
	public Base() {
		super();
	}
	
	
	/**
	 * Berakja a megadott nyersanyagot a container listájába.
	 * @param m a nyersanyag amit a tárolóba tesz
	 * @return a tárolóba tétel sikeressége
	 */
	public boolean AcceptMaterial(Material m) {
		container.add(m);
		return true;
	}
	
	
	/**
	 * Egy adott nyersanyagot a tárolóban megszámoló metódus
	 * @param s a nyersanyag neve
	 * @return a ha megvan a kellõ mennyiség akkor 1 ha nincs akkor 0
	 */
	private int Check(String s) {
		int n=0;
		for(int i=0; i<container.size(); i++) {
			if(s.equals(container.get(i).getClass().getSimpleName())) {
				n++;
			}
		}
		if(n>=3) return 1;
		else return 0;
	}
	
	/**
	 * Leellenõrzi hogy összegyûlt-e már a bázis megépítéséhez szükséges mennyiségû nyersanyag
	 * @param g A játék
 	 */
	private boolean CheckMaterials() {
		int n=0;
		n+=Check("Aluminium");
		n+=Check("Carbon");
		n+=Check("Copper");
		n+=Check("Gold");
		n+=Check("Ice");
		n+=Check("Iron");
		n+=Check("Uranium");
		if(n==7) {
			Game.Instance().Win();
			return true;
		}
		return false;
	}

	/**
	 * A bázisépítéshez eddig összegyûjtött nyersanyagokat lekérdezõ metódus
	 * @return a tárolóban lévõ nyersanyagok
	 */
	public ArrayList<Material> GetContainer() {
		return container;
	}
	
	/**
	 * Megvizsgálja, hogy teljesültek-e a gyõzelem feltételei
	 * @return Igazal tér vissza, ha nyertek a játékosok, hamissal ha nem
	 */
	public boolean CheckWin() {
		return CheckMaterials();
	}
	
}
