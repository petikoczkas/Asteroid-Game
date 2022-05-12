package aszteroida;

import java.util.ArrayList;

/**
 * Az aszteroidaövben egy aszteroidát reprezentáló osztály. Tárolja a magjában lévõ
 * nyersanyagot, a szomszédos aszteroidákat, a rajta lévõ teleportkapukat és a rajta lévõ
 * karaktereket.
 * @author Dálnoky
 *
 */
public class Asteroid extends Place {

	/**
	 * Az aszteroida kérgének vastagságát tárolja.
	 */
	private int layerNum;
	
	/**
	 * :Az aszteroidában lévõ nyersanyag
	 */
	private Material material;
	
	/**
	 * Az aszteroidával szomszédos aszteroidák listája
	 */
	private ArrayList <Asteroid> neighbours=new ArrayList<Asteroid>();
	
	/**
	 * Az aszteroidára letett teleportkapuk listája
	 */
	private ArrayList<TeleportGate> tpgate=new ArrayList<TeleportGate>();
	
	/**
	 * Az aszterioda napközelségét tároló változó
	 */
	private boolean inPh=false;
	
	/**
	 * Tárolja, hogy az aszteroida felrobbant-e
	 */
	public boolean exploded=false;
	
	public Asteroid() {
		layerNum = 0;
	}
	
	/**
	 * Konstruktor adott kompozicióju aszteroida létrehozásához
	 * @param n az aszteroida neve
	 * @param mat az aszteroida magjában lévõ nyersanyag
	 * @param layer az aszteroida kérgének vastagsága
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
	 * Getter metódus a teleportkapukhoz
	 * @return az szteroidán lévõ teleportkapuk listája
	 */
	public ArrayList<TeleportGate> GetTpgate() {
		return tpgate;
	}

	/**
	 * A napközelséget beállító metódus
	 * @param b a npközelség állapota
	 */
	public void SetInPh(boolean b) {
		inPh=b;
	}
	
	/**
	 * A napközelséget visszaadó metódus
	 */
	public boolean GetInPh() {
		return inPh;
	}
	
	/**
	 * Beállítja, hogy az adaott aszteroidának milyen vastag a kérge
	 * @param l a megadott vastagság
	 */
	public void SetLayerNum(int l) {
		layerNum=l;
		
		if(l==0 && inPh==true && material != null)
		{
			material.ExtremeHeat();
		}
	}
	
	/**
	 * Getter metódus az aszteroida kéregvastagságához
	 * @return a kéregvastagság
	 */
	public int GetLayerNum() {
		return layerNum;
	}
	/**
	 * Az aszteroida magjában lévõ nyersanyagnak a lekérdezése
	 * @return a magban lévõ nyersanyag
	 */
	public Material GetMaterial() {
		return material;
	}
	
	/**
	 * Az aszteroida magjában lévõ nyersanyag beállítása
	 * @param m a beállítandó nyersanyag
	 */
	public void SetMaterial(Material m) {
		material=m;
		if(material!=null) m.SetAsteroid(this);
	}
	
	/**
	 * Az üres aszteroidába való nyersanyag visszatételt megkísérlõ metódus
	 * @param m a letenni kívánt nyersanyag
	 * @return a letétel sikeressége
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
	 * Az aszteroida ellenõrzi, hogy elérhetõ e másik aszteroida róla,
	 * ha nem az ekvivalens az aszteroida felrobbanásával.
	 * @return a szomszédok nemlétezésének vagy létezésének ténye
	 */
	public boolean CheckNeighbours() {
		if(neighbours.size()==0) {
			return false;
		}
		else return true;
	}
	
	/**
	 * Az aszteroidában lévõ uránium végsõ alkalommal is napközelbe
	 * került ezért az aszteroida felrobban
	 */
	public void Explode() {
		for(int i=0; i<GetCharacters().size(); i++) {
			characters.get(i).Explodes();
		}
		exploded=true;
		
	}
	
	/**
	 * Az aszteroidára egy telepes teleportkaput rak le
	 * @param t az lerakandó teleportkapu
	 */
	public void SetTpGate(TeleportGate t) {
		 tpgate.add(t);
		 
	}
	/**
	 * Getter metódus a szomszédos aszteroidák lekérdezéséhez
	 * @return a szomszédok listája
	 */
	public ArrayList<Asteroid> GetNeighbours() {
		return neighbours;
	}
	/**
	 * Szomszédos aszteroida beállítására alkalmas metódus
	 * @param a szomszédos aszteroida
	 */
	public void SetNeighbour(Asteroid a) {
		neighbours.add(a);
		a.GetNeighbours().add(this);
	}
	
	
}
