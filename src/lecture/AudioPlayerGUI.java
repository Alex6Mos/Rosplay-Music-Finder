package lecture;

import java.awt.BorderLayout; 
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

public class AudioPlayerGUI {
	private Thread audioPlayerThread;	
	private AudioPlayer player;
	private Action load;
	private Action play;
	private Action stop;
	private JFrame frame;
	private JPanel contentPane;
	private JFileChooser chooser;
	private File audioFile;
    private JLabel title;
	
	
	public AudioPlayerGUI(){

		
        player = new AudioPlayer();
		frame = new JFrame("AudioPlayer");
		contentPane = new JPanel(new BorderLayout());
		chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(".wav"))
					return true;
				else
					return false;
			}

			@Override
			public String getDescription() {
				return null;
			}
		});

		contentPane.setPreferredSize(new Dimension(300, 150));

		stop = new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				//Thread.currentThread().interrupt();				
				player.stop();				
			}
		};
		stop.putValue(Action.SMALL_ICON, new ImageIcon("stop.png"));		
		
		
		play= new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				
					player.init();
					audioPlayerThread = new Thread(player);
					audioPlayerThread.start();
                   
					player.getLine().addLineListener(new LineListener(){

						public void update(LineEvent le) {
							if(le.getType()==LineEvent.Type.STOP){					
								play.setEnabled(true);
								stop.setEnabled(false);	
								
								
							}
							if(le.getType()==LineEvent.Type.START){					
								play.setEnabled(false);
								stop.setEnabled(true);	
								
							}	
						}			
					});
				
			}
		};

		play.putValue(Action.SMALL_ICON, new ImageIcon("play.png"));		

		load = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					audioFile=chooser.getSelectedFile();
					player.setFile(audioFile);	
                    player.init();
                    title.setText("Fichier en cours de lecture: "+ audioFile.getName());
                    contentPane.paintAll(contentPane.getGraphics());
					
				}
			}
		};
		load.putValue(Action.SMALL_ICON, new ImageIcon("load.png"));
		
		JToolBar toolbar = new JToolBar();
		toolbar.add(load);
		toolbar.add(play);
		toolbar.add(stop);

		contentPane.add(toolbar, BorderLayout.NORTH);
        title=new JLabel("Aucun titre en cours de lecture");
        contentPane.add(title,BorderLayout.CENTER);
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}
}

