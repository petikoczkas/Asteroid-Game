package aszteroida;

/**
 * A játékban lévõ karaktereket (Settler, Robot, Ufo) reprezentáló osztályt
 * @author Dálnoky
 *
 */
public abstract class Character implements Steppable {
	
	/**
	 * Jelzi, hogy halott-e a karakter
	 */
	boolean dead=false;
	/**
	 * A karakter pillanatnyi helyzetéül szolgáló aszteroida vagy teleportkapu
	 */
	protected Place place;
	
	/**
	 * Konstruktor
	 * @param timer a játékban lévõ idõzítõ
	 */
	public Character() {
		Timer.Instance().AddSteppable(this);
	}
	
	
	/**
	 * Beállítja a tartozkodási helyét a karakternek
	 * @param p a tartózkodási hely
	 */
	public void SetPlace(Place p) {
		place=p;
	}
	
	/**
	 * Visszaadja a tartozkodási helyét a workernek
	 * @return a tartózkodási hely
	 */
	public Place GetPlace() {
		return place;
	}
	
	/**
	 * A paraméterül kapott place-re kerül a karakter
	 * @param p a mozgás célhelye
	 */
	public void  Move(Place p) {
		place.Remove(this);
		p.Accept(this);
	}
	
	/**
	 * A karakter, ha Settler-rõl vagy Ufo-ról van szó akkor meghal, ha pedig Robot-ról akkor elromlik 
	 */
	public void Die() {
		place.GetCharacters().remove(this);
		dead=true;
	}
	
	/**
	 * Az aszteroida, amin a character áll felrobban ezért a character-rel
	 * történik valami. Settler és Ufo esetében meghal, Robot esetében pedig átkerül egy
	 * véletlenszerû szomszédos aszteroidára.
	 */
	public void Explodes() {
		Die();
	}
	
	/**
	 * A karakter elvégez egy müveletet
	 */
	@Override
	public void Step() {
		
	}

}
