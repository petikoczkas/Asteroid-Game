package aszteroida;

/**
 * Az az interfész, ami minden olyan dolgot reprezentál, ami az idõben tud lépni. Felelõs az
 * aszteroidamezõ, a nap és minden character objektum és a megkergült teleportkapuk lépésének
 * végrehajtásáért.
 * @author Dálnoky
 *
 */
public interface Steppable {
	
	/**
	 * Az adott lépésben végrehajtott mûvelet(ek).
	 */
	public void Step();
}