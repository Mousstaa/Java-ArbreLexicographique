package arbreLexico;

public class NoeudVide extends NoeudAbstrait {

	public NoeudVide() {
		super(null);
	}

	public boolean contient(String s) {
		return false;
	}

	public boolean prefixe(String s) {
		return false;
	}

	public int nbMots() {
		return 0;
	}

	public NoeudAbstrait ajout(String s) {
		NoeudAbstrait n = new Marque(new NoeudVide());
		for (int i = s.length() - 1 ; i >= 0; i --)
			n = new Noeud(new NoeudVide(), n , s.charAt(i));
		return n;
	}

	public NoeudAbstrait suppr(String s) {
		throw new ModificationImpossibleException("Suppression impossible");
	}

	public String toString(String s) {
		return "";
	}

	public static void main(String[] args) {

	}

}
