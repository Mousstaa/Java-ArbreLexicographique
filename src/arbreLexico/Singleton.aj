package arbreLexico;

public aspect Singleton {
	private NoeudVide unique = new NoeudVide();
	
	private pointcut appelConstructeurNoeudVide() : call(public NoeudVide.new()) && !(within(Singleton));
	
	NoeudVide around() : appelConstructeurNoeudVide() {
		return unique;
	}

}
