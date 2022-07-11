package arbreLexico;

public class ArbreLexicographique {
	private NoeudAbstrait entree;

	public ArbreLexicographique() {
		entree = new NoeudVide();
	}

	public boolean contient(String s) {
		return entree.contient(s);
	}

	public boolean prefixe(String s) {
		return entree.prefixe(s);
	}

	public int nbMots() {
		return entree.nbMots();
	}

	public boolean ajout(String s) {
		try {
			entree = entree.ajout(s);
		} catch (ModificationImpossibleException e) {
			return false;
		}
		return true;
	}

	public boolean suppr(String s) {
		try {
			entree = entree.suppr(s);
		} catch (ModificationImpossibleException e) {
			return false;
		}
		return true;
	}

	public String toString() {
		return entree.toString("");
	}

	public static void main(String[] args) {
		ArbreLexicographique al = new ArbreLexicographique();
		System.out.println(al.ajout("toto"));
		System.out.println(al.ajout("dupont"));
		System.out.println(al.ajout("totoche"));
		System.out.println(al.ajout("to"));
		System.out.println(al.ajout("totochee"));
		System.out.println(al.ajout("toto"));
		System.out.println(al.ajout("tot"));
		System.out.println(al.ajout("titi"));
		System.out.println(al.ajout("tata"));
		System.out.println(al.ajout("tomate"));
		System.out.println(al.ajout("tomate"));
		System.out.println(">>>" + al + "<<<<<");
		System.out.println(al.suppr("tot"));
		System.out.println(al.suppr(""));
		System.out.println(al.suppr("toto"));
		System.out.println(">>>" + al + "<<<<<");
	}

}
