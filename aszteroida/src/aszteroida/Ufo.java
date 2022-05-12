package aszteroida;

/**
 * Az aszteroidamez�ben �l�lkod� tolvaj �rl�nyeket reprezent�lja, akik a m�r kif�rt
 * aszteroid�kb�l veszik ki a nyersanyagokat a telepesek el�l.
 * @author D�lnoky
 *
 */
public class Ufo extends Character {
	
	private boolean crosstp = true;
	
	/**
	 * Konstruktor
	 * @param n az ufo neve
	 * @param timer a j�t�k id�z�t�je
	 */
	public Ufo() {
		super();
	}
	
	/**
	 * A teljesen �tf�rt aszteroid�b�l az �rl�ny kib�ny�ssza a nyersanyagot, vagyis az
	 * aszteroida �regess� v�lik. A nyersanyag kit�rl�dik a j�t�kb�l.
	 */
	public void Steal() {
		if(place.GetLayerNum() == 0 && place.GetMaterial() != null)
			place.SetMaterial(null);
	}
	
	
	/**
	 * Az Ufo egy l�p�s�t v�grehat� met�dus
	 * Ha van lehet�s�ge lopni megteszi, k�l�nben �tl�p a legkedvez�bb szomsz�dos aszteroid�ra, vagy egy teleportkapura. Ha nincs hova l�pnie meghal
	 */
	@Override
	public void Step() {
		int min = 7;
		// ha aszteroid�n �ll
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
