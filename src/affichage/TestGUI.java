package affichage;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.Color;

/**
 * Classe main permettant de lancer l'application en interface graphique
 * 
 * @author BERANGER - MOSQUERA
 * 
 * @version 1
 */

public class TestGUI {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fen = new GUIaffiche();
		fen.getContentPane().setBackground(Color.DARK_GRAY);
        String pwd = System.getProperty("user.dir");
       	Image icon = Toolkit.getDefaultToolkit().getImage(pwd + "/src/affichage/Rosplay.png");  
	    fen.setIconImage(icon); 
		fen.setSize(1000, 500);
		fen.setBounds(300, 100, 1000, 500);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setVisible(true);
	}
}
