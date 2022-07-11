package arbreLexico;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultTreeModel;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;

public privileged aspect Visualisation {

	declare parents : ArbreLexicographique implements TreeModel;
	declare parents : NoeudAbstrait implements TreeNode;

	private DefaultTreeModel ArbreLexicographique.model;
	private DefaultMutableTreeNode NoeudAbstrait.node;

	// ------------------Méthodes requises par TreeModel
	public Object ArbreLexicographique.getRoot() {
		return model.getRoot();
	}

	public Object ArbreLexicographique.getChild(Object parent, int index) {
		return model.getChild(parent, index);
	}

	public int ArbreLexicographique.getChildCount(Object parent) {
		return model.getChildCount(parent);
	}

	public boolean ArbreLexicographique.isLeaf(Object node) {
		return model.isLeaf(node);
	}

	public void ArbreLexicographique.valueForPathChanged(TreePath path, Object newValue) {
		model.valueForPathChanged(path, newValue);
	}

	public int ArbreLexicographique.getIndexOfChild(Object parent, Object child) {
		return model.getIndexOfChild(parent, child);
	}

	public void ArbreLexicographique.addTreeModelListener(TreeModelListener l) {
		model.addTreeModelListener(l);
	}

	public void ArbreLexicographique.removeTreeModelListener(TreeModelListener l) {
		model.removeTreeModelListener(l);
	}

	// ---- méthodes requises par TreeNode
	public TreeNode NoeudAbstrait.getChildAt(int childIndex) {
		return node.getChildAt(childIndex);
	}

	public int NoeudAbstrait.getChildCount() {
		return node.getChildCount();
	}

	public TreeNode NoeudAbstrait.getParent() {
		return node.getParent();
	}

	public int NoeudAbstrait.getIndex(TreeNode n) {
		return node.getIndex(n);
	}

	public boolean NoeudAbstrait.getAllowsChildren() {
		return node.getAllowsChildren();
	}

	public boolean NoeudAbstrait.isLeaf() {
		return node.isLeaf();
	}

	public Enumeration NoeudAbstrait.children() {
		return node.children();
	}

	// ---- Autres introductions
	private JTree ArbreLexicographique.vue;

	public void ArbreLexicographique.setVue(JTree tree) {
		if (tree == null)
			throw new IllegalArgumentException("null interdit");
		vue = tree;
		tree.setModel(this);
	}

	// ----- Pointcuts
	private pointcut initArbre(ArbreLexicographique arbre) : 
		execution(public ArbreLexicographique.new()) && this(arbre);

	private pointcut initMarque(Marque m) : 
		execution(public Marque.new(NoeudAbstrait)) && this(m);

	private pointcut initNoeud(Noeud n, char c) :
		execution(public Noeud.new(NoeudAbstrait, NoeudAbstrait, char)) 
		&& this(n) && args(NoeudAbstrait, NoeudAbstrait, c);

	private pointcut nouveauFils(Noeud pere, NoeudAbstrait fils) : 
		set(private NoeudAbstrait Noeud.fils)
		&& target(pere) && args(fils);

	private pointcut nouveauFrere(NoeudAbstrait n, NoeudAbstrait frere) : 
		set(protected NoeudAbstrait NoeudAbstrait.frere)
		&& target(n) && args(frere);

	private pointcut ajout() : withincode(public NoeudAbstrait NoeudAbstrait+.ajout(String));

	private pointcut suppression() : withincode(public NoeudAbstrait NoeudAbstrait+.suppr(String));

	private pointcut nouvelleEntree(ArbreLexicographique arbre, NoeudAbstrait entree) : set(private NoeudAbstrait ArbreLexicographique.entree) && args(entree) && this(arbre);

	// ---- Advices
	// ------------Constructeurs
	before(ArbreLexicographique arbre) : initArbre(arbre) {
		arbre.model = new DefaultTreeModel(new DefaultMutableTreeNode());
	}

	before(Marque m) : initMarque(m) {
		m.node = new DefaultMutableTreeNode();
	}

	before(Noeud n, char c) : initNoeud(n, c) {
		n.node = new DefaultMutableTreeNode(c);
	}

	after(AppliArbre aa) : execution(public AppliArbre.new()) && this(aa) {
		aa.arbre.setVue(aa.vueArbre);
	}

	// ----------------Mises à jour

	before(NoeudAbstrait n, NoeudAbstrait frere) : nouveauFrere(n, frere) && 
	ajout() {
		if (n.frere != frere) {
			DefaultMutableTreeNode pere = (DefaultMutableTreeNode) n.node.getParent();
			if (pere == null) { // nouveau chainage inséré avant
				pere = (DefaultMutableTreeNode)frere.node.getParent();
				int indiceFrere = pere.getIndex(frere.node);
				pere.insert(n.node, indiceFrere);
			} else {
				int indiceNouveau = pere.getIndex(n.node);
				pere.insert(frere.node, indiceNouveau + 1);
			}
		}
	}

	before(NoeudAbstrait n, NoeudAbstrait frere) : nouveauFrere(n, frere) && 
	suppression() {
		if (n.frere != frere) {
			DefaultMutableTreeNode pere = (DefaultMutableTreeNode) n.node.getParent();
			pere.remove(n.frere.node);
		}
	}

	before(Noeud pere, NoeudAbstrait fils) : nouveauFils(pere, fils) && 
	(ajout() || withincode(public Noeud.new(NoeudAbstrait, NoeudAbstrait, char))) {
		if (pere.fils != fils)
			pere.node.insert(fils.node, 0);
	}

	before(Noeud pere, NoeudAbstrait fils) : nouveauFils(pere, fils) && suppression() {
		if (pere.fils != fils)
			pere.node.remove(0);
	}

	before(ArbreLexicographique arbre, NoeudAbstrait entree) : nouvelleEntree(arbre, entree) 
	&& withincode(public boolean ArbreLexicographique.ajout(String)) {
		if (arbre.entree != entree) {
			DefaultMutableTreeNode racine = (DefaultMutableTreeNode) arbre.getRoot();
			racine.insert(entree.node, 0);
		}
	}

	before(ArbreLexicographique arbre, NoeudAbstrait entree) : nouvelleEntree(arbre, entree)
	&& withincode(public boolean ArbreLexicographique.suppr(String)) {
		if (arbre.entree != entree) {
			DefaultMutableTreeNode racine = (DefaultMutableTreeNode) arbre.getRoot();
			racine.remove(0);
		}
	}

	
	
	
	
	
	
	
	
	
	// pour recharger et ouvrir l'arbre après chaque modification
	after(ArbreLexicographique arbre) returning (boolean b) : 
		(call(public boolean ArbreLexicographique.ajout(String)) 
				|| call(public boolean ArbreLexicographique.suppr(String))) 
		&& target(arbre) {
		if (b) {
			arbre.model.reload();
			for (int i = 0; i < arbre.vue.getRowCount(); i++)
				arbre.vue.expandRow(i);
		}
	}

}
