package aszteroida;

public interface Drawable {
	
	/**
	 * Jelzi a GameFrame objektumnak, hogy ki kell valamit rajzolni
	 * @param s a kirajzolandˇ telepes
	 */
	public void DrawNotify(Settler s);
}
