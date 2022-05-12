package aszteroida;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Egy telepest reprezentál. A telepeseket a játékosok irányítják. Tudnak mozogni, teleportálni,
 * fúrni, bányászni, robotokat és teleportkapukat létrehozni. A kibányászott nyersanyagokat az
 * inventory-jában tárolja. Ezeket a nyersanyagokat üres aszteroidára le is rakhatja. Aszteroida
 * robbanás vagy napvihar hatására meghal.
 * @author Dálnoky
 *
 */
public class Settler extends Worker implements Drawable {
	
	/**
	 * A telepesnél lévõ, még le nem rakott, teleportkapukat tartalmazó lista.
	 */
	private ArrayList<TeleportGate> teleportGates=new ArrayList<TeleportGate>();
	
	/**
	 *  A telepes inventory-jában lévõ nyersanyagokat tartalmazó lista.
	 */
	private ArrayList<Material> inventory=new ArrayList<Material>();
	
	/**
	 * Konstruktor
	 * @param n a telepes neve
	 * @param timer a játékban lévõ idõzítõ
	 */
	public Settler() {
		super();
	}
	
	/**
	 * A telepesnél lévõ nyersanyagokat lekérdezõ metódus
	 * @return nyersanyagok listája
	 */
	public ArrayList<Material> GetInventory() {
		return inventory;
	}
	
	/**
	 * A telepes felveszi a nyersanyagot a listájába
	 * @param m a nyersanyagot
	 */
	public void AddMaterial(Material m) {
		inventory.add(m);
	}
	
	/**
	 * Létrehoz egy új robotot, azon az aszteroidán, amin éppen áll. A
	 * robot helyének beállítja ezt az aszteroidát, és az aszteroida characters listájába felveszi
	 * a robotot.
	 * @param name a telepes neve
	 * @param timer a játékban lévõ idõzítõ
	 */
	public boolean CreateRobot() {
		if(RobotCheck()) {
			return true;
		}
		return false;
	}
	
	/**
	 * A megfelelõ anyagok felhasználása mellett megépül egy teleportkapu-pár, és a telepes inventoryjában megjelenik
	 * @param name1 az egyik kapu neve
	 * @param name2 a másik kapu neve
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
	 * Kiveszi egy kifúrt aszteroida közepében lévõ nyersanyagot és azt
	 * hozzáadja a telepes inventory listájához. Ha az aszteroida üres vagy még nincs
	 * kifúrva, figyelmezteti a játékost.
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
	 * listájából, és azt berakja az üres aszteroida közepébe, amin épp a telepes áll. Ha az
	 * aszteroida nem üres, figyelmezteti a játékost.
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
	 * Lerak egy teleportkaput az aszteroida mellé, amin épp a
	 * telepes áll. A teleportkapu aszteroidájának beállítja az aszteroidát, az aszteroida
	 * teleportkapuihoz pedig hozzáadja a teleportkaput.
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
	 * A telepesnél lévõ teleportkapukat lekérdezõ nyersanyagok
	 * @return a teleportkapuk listája
	 */
	public ArrayList<TeleportGate> GetTpGates() {
		return teleportGates;
	}
	
	/**
	 * Hozzáadja a telepes teleportkapulistájához a az  épített teleportkapukat
	 * @param t1 az egyik teleportkapu
	 * @param t2 a másik teleportkapu
	 */
	public void SetTpGates(TeleportGate t1,TeleportGate t2) {
		teleportGates.add(t1);
		teleportGates.add(t2);
	}
	
	/**
	 * Az a metódus ami ellenõrzi a kellõ nyersanyagok meglétét robotépítéshez
	 * @param name az építendõ robot neve
	 * @param timer a játékban lévõ idõzítõ
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
	 * Az a metódus ami ellenõrzi a kellõ nyersanyagok meglétét teleportkapupár építéshez
	 * @param name1 az egyik kapu neve
	 * @param name2 a másik kapu neve
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
	 * A telepes egy lépését végrehajtó metódus
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
