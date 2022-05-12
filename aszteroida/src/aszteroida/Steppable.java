package aszteroida;

/**
 * Az az interf�sz, ami minden olyan dolgot reprezent�l, ami az id�ben tud l�pni. Felel�s az
 * aszteroidamez�, a nap �s minden character objektum �s a megkerg�lt teleportkapuk l�p�s�nek
 * v�grehajt�s��rt.
 * @author D�lnoky
 *
 */
public interface Steppable {
	
	/**
	 * Az adott l�p�sben v�grehajtott m�velet(ek).
	 */
	public void Step();
}