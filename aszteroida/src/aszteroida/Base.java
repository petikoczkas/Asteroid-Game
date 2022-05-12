package aszteroida;

import java.util.ArrayList;
/**
 * Azt az aszteroid�t reprezent�lja, amire a b�zist meg lehet �p�teni.
 * @author D�lnoky
 *
 */
public class Base extends Asteroid{

	/**
	 * Egy materialokat tartalmaz� lista. Ebbe gy�jthetik a j�t�kosok a
	 * b�zis meg�p�t�s�hez sz�ks�ges nyersanyagokat.
	 */
	private ArrayList <Material> container=new ArrayList<Material>();
	
	/**
	 * Konstruktor
	 */
	public Base() {
		super();
	}
	
	
	/**
	 * Berakja a megadott nyersanyagot a container list�j�ba.
	 * @param m a nyersanyag amit a t�rol�ba tesz
	 * @return a t�rol�ba t�tel sikeress�ge
	 */
	public boolean AcceptMaterial(Material m) {
		container.add(m);
		return true;
	}
	
	
	/**
	 * Egy adott nyersanyagot a t�rol�ban megsz�mol� met�dus
	 * @param s a nyersanyag neve
	 * @return a ha megvan a kell� mennyis�g akkor 1 ha nincs akkor 0
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
	 * Leellen�rzi hogy �sszegy�lt-e m�r a b�zis meg�p�t�s�hez sz�ks�ges mennyis�g� nyersanyag
	 * @param g A j�t�k
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
	 * A b�zis�p�t�shez eddig �sszegy�jt�tt nyersanyagokat lek�rdez� met�dus
	 * @return a t�rol�ban l�v� nyersanyagok
	 */
	public ArrayList<Material> GetContainer() {
		return container;
	}
	
	/**
	 * Megvizsg�lja, hogy teljes�ltek-e a gy�zelem felt�telei
	 * @return Igazal t�r vissza, ha nyertek a j�t�kosok, hamissal ha nem
	 */
	public boolean CheckWin() {
		return CheckMaterials();
	}
	
}
