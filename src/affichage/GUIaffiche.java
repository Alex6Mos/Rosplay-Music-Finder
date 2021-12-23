package affichage;

import java.awt.Color;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import lecture.ExtractionMetaData;
import lecture.Playlist;
import lecture.Sound;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/**
 * Mise en place de l'interface graphique
 * 
 * @author Christian BERANGER et Alexis MOSQUERA
 * 
 * @version 10
 */

public class GUIaffiche extends JFrame implements ActionListener {
	
	private class MP3 extends Mp3File {
		private String name;
		
		public MP3(String name, File fiche) throws UnsupportedTagException, InvalidDataException, IOException {
			super(fiche);
			this.name = name;
		}
		public String toString() {
			return name;
		}
		
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("unused")
	private JLabel namef, Albumimg, Rosplayimg;
	private JMenuBar menu;
	private JMenu Fichier, Apparence;
	private JMenuItem ouvrir, Playlist, Clear, quitter, sombre, clair, Aide;
	private JButton Info, Play, Pause, Save, Infoa;
	private JTextArea Affiche;
	private ImageIcon img;
	private Image Ros;
	private File f, z;
	private Sound s;
	private JScrollPane scrollPane, scrollPane_1;
	@SuppressWarnings("rawtypes")
	private JList list;
	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	private Playlist h;
	@SuppressWarnings("unused")
	private String output;
	private Thread b;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	/**
	 * Constructeur permettant d'initialiser la fenetre graphique principale
	 */
	public GUIaffiche(){
		
		/**
		 * Titre de la fenetre
		 */
		super ("Rosplay Music Finder");
		
		/**
		 * Dimenssions
		 */
		setMinimumSize(new Dimension(800, 650));
		setPreferredSize(new Dimension(2000, 800));

		/**
		 * Définition de la fenêtre principale
		 */
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setContentPane(contentPane);
		
		/**
		 * Définition de la barre de menu
		 */
		menu = new JMenuBar();
		setJMenuBar(menu);
		menu.setBackground(Color.lightGray);
		
		/**
		 * Onglet fichier
		 */
		Fichier = new JMenu("Fichier");
		menu.add(Fichier);
		
		/**
		 * Onglet Apparence
		 */
		Apparence = new JMenu("Apparence");
		menu.add(Apparence);
		
		/**
		 * Bouton d'aide
		 */
		Aide = new JMenuItem("Aide ?") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getMaximumSize() {
				Dimension d1 = super.getPreferredSize();
                Dimension d2 = super.getMaximumSize();
                d2.width = d1.width;
                return d2;
			}
		};
		Aide.setPreferredSize(new Dimension(45,10));
		Aide.setHorizontalAlignment(SwingConstants.CENTER);
		Aide.setBackground(Color.LIGHT_GRAY);
		menu.add(Aide);		
		Aide.addActionListener(this);
		
		/**
		 * Bouton pour ouvrir des fichiers 
		 */
		ouvrir = new JMenuItem("Ouvrir Fichier MP3");
		Fichier.add(ouvrir);
		ouvrir.addActionListener(this);
		
		/**
		 * Bouton pour ouvrir une playlist
		 */
		Playlist = new JMenuItem("Ouvrir Playlist");
		Fichier.add(Playlist);
		Playlist.addActionListener(this);
		
		/**
		 * Bouton pour réinitialiser
		 */
		Clear = new JMenuItem("Clear");
		Fichier.add(Clear);
		Clear.addActionListener(this);
		
		/**
		 * Bouton pour fermer la fenêtre
		 */
		quitter = new JMenuItem("Quitter/Fermer Rosplay Music Finder");
		Fichier.add(quitter);
		quitter.addActionListener(this);
		
		/**
		 * Bouton pour passer en thème sombre
		 */
		sombre = new JMenuItem("Thème Sombre");
		Apparence.add(sombre);
		sombre.addActionListener(this);
		
		/**
		 * Bouton pour passer en thème clair
		 */
		clair = new JMenuItem("Thème Clair");
		Apparence.add(clair);
		clair.addActionListener(this);
		
		
		/**
		 * Bouton pour sauvegarder la playlist
		 */
		Save = new JButton ("Sauvegarder la liste en tant que Playlist");
		Save.setBounds(350, 10, 300, 20);
		getContentPane().add(Save);
		Save.setBackground(Color.LIGHT_GRAY);
		Save.addActionListener(this);
		
		
		
		/**
		 * Paramètrages de la fenêtre principale
		 */
		getContentPane().setLayout(null);
		this.pack();
		contentPane.setBackground(Color.DARK_GRAY);
		
		/**
		 * Mise en place d'un scroll pour l'affichage des fichiers MP3
		 */
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.setBounds(102, 102, 300, 450);
		contentPane.add(scrollPane);
		scrollPane.setVisible(false);
		
		/**
		 * Mise en place d'un scroll pour l'affichage des metadonnees
		 */
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(582, 102, 300, 450);
		contentPane.add(scrollPane_1);
		
		/**
		 * Affichage de la zone de texte pour afficher les métadonnées
		 */
		Affiche = new JTextArea();
		scrollPane_1.setViewportView(Affiche);
		Affiche.setForeground(Color.white);
		Affiche.setBackground(new Color(0, 102, 102));
		Affiche.setEditable(false);
		Affiche.setVisible(true);
		scrollPane_1.setVisible(false);
		
		model = new DefaultListModel();
		
		/**
		 * Liste pour afficher les fichiers MP3
		 */
		list = new JList(model);
		list.setForeground(Color.white);

		scrollPane.setViewportView(list);
		list.setBorder(null);
		list.setBackground(new Color(0, 102, 102));
		list.setVisible(false);
		
		/**
		 * Affichage du logo sur l'application
		 */
		Rosplayimg = new JLabel("");
		Rosplayimg.setBounds(0, 0, 100, 100);
		String pwd = System.getProperty("user.dir");
		img = new ImageIcon(pwd + "/src/affichage/Rosplay.png");
		Ros = img.getImage();
		Ros = Ros.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Rosplayimg.setIcon(new ImageIcon(Ros));
		contentPane.add(Rosplayimg);
		
		/**
		 * Mise en place du bouton d'informations
		 */
		Info =  new JButton ("Informations Fichier");
		Info.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(Info);
		Info.addActionListener(this);
		Info.setBounds(350, 40, 300, 20);
		
		/**
		 * Mise en place du bouton pour écouter le fichier MP3
		 */
		Play = new JButton ("Play");
		Play.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(Play);
		Play.addActionListener(this);
		Play.setBounds(412, 102, 160, 20);
		Play.setVisible(false);
		
		/**
		 * Mise en place du bouton pour mettre en pause l'écoute
		 */
		Pause = new JButton ("Stop");
		Pause.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(Pause);
		Pause.addActionListener(this);
		Pause.setBounds(412, 102, 160, 20);
		Pause.setVisible(false);
		
		/**
		 * Mise en place du bouton d'informations de la liste de fichiers
		 */
		Infoa = new JButton ("Informations liste");
		Infoa.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(Infoa);
		Infoa.addActionListener(this);
		Infoa.setBounds(350, 60, 300, 20);
		
		/**
		 * Mise en place de l'affichage de l'image d'album d'un fichier MP3
		 */
		Albumimg = new JLabel("");
		Albumimg.setOpaque(true);
		Albumimg.setBackground(new Color(51, 102, 102));
		Albumimg.setBounds(412, 135, 160, 160);
		contentPane.add(Albumimg);
		Albumimg.setVisible(false);
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	/**
	 * Fonctionalites suite a une action de l'utilisateur
	 * 
	 * @param e ActionEvent
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		/**
		 * Ouverture d'un fichier MP3
		 */				
		if(e.getSource()==ouvrir) {;
			JFileChooser jfc = new JFileChooser(); 
			jfc.setAcceptAllFileFilterUsed(false); 
			// FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG3 songs", "mp3"); 
		    FileFilter filter = new FileFilter() {
		    	public String getDescription() {
					return "MP3 Files";
				}
		    	public boolean accept (File file) {
			    	String mime = null;
			    	try {
			    		mime = Files.probeContentType(file.toPath());
			    	}
			    	catch(IOException e) {
			    		e.printStackTrace();
			    	}
			    	if(file.isDirectory()) {
			    		return true;
			    	}
			    	else {
			    		if(mime != null && mime.equals("audio/mpeg")) {
			    			return true;
			    		}
			    		else {
			    			return false;
			    		}
			    	}
		    	}		    			    	
		    };
		    jfc.removeChoosableFileFilter(jfc.getAcceptAllFileFilter());
		    jfc.addChoosableFileFilter(filter);
		    int fileResult = jfc.showOpenDialog(null);
		    if (fileResult == JFileChooser.APPROVE_OPTION) { 
		    	if (h != null) {
		    		try {
						h.addP(new Mp3File(jfc.getSelectedFile()));
					} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
		    	else {
		    		h = new Playlist();
		    		try {
						h.addP(new Mp3File(jfc.getSelectedFile()));
					} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
		    	scrollPane.setVisible(true);
		    	list.setVisible(true);
		    	Play.setVisible(true);
		    	f = jfc.getSelectedFile();
		    	namef = new JLabel(f.getName());
		    	
		    	try {
					model.addElement(new MP3(f.getName(), f));
				} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		}
		
		/**
		 * Ouverture d'un dossier avec fichiers MP3
		 */
		if(e.getSource()==Playlist) {
			JFileChooser jfc = new JFileChooser(); 
		    jfc.setAcceptAllFileFilterUsed(false);
		    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    int fileResult = jfc.showOpenDialog(null);
		    if (fileResult == JFileChooser.APPROVE_OPTION) { 
		    	scrollPane.setVisible(true);
		    	list.setVisible(true);
		    	Play.setVisible(true);
		    	z = jfc.getSelectedFile();
		    	h = new Playlist(z.getAbsolutePath());
		    	for(File j : z.listFiles()) {
		    		if(j!=null) {
		    			if(j.getName().endsWith(".mp3")) {
		    				try {
								model.addElement(new MP3(j.getName(), j));
							} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		    			}
		    		}
		    	}
			}
		}
		
		/**
		 * Réinitialisation de la liste de fichiers
		 */
		if(e.getSource()==Clear) {
			model.clear();
			Affiche.setText(" ");
			scrollPane_1.setVisible(false);
			scrollPane.setVisible(false);
	    	list.setVisible(false);
	    	Albumimg.setVisible(false);
	    	Play.setVisible(false);
	    	Pause.setVisible(false);
		}
		
		/**
		 * Action permettant de quitter l'application
		 */
		if(e.getSource()==quitter) {
			System.exit(0);
		}
		
		/**
		 * Action permettant de passer en thème sombre
		 */
		if(e.getSource()==sombre) {
			contentPane.setBackground(Color.DARK_GRAY);
			list.setBackground(new Color(0, 102, 102));
			Affiche.setBackground(new Color(0, 102, 102));
			Albumimg.setBackground(new Color(51, 102, 102));
			list.setForeground(Color.white);
			Affiche.setForeground(Color.white);
		}
		
		/**
		 * Action permettant de passer en thème clair
		 */
		if(e.getSource()==clair) {
			contentPane.setBackground(Color.white);
			list.setBackground(new Color(102, 204, 153));
			Affiche.setBackground(new Color(102, 204, 153));
			Albumimg.setBackground(new Color(153, 255, 255));
			list.setForeground(Color.BLACK);
			Affiche.setForeground(Color.BLACK);
		}
		
		/**
		 * Action permettant d'obtenir des informations sur l'application
		 */
		if(e.getSource()==Aide) {
			JOptionPane.showMessageDialog(this, "Bienvenue sur Rosplay Music Finder \n\nIci, vous pouvez accéder à vos titres en ouvrant un fichier ! \nIl vous est alors possible d'avoir accès aux informations du Titre sélectionné !\nVous pouvez également l'ajouter à une playlist, sauvegarder une playlist ainsi qu'écouter une musique ! \n\nBon divertisement ! ", "Aide", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * Action permettant de sauvegarder une liste en tant que playlist
		 */
		if(e.getSource()==Save) {
			String x = JOptionPane.showInputDialog(this, "Nom de votre Playlist : ");
			h.SaveXSPF(x);
		}
		
		/**
		 * Action permettant d'afficher les metadonnee d'un fichier MP3
		 */
		if(e.getSource()==Info) {
			Object o = list.getSelectedValue();
			if(o instanceof MP3) {
				ExtractionMetaData x = new ExtractionMetaData(Paths.get(((Mp3File)o).getFilename()));
				BufferedImage klo = null;
				try {
					klo = ImageIO.read(new ByteArrayInputStream(((MP3)o).getId3v2Tag().getAlbumImage()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Image tui = klo.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
				Albumimg.setIcon(new ImageIcon(tui));
				Affiche.setText(x.GetDta());
				if(Affiche!=null) {
					scrollPane_1.setVisible(true);
					Albumimg.setVisible(true);
				}
			}
		}
		
		/**
		 * Action permettant d'afficher les metadonnee de tous les fichier MP3 de la liste
		 */
		if(e.getSource()==Infoa){
			Affiche.setText(" ");
			Albumimg.setVisible(false);
			scrollPane_1.setVisible(true);
			for(ExtractionMetaData i : h.getPlaylist().values()) {
				Affiche.append(i.GetDta()+"\n");
			}
		}
		
		/**
		 * Action pour écouter le fichier MP3
		 */
		if(e.getSource()==Play) {
			b = new Thread() {
				public void run() {
					synchronized (this){
						try {
							s = new Sound(((MP3)list.getSelectedValue()).getFilename());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							s.play();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
			};
			b.start();
			Play.setVisible(false);
			Pause.setVisible(true);
		}
		/**
		 * Action pour mettre en pause l'écoute
		 */
		if(e.getSource()==Pause) {
			b.stop();
			Play.setVisible(true);
			Pause.setVisible(false);
		}
	}
}
