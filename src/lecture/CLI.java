package lecture;

import java.io.File;
import java.nio.file.Paths;

/**
 * Mise en place de l'interface Console
 * 
 * @author BERANGER - MOSQUERA
 * 
 * @version 3
 */
public class CLI{

	public static void main(String[] args) {
		boolean F=false;
		boolean F2 =false;
		boolean CheckForErrors = true;
		boolean HasArgs=false;
		String output=null;
	
		Playlist p=null;
		
		// on suppose que -o est le dernier argument Example: -d C://Musics -o fichier
		for(int	 i=0; i<args.length; i++) {
			
			if((args[i].equals("-d")) ) {
				HasArgs=true;
				if (i+1<args.length) {

					File z = new File(args[i+1]);
					
					if(z.isDirectory()) {
						p = new Playlist(z.getAbsolutePath());
						F=true;
					
						//La playlist contient déjà les mp3

						for (ExtractionMetaData metadata: p.getPlaylist().values()) {
							System.out.println(metadata.GetDta());
							
						}
						
					 //condition ? vrai : faux
						
						if (F2) {
							p.SaveXSPF(output==null ? z.getName():output);
						}

					}	
					else {
						CheckForErrors = false;
					}
				}
				else {
					CheckForErrors = false;
				}
			}	
			
			if((args[i].equals("-f"))) {	//vérifie si -f est rentré en argument
				HasArgs=true;
				if (i+1<args.length) {
					File z = new File(args[i+1]);
					if(z.isFile() && z.getName().endsWith(".mp3")) {
						ExtractionMetaData m = new ExtractionMetaData(Paths.get(z.getAbsolutePath()));
						System.out.println(m.GetDta());
					}
					else {
						CheckForErrors = false;
					}
				}
				else {
					CheckForErrors = false;
				}
					
			}
			
			if((args[i].equals("-h"))) {   //vérifie si -h est rentré en argument
				HasArgs=true;
				System.out.println(Help.DisplayHelp(true));
				CheckForErrors = true;
				break;
					
			}
			
			if((args[i].equals("-o")) ) {	//vérifie si -o est rentré en argument
				HasArgs=true;
				if ((i+1<args.length)) {
					F2 =true;
					output = args[i+1];
					
					if (F && p!=null) {
						p.SaveXSPF(output);
					}
				}
				else {
					CheckForErrors =false;
				}
				
					
			}
			
		}
		
		if (!CheckForErrors) {
			System.out.println(Help.DisplayHelp(false));
		}
		if (!HasArgs) {
			System.out.println(Help.DisplayHelp(false));
		}
		
		
	}
	
	/**
	 * Mise en place du systeme d'aide
	 */
	private static final class Help{	// Fonction pour afficher l'aide
		
		public static final String DisplayHelp(boolean Error) {
			if(Error == false) {
				
				return String.format("%s", "Une erreur innatendue s'est produite...\nUtilisez -h pour plus d'aide");
				
			}
			else {
				
				return String.format("%s", "-f Permet de rechercher un fichier\n-d Permet de rechercher un dossier.\n-o Permet d'indiquer le fichier de sortie pour sauvegarder le résultat d une extraction de la playlist dans un fichier à spécifier par l'utilisateur. \nIl faut mettre le chemin du fichier après chaque commande pour que cela fonctionne.");
				
			}			
			
		}
	}
	
}

