package aszteroida;

/**
 * A j�t�kban l�v� karaktereket (Settler, Robot, Ufo) reprezent�l� oszt�lyt
 * @author D�lnoky
 *
 */
public abstract class Character implements Steppable {
	
	/**
	 * Jelzi, hogy halott-e a karakter
	 */
	boolean dead=false;
	/**
	 * A karakter pillanatnyi helyzet��l szolg�l� aszteroida vagy teleportkapu
	 */
	protected Place place;
	
	/**
	 * Konstruktor
	 * @param timer a j�t�kban l�v� id�z�t�
	 */
	public Character() {
		Timer.Instance().AddSteppable(this);
	}
	
	
	/**
	 * Be�ll�tja a tartozkod�si hely�t a karakternek
	 * @param p a tart�zkod�si hely
	 */
	public void SetPlace(Place p) {
		place=p;
	}
	
	/**
	 * Visszaadja a tartozkod�si hely�t a workernek
	 * @return a tart�zkod�si hely
	 */
	public Place GetPlace() {
		return place;
	}
	
	/**
	 * A param�ter�l kapott place-re ker�l a karakter
	 * @param p a mozg�s c�lhelye
	 */
	public void  Move(Place p) {
		place.Remove(this);
		p.Accept(this);
	}
	
	/**
	 * A karakter, ha Settler-r�l vagy Ufo-r�l van sz� akkor meghal, ha pedig Robot-r�l akkor elromlik 
	 */
	public void Die() {
		place.GetCharacters().remove(this);
		dead=true;
	}
	
	/**
	 * Az aszteroida, amin a character �ll felrobban ez�rt a character-rel
	 * t�rt�nik valami. Settler �s Ufo eset�ben meghal, Robot eset�ben pedig �tker�l egy
	 * v�letlenszer� szomsz�dos aszteroid�ra.
	 */
	public void Explodes() {
		Die();
	}
	
	/**
	 * A karakter elv�gez egy m�veletet
	 */
	@Override
	public void Step() {
		
	}

}
