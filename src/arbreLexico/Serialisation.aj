package arbreLexico;

import java.io.Serializable;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public privileged aspect Serialisation {
	declare parents : ArbreLexicographique implements Serializable;

	public void ArbreLexicographique.sauve(String nomFichier) throws IOException {
		Writer w = new BufferedWriter(new FileWriter(nomFichier));
		w.write(toString());
		w.flush();
		w.close();
	}

	public void ArbreLexicographique.charge(String nomFichier) throws IOException {
		Reader r = new BufferedReader(new FileReader(nomFichier));
		entree = new NoeudVide();
		String s = "";
		char c;
		int i = r.read();
		while (i != -1) {
			c = (char) i;
			if (c == '\n') {
				ajout(s);
				s = "";
			} else {
				s += c;
			}
			i = r.read();
		}
		r.close();
	}

//	declare parents : NoeudAbstrait implements Serializable;
//
//	public void ArbreLexicographique.sauve(String nomFichier) throws IOException {
//		ObjectOutputStream out = null;
//		out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(nomFichier)));
//		out.writeObject(this);
//		out.flush();
//		out.close();
//	}
//
//	public void ArbreLexicographique.charge(String nomFichier) throws IOException {
//		ObjectInputStream in = null;
//		in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(nomFichier)));
//		ArbreLexicographique a = null;
//		try {
//			a = (ArbreLexicographique) in.readObject();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.entree = a.entree;
//		in.close();
//	}

}
