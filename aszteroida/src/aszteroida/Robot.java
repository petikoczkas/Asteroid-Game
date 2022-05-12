package aszteroida;

import java.util.ArrayList;
import java.util.Random;

/**
 * Egy robotot reprezent�l. A robotokat mesters�ges intelligencia ir�ny�tja. Tudnak mozogni,
 * teleport�lni, f�rni. Aszteroida robban�s hat�s�ra �tker�l egy v�letlen kiv�lasztott szomsz�dos
 * aszteroid�ra. Napvihar hat�s�ra t�nkremegy.
 * @author D�lnoky
 *
 */
public class Robot extends Worker {
	
	/**
	 * Konstruktor
	 * @param n a robot neve
	 * @param t a j�t�kban l�v� id�z�t�
	 */
	public Robot() {
		super();
	}
	
	/**
	 * A robot v�laszt egy v�letlen helyet a hely�nek szomsz�djai k�z�l, �s �tmegy arra.
	 */
	@Override
	public void Explodes() {
		ArrayList<Asteroid> a=place.GetNeighbours();
		Random r=new Random();
		int idx=r.nextInt(a.size());
		Move(a.get(idx));
	}
	/**
	 * Ha a robot olyan aszteroid�n van, ami m�g nincs kif�rva, akkor f�r�st
	 * v�gez, ha pedig az aszteroida m�r ki van f�rva, akkor �tmegy egy v�letlen szomsz�dos
	 * aszteroid�ra
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
