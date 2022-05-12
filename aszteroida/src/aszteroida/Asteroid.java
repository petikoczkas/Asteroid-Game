package aszteroida;

import java.util.ArrayList;

/**
 * Az aszteroida�vben egy aszteroid�t reprezent�l� oszt�ly. T�rolja a magj�ban l�v�
 * nyersanyagot, a szomsz�dos aszteroid�kat, a rajta l�v� teleportkapukat �s a rajta l�v�
 * karaktereket.
 * @author D�lnoky
 *
 */
public class Asteroid extends Place {

	/**
	 * Az aszteroida k�rg�nek vastags�g�t t�rolja.
	 */
	private int layerNum;
	
	/**
	 * :Az aszteroid�ban l�v� nyersanyag
	 */
	private Material material;
	
	/**
	 * Az aszteroid�val szomsz�dos aszteroid�k list�ja
	 */
	private ArrayList <Asteroid> neighbours=new ArrayList<Asteroid>();
	
	/**
	 * Az aszteroid�ra letett teleportkapuk list�ja
	 */
	private ArrayList<TeleportGate> tpgate=new ArrayList<TeleportGate>();
	
	/**
	 * Az aszterioda napk�zels�g�t t�rol� v�ltoz�
	 */
	private boolean inPh=false;
	
	/**
	 * T�rolja, hogy az aszteroida felrobbant-e
	 */
	public boolean exploded=false;
	
	public Asteroid() {
		layerNum = 0;
	}
	
	/**
	 * Konstruktor adott kompozici�ju aszteroida l�trehoz�s�hoz
	 * @param n az aszteroida neve
	 * @param mat az aszteroida magj�ban l�v� nyersanyag
	 * @param layer az aszteroida k�rg�nek vastags�ga
	 */
	public Asteroid(int mat, int layer) {
		layerNum = layer;
		switch(mat) {
			case 0 : material = null;
				break;
			case 1: material = new Ice(this);
				break;
			case 2: material = new Uranium(this);
				break;
			case 3: material = new Aluminium(this);
				break;
			case 4: material = new Carbon(this);
				break;
			case 5: material = new Copper(this);
				break;
			case 6: material = new Gold(this);
				break;
			case 7: material = new Iron(this);
				break;
			default : material = null;break;
		}
	}
	
	/**
	 * Getter met�dus a teleportkapukhoz
	 * @return az szteroid�n l�v� teleportkapuk list�ja
	 */
	public ArrayList<TeleportGate> GetTpgate() {
		return tpgate;
	}

	/**
	 * A napk�zels�get be�ll�t� met�dus
	 * @param b a npk�zels�g �llapota
	 */
	public void SetInPh(boolean b) {
		inPh=b;
	}
	
	/**
	 * A napk�zels�get visszaad� met�dus
	 */
	public boolean GetInPh() {
		return inPh;
	}
	
	/**
	 * Be�ll�tja, hogy az adaott aszteroid�nak milyen vastag a k�rge
	 * @param l a megadott vastags�g
	 */
	public void SetLayerNum(int l) {
		layerNum=l;
		
		if(l==0 && inPh==true && material != null)
		{
			material.ExtremeHeat();
		}
	}
	
	/**
	 * Getter met�dus az aszteroida k�regvastags�g�hoz
	 * @return a k�regvastags�g
	 */
	public int GetLayerNum() {
		return layerNum;
	}
	/**
	 * Az aszteroida magj�ban l�v� nyersanyagnak a lek�rdez�se
	 * @return a magban l�v� nyersanyag
	 */
	public Material GetMaterial() {
		return material;
	}
	
	/**
	 * Az aszteroida magj�ban l�v� nyersanyag be�ll�t�sa
	 * @param m a be�ll�tand� nyersanyag
	 */
	public void SetMaterial(Material m) {
		material=m;
		if(material!=null) m.SetAsteroid(this);
	}
	
	/**
	 * Az �res aszteroid�ba val� nyersanyag visszat�telt megk�s�rl� met�dus
	 * @param m a letenni k�v�nt nyersanyag
	 * @return a let�tel sikeress�ge
	 */
	public boolean AcceptMaterial(Material m) {
		if(layerNum==0 && material==null) {
			material=m;
			if(material!=null) material.SetAsteroid(this);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Az aszteroida ellen�rzi, hogy el�rhet� e m�sik aszteroida r�la,
	 * ha nem az ekvivalens az aszteroida felrobban�s�val.
	 * @return a szomsz�dok neml�tez�s�nek vagy l�tez�s�nek t�nye
	 */
	public boolean CheckNeighbours() {
		if(neighbours.size()==0) {
			return false;
		}
		else return true;
	}
	
	/**
	 * Az aszteroid�ban l�v� ur�nium v�gs� alkalommal is napk�zelbe
	 * ker�lt ez�rt az aszteroida felrobban
	 */
	public void Explode() {
		for(int i=0; i<GetCharacters().size(); i++) {
			characters.get(i).Explodes();
		}
		exploded=true;
		
	}
	
	/**
	 * Az aszteroid�ra egy telepes teleportkaput rak le
	 * @param t az lerakand� teleportkapu
	 */
	public void SetTpGate(TeleportGate t) {
		 tpgate.add(t);
		 
	}
	/**
	 * Getter met�dus a szomsz�dos aszteroid�k lek�rdez�s�hez
	 * @return a szomsz�dok list�ja
	 */
	public ArrayList<Asteroid> GetNeighbours() {
		return neighbours;
	}
	/**
	 * Szomsz�dos aszteroida be�ll�t�s�ra alkalmas met�dus
	 * @param a szomsz�dos aszteroida
	 */
	public void SetNeighbour(Asteroid a) {
		neighbours.add(a);
		a.GetNeighbours().add(this);
	}
	
	
}
