package arbreLexico;

public class Noeud extends NoeudAbstrait {
	private char valeur;
	private NoeudAbstrait fils;
	
	public Noeud(NoeudAbstrait frere, NoeudAbstrait fils, char valeur) {
		super(frere);
		if (fils == null || fils instanceof NoeudVide)
			throw new IllegalArgumentException("fils null ou vide interdit");
		this.fils = fils;
		this.valeur = valeur;
	}

	@Override
	public boolean contient(String s) {
		if (s.isEmpty())
			return false;
		char c = s.charAt(0);
		if (c < valeur)
			return false;
		if (c == valeur)
			return fils.contient(s.substring(1));
		return frere.contient(s);
	}

	@Override
	public boolean prefixe(String s) {
		if (s.isEmpty())
			return true;
		char c = s.charAt(0);
		if (c < valeur)
			return false;
		if (c == valeur)
			return fils.prefixe(s.substring(1));
		return frere.prefixe(s);
	}

	@Override
	public int nbMots() {
		return fils.nbMots() + frere.nbMots();
	}

	@Override
	public NoeudAbstrait ajout(String s) {
		if (s.isEmpty()) 
			return new Marque(this);
		char c = s.charAt(0);
		if (c < valeur) {
			NoeudAbstrait n = new NoeudVide().ajout(s);
			n.frere = this;
			return n;
		}
		if (c == valeur) {
			fils = fils.ajout(s.substring(1));
			return this;
		}
		frere = frere.ajout(s);
		return this;
	}

	@Override
	public NoeudAbstrait suppr(String s) {
		if (s.isEmpty())
			throw new ModificationImpossibleException();
		char c = s.charAt(0);
		if (c < valeur)
			throw new ModificationImpossibleException();
		if (c == valeur) {
			fils = fils.suppr(s.substring(1));
			if (fils instanceof NoeudVide)
				return frere;
			return this;
		}
		frere = frere.suppr(s);
		return this;
	}

	@Override
	public String toString(String s) {
		return fils.toString(s + valeur) + frere.toString(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
