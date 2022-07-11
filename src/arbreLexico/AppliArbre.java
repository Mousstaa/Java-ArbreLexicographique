package arbreLexico;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class AppliArbre {

	private JFrame frmArbreLexicographique;
	private JTextField fieldQuoi;
	private JFileChooser choixFichier;
	private ArbreLexicographique arbre;
	private JLabel lblMessage;
	private JTextArea vueListe;
	private JTree vueArbre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppliArbre window = new AppliArbre();
					window.frmArbreLexicographique.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppliArbre() {
		arbre = new ArbreLexicographique();
		initialize();
		arbre.setVue(vueArbre);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmArbreLexicographique = new JFrame();
		frmArbreLexicographique.setTitle("Arbre Lexicographique");
		frmArbreLexicographique.setBounds(100, 100, 570, 650);
		frmArbreLexicographique.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		choixFichier = new JFileChooser(System.getProperty("user.dir"));

		JMenuBar menuBar = new JMenuBar();
		frmArbreLexicographique.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);

		JMenuItem mntmCharger = new JMenuItem("Charger ...");
		mntmCharger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retour = choixFichier.showOpenDialog(frmArbreLexicographique);
				if (retour == JFileChooser.APPROVE_OPTION)
					try {
						arbre.charge(choixFichier.getSelectedFile().getAbsolutePath());
						miseAJour();
					} catch (FileNotFoundException fnfe) {
						negatif("Fichier non trouvé");
					} catch (IOException ioe) {
						negatif("Lecture du fichier impossible");
					}
				// ------------------------
			}
		});
		mnFichier.add(mntmCharger);

		JMenuItem mntmSauvegarder = new JMenuItem("Sauvegarder ...");
		mntmSauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int retour = choixFichier.showOpenDialog(frmArbreLexicographique);
				if (retour == JFileChooser.APPROVE_OPTION)
					try {
						arbre.sauve(choixFichier.getSelectedFile().getAbsolutePath());
						miseAJour();
					} catch (FileNotFoundException fnfe) {
						negatif("Fichier non trouvé");
					} catch (IOException ioe) {
						negatif("Sauvegarde impossible");
					}				
				//--------------
			}
		});
		mnFichier.add(mntmSauvegarder);

		JSeparator separator = new JSeparator();
		mnFichier.add(separator);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmArbreLexicographique.dispose();
				//--------------------
			}
		});
		mnFichier.add(mntmQuitter);

		JMenu mnAide = new JMenu("Aide");
		menuBar.add(mnAide);
		
		JMenuItem mntmAide = new JMenuItem("Aide");
		mntmAide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmArbreLexicographique, "Renseigner le champ \"Quoi ?\" puis sélectionner l'action à effectuer sur l'arbre lexicographique\n"
						+ "en cliquant sur un des quatre boutons à gauche.", "Aide", JOptionPane.INFORMATION_MESSAGE);
				//-----------------------
			}
		});
		mnAide.add(mntmAide);
		
		JMenuItem mntmAPropos = new JMenuItem("A propos");
		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frmArbreLexicographique, "Application réalisée dans le cadre du cours\n"
						+ "\"Programmation Par Objets Avancée\"\n"
						+ "Master MIASHS - Université Grenoble Alpes\n"
						+ "Année 2018/2019", "A propos", JOptionPane.INFORMATION_MESSAGE);
				//-----------------------
			}
		});
		mnAide.add(mntmAPropos);

		JPanel panelPrincipal = new JPanel();
		frmArbreLexicographique.getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(new BorderLayout(0, 0));

		JPanel panelBoutons = new JPanel();
		panelPrincipal.add(panelBoutons, BorderLayout.NORTH);
		panelBoutons.setLayout(new BorderLayout(0, 0));

		JPanel panelBoutonsGauche = new JPanel();
		panelBoutons.add(panelBoutonsGauche, BorderLayout.WEST);

		JButton btnAjouter = new JButton("ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (arbre.ajout(fieldQuoi.getText()))
					miseAJour();
				else
					negatif("ajout impossible");
				//---------------
			}
		});
		panelBoutonsGauche.add(btnAjouter);

		JButton btnSupprimer = new JButton("supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (arbre.suppr(fieldQuoi.getText()))
					miseAJour();
				else
					negatif("suppression impossible");
				//----------------
			}
		});
		panelBoutonsGauche.add(btnSupprimer);

		JButton btnChercher = new JButton("chercher");
		btnChercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (arbre.contient(fieldQuoi.getText()))
					positif("Trouvé");
				else
					negatif("Non trouvé");
				//----------------
			}
		});
		panelBoutonsGauche.add(btnChercher);

		JButton btnPrefixe = new JButton("prefixe");
		btnPrefixe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (arbre.prefixe(fieldQuoi.getText()))
					positif(fieldQuoi.getText() + " est préfixe d'au moins un élément");
				else
					negatif(fieldQuoi.getText() + " n'est préfixe d'aucun élément");
				//------------
			}
		});
		panelBoutonsGauche.add(btnPrefixe);

		JLabel lblQuoi = new JLabel("Quoi ? ");
		panelBoutonsGauche.add(lblQuoi);

		fieldQuoi = new JTextField();
		panelBoutons.add(fieldQuoi, BorderLayout.CENTER);
		fieldQuoi.setColumns(10);

		lblMessage = new JLabel(" ");
		panelPrincipal.add(lblMessage, BorderLayout.SOUTH);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelPrincipal.add(tabbedPane, BorderLayout.CENTER);

		JScrollPane scrollArbre = new JScrollPane();
		tabbedPane.addTab("Arbre", null, scrollArbre, null);

		vueArbre = new JTree();
		scrollArbre.setViewportView(vueArbre);

		JScrollPane scrollListe = new JScrollPane();
		tabbedPane.addTab("Liste", null, scrollListe, null);

		vueListe = new JTextArea();
		scrollListe.setViewportView(vueListe);
	}
	
	private void positif(String texte) {
		lblMessage.setForeground(Color.BLACK);
		lblMessage.setText(texte);
	}
	
	private void negatif(String texte) {
		lblMessage.setForeground(Color.RED);
		lblMessage.setText(texte);
	}
	
	private void miseAJour() {
		// méthode à appeler après chaque mise à jour de l'arbre
		vueListe.setText(arbre.toString());
		positif("OK - " + arbre.nbMots() + " mots");
	}

}
