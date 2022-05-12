package aszteroida;

import java.util.ArrayList;
import java.util.Random;

/**
 * Egy robotot reprezentál. A robotokat mesterséges intelligencia irányítja. Tudnak mozogni,
 * teleportálni, fúrni. Aszteroida robbanás hatására átkerül egy véletlen kiválasztott szomszédos
 * aszteroidára. Napvihar hatására tönkremegy.
 * @author Dálnoky
 *
 */
public class Robot extends Worker {
	
	/**
	 * Konstruktor
	 * @param n a robot neve
	 * @param t a játékban lévõ idõzítõ
	 */
	public Robot() {
		super();
	}
	
	/**
	 * A robot választ egy véletlen helyet a helyének szomszédjai közül, és átmegy arra.
	 */
	@Override
	public void Explodes() {
		ArrayList<Asteroid> a=place.GetNeighbours();
		Random r=new Random();
		int idx=r.nextInt(a.size());
		Move(a.get(idx));
	}
	/**
	 * Ha a robot olyan aszteroidán van, ami még nincs kifúrva, akkor fúrást
	 * végez, ha pedig az aszteroida már ki van fúrva, akkor átmegy egy véletlen szomszédos
	 * aszteroidára
	 */
	@Override
	public void Step() {
		if(place.GetLayerNum()>0) {
			Drill();
		}
		else {
			ArrayList<Asteroid> a=place.GetNeighbours();
			Random r=new Random();
			int idx=r.nextInt(a.size());
			Move(a.get(idx));
		}
	}
}
