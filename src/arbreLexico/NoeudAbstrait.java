package arbreLexico;

public abstract class NoeudAbstrait {
	protected NoeudAbstrait frere;

	public NoeudAbstrait(NoeudAbstrait frere) {
		if (frere == null && !(this instanceof NoeudVide))
			throw new IllegalArgumentException("frere null interdit");
		this.frere = frere;
	}
	
	public abstract boolean contient(String s);
	public abstract boolean prefixe(String s);
	public abstract int nbMots();
	public abstract NoeudAbstrait ajout(String s);
	public abstract NoeudAbstrait suppr(String s);
	public abstract String toString(String s);

	public static void main(String[] args) {
		NoeudAbstrait entree = new Marque(null);
		entree = entree.ajout("toto");
		entree = entree.ajout("dupont");
		entree = entree.ajout("totoche");
		entree = entree.ajout("to");
		entree = entree.ajout("totochee");
		entree = entree.ajout("tot");
		entree = entree.ajout("titi");
		entree = entree.ajout("tata");
		entree = entree.ajout("tomate");
		System.out.println(">>>" + entree.toString("") + "<<<<<");
		entree = entree.suppr("tot");
		entree = entree.suppr("");
		entree = entree.suppr("toto");
		System.out.println(">>>" + entree.toString("") + "<<<<<");


	}

}
