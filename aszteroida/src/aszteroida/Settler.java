package aszteroida;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Egy telepest reprezent�l. A telepeseket a j�t�kosok ir�ny�tj�k. Tudnak mozogni, teleport�lni,
 * f�rni, b�ny�szni, robotokat �s teleportkapukat l�trehozni. A kib�ny�szott nyersanyagokat az
 * inventory-j�ban t�rolja. Ezeket a nyersanyagokat �res aszteroid�ra le is rakhatja. Aszteroida
 * robban�s vagy napvihar hat�s�ra meghal.
 * @author D�lnoky
 *
 */
public class Settler extends Worker implements Drawable {
	
	/**
	 * A telepesn�l l�v�, m�g le nem rakott, teleportkapukat tartalmaz� lista.
	 */
	private ArrayList<TeleportGate> teleportGates=new ArrayList<TeleportGate>();
	
	/**
	 *  A telepes inventory-j�ban l�v� nyersanyagokat tartalmaz� lista.
	 */
	private ArrayList<Material> inventory=new ArrayList<Material>();
	
	/**
	 * Konstruktor
	 * @param n a telepes neve
	 * @param timer a j�t�kban l�v� id�z�t�
	 */
	public Settler() {
		super();
	}
	
	/**
	 * A telepesn�l l�v� nyersanyagokat lek�rdez� met�dus
	 * @return nyersanyagok list�ja
	 */
	public ArrayList<Material> GetInventory() {
		return inventory;
	}
	
	/**
	 * A telepes felveszi a nyersanyagot a list�j�ba
	 * @param m a nyersanyagot
	 */
	public void AddMaterial(Material m) {
		inventory.add(m);
	}
	
	/**
	 * L�trehoz egy �j robotot, azon az aszteroid�n, amin �ppen �ll. A
	 * robot hely�nek be�ll�tja ezt az aszteroid�t, �s az aszteroida characters list�j�ba felveszi
	 * a robotot.
	 * @param name a telepes neve
	 * @param timer a j�t�kban l�v� id�z�t�
	 */
	public boolean CreateRobot() {
		if(RobotCheck()) {
			return true;
		}
		return false;
	}
	
	/**
	 * A megfelel� anyagok felhaszn�l�sa mellett meg�p�l egy teleportkapu-p�r, �s a telepes inventoryj�ban megjelenik
	 * @param name1 az egyik kapu neve
	 * @param name2 a m�sik kapu neve
	 */
	public boolean CreateTpGates() {
		if(teleportGates.size()<2) {
			if(TpGateCheck()) {
				return true;
			}
		}
		else
		{
			
			GameFrame.Instance().Message("Not enough space for TpGate creation");
		}
		return false;
	}
	
	/**
	 * Kiveszi egy kif�rt aszteroida k�zep�ben l�v� nyersanyagot �s azt
	 * hozz�adja a telepes inventory list�j�hoz. Ha az aszteroida �res vagy m�g nincs
	 * kif�rva, figyelmezteti a j�t�kost.
	 */
	public boolean Mine() {
		if(inventory.size()<10 && place.GetMaterial()!=null && place.GetLayerNum()==0) {
			AddMaterial(place.GetMaterial());
			place.SetMaterial(null);
			return true;
		}
		else if(place.GetLayerNum()>0) {
			GameFrame.Instance().Message("Asteroid is not drilled");
			return false;
		}
		else if(place.GetMaterial()==null){
			GameFrame.Instance().Message("Asteroid is empty");
			return false;
		}
		else {
			GameFrame.Instance().Message("Settlers inventory is full");
			return false;
		}
	}
	
	/**
	 * Kivesz egy megadott nyersanyagot a telepes inventory
	 * list�j�b�l, �s azt berakja az �res aszteroida k�zep�be, amin �pp a telepes �ll. Ha az
	 * aszteroida nem �res, figyelmezteti a j�t�kost.
	 * @param m az adott nyersanyag
	 */
	public boolean Remove(Material m) {
		if(GetPlace().AcceptMaterial(m)) {
			inventory.remove(m);
			return true;
		}
		else{
			GameFrame.Instance().Message("Can't place material in not empty asteroid");
			return false;
		}
	}

	
	/**
	 * Lerak egy teleportkaput az aszteroida mell�, amin �pp a
	 * telepes �ll. A teleportkapu aszteroid�j�nak be�ll�tja az aszteroid�t, az aszteroida
	 * teleportkapuihoz pedig hozz�adja a teleportkaput.
	 * @param tp a teleportkapu
	 */
	public void PlaceTpGate(TeleportGate tp) {
		if(!teleportGates.isEmpty()) {
			tp.SetAsteroid((Asteroid)place);
			teleportGates.remove(tp);
		}
		else
			GameFrame.Instance().Message("There are no TeleportGates");
	}
	
	/**
	 * A telepesn�l l�v� teleportkapukat lek�rdez� nyersanyagok
	 * @return a teleportkapuk list�ja
	 */
	public ArrayList<TeleportGate> GetTpGates() {
		return teleportGates;
	}
	
	/**
	 * Hozz�adja a telepes teleportkapulist�j�hoz a az  �p�tett teleportkapukat
	 * @param t1 az egyik teleportkapu
	 * @param t2 a m�sik teleportkapu
	 */
	public void SetTpGates(TeleportGate t1,TeleportGate t2) {
		teleportGates.add(t1);
		teleportGates.add(t2);
	}
	
	/**
	 * Az a met�dus ami ellen�rzi a kell� nyersanyagok megl�t�t robot�p�t�shez
	 * @param name az �p�tend� robot neve
	 * @param timer a j�t�kban l�v� id�z�t�
	 */
	private boolean RobotCheck() {
		int[] rcp  = { 0,0,0 };
		Material[] temp = {null,null,null};
		
		for(Material i: inventory) {
			if(i.getClass().getSimpleName().equals("Iron")) rcp[0]++;
			if(i.getClass().getSimpleName().equals("Carbon")) rcp[1]++;
			if(i.getClass().getSimpleName().equals("Uranium")) rcp[2]++;
		}
		
		if(rcp[0] > 0 && rcp[1] > 0 && rcp[2] > 0) {
			for(Material i : inventory) {
				if(i.getClass().getSimpleName().equals("Iron") && rcp[0] > 0) {
					temp[0] = i;
					rcp[0]--;
				}
				if(i.getClass().getSimpleName().equals("Carbon") && rcp[1] > 0) {
					temp[1] = i;
					rcp[1]--;
				}
				if(i.getClass().getSimpleName().equals("Uranium") && rcp[2] > 0) {
					temp[2] = i;
					rcp[2]--;
				}
			}
			inventory.remove(temp[0]);
			inventory.remove(temp[1]);
			inventory.remove(temp[2]);
			Robot r = new Robot();
			r.SetPlace(place);
			place.GetCharacters().add(r);
			return true;
		}
		else{
			GameFrame.Instance().Message("Not enough material for robot creation");
			return false;
		}
	}
	
	/**
	 * Az a met�dus ami ellen�rzi a kell� nyersanyagok megl�t�t teleportkapup�r �p�t�shez
	 * @param name1 az egyik kapu neve
	 * @param name2 a m�sik kapu neve
	 */
	private boolean TpGateCheck() {
		int rcp[] = { 0,0,0 };
		Material[] temp = {null,null,null,null};
		for(Material i : inventory) {
			if(i.getClass().getSimpleName().equals("Iron")) rcp[0]++;
			if(i.getClass().getSimpleName().equals("Ice")) rcp[1]++;
			if(i.getClass().getSimpleName().equals("Uranium")) rcp[2]++;
		}
		
		if(rcp[0] > 1 && rcp[1] > 0 && rcp[2] > 0) {
			for(Material i : inventory) {
				if(i.getClass().getSimpleName().equals("Iron") && rcp[0] > 0) {
					if(rcp[0] == 2) temp[0] = i; 
					else temp[1] = i; 
					rcp[0]--;
				}
				if(i.getClass().getSimpleName().equals("Ice") && rcp[1] > 0) {
					temp[2] = i;
					rcp[1]--;
				}
				if(i.getClass().getSimpleName().equals("Uranium") && rcp[2] > 0) {
					temp[3] = i;
					rcp[2]--;
				}
			}
			for(int i = 0; i < 4; i++)
				inventory.remove(temp[i]);
			
			TeleportGate t1=new TeleportGate();
			TeleportGate t2=new TeleportGate();
			SetTpGates(t1,t2);
			t1.SetPair(t2);
			t2.SetPair(t1);
			return true;
		}
		else{
			GameFrame.Instance().Message("Not enough material for tpgate creation");
			return false;
		}
	}
	
	/**
	 * A telepes egy l�p�s�t v�grehajt� met�dus
	 */
	@Override
	public void Step() {
		DrawNotify(this);
		GameFrame.Instance().Message("");
	}
	
	@Override
	public void Die() {
		place.GetCharacters().remove(this);
		dead=true;
		Timer.Instance().GetSteppable().remove(this);
	}

	@Override
	public void DrawNotify(Settler s) {
		GameFrame.Instance().DrawStatus(this);
	}
}
