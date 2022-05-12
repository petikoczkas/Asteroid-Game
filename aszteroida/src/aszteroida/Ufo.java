package aszteroida;

/**
 * Az aszteroidamezõben ólálkodó tolvaj ûrlényeket reprezentálja, akik a már kifúrt
 * aszteroidákból veszik ki a nyersanyagokat a telepesek elõl.
 * @author Dálnoky
 *
 */
public class Ufo extends Character {
	
	private boolean crosstp = true;
	
	/**
	 * Konstruktor
	 * @param n az ufo neve
	 * @param timer a játék idõzítõje
	 */
	public Ufo() {
		super();
	}
	
	/**
	 * A teljesen átfúrt aszteroidából az ûrlény kibányássza a nyersanyagot, vagyis az
	 * aszteroida üregessé válik. A nyersanyag kitörlõdik a játékból.
	 */
	public void Steal() {
		if(place.GetLayerNum() == 0 && place.GetMaterial() != null)
			place.SetMaterial(null);
	}
	
	
	/**
	 * Az Ufo egy lépését végreható metódus
	 * Ha van lehetõsége lopni megteszi, különben átlép a legkedvezõbb szomszédos aszteroidára, vagy egy teleportkapura. Ha nincs hova lépnie meghal
	 */
	@Override
	public void Step() {
		int min = 7;
		// ha aszteroidán áll
		if(place.GetNeighbours() != null) {
			if(((Asteroid)place).CheckNeighbours()) {
				Asteroid temp = ((Asteroid)place).GetNeighbours().get(0);
				TeleportGate tempgate = null;
				if(place.GetLayerNum() == 0 && ((Asteroid)place).GetMaterial() != null) {
					Steal();
					return;
				}
				else {
					for(Asteroid p : ((Asteroid)place).GetNeighbours()) {
						if(p.GetLayerNum() < min && p.GetMaterial() != null) {
							min = p.GetLayerNum();
							temp = p;
							if(p.GetTpgate().size() > 0) tempgate = p.GetTpgate().get(0);
						}
					}	
				}
				if(min > 0 && tempgate != null) {
					Move(tempgate);
					return;
				}
				Move(temp);
			}
			else {
				Die();
			}
		}
		// ha teleportkapun
		else {
			if(crosstp) {
				Move(((TeleportGate)place).GetPair());
			}
			else {
				Move(((TeleportGate)place).GetAsteroid());
			}
			crosstp = !crosstp;
		}
	}
}
