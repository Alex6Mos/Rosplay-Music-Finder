package lecture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Mise en place de la creation et sauvegarde de playlist
 * 
 * @author BERANGER - MOSQUERA
 *
 * @version 4
 */

public class Playlist {

	private HashMap <Mp3File, ExtractionMetaData> playlist;	
	
	/**
	 * Parcours de la liste de fichiers
	 * 
	 * @param path String
	 */
	public Playlist(String path) {
		playlist = new HashMap <Mp3File, ExtractionMetaData>();
		File z = new File(path);

		for(File i: z.listFiles()){
			if(i!=null) {
				if(i.getName().endsWith(".mp3")) {
					try {
						playlist.put(new Mp3File(i.getAbsolutePath()), new ExtractionMetaData(Paths.get(i.getAbsolutePath())));
					} catch (UnsupportedTagException | InvalidDataException | IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Constructeur permettant un autre parcours de la liste de fichiers
	 */
	public Playlist() {
		// TODO Auto-generated constructor stub
		playlist = new HashMap <Mp3File, ExtractionMetaData>();
	}


	/**
	 * Sauvegarde d'une playlist en fonction du nom donne
	 * 
	 * @param Name String
	 */
	public void SaveXSPF(String Name) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document d = db.newDocument();
			Element e = d.createElement ("playlist");
			e.setAttribute("version", "1");
			e.setAttribute("xmlns", "http://xspf.org/ns/0/");
			d.appendChild(e);
			Element f = (Element) e.appendChild(d.createElement("trackList"));

			for(Map.Entry <Mp3File, ExtractionMetaData> i : playlist.entrySet()) {
				Element b = d.createElement("track");
				b.appendChild(d.createElement("location")).appendChild(d.createTextNode(i.getKey().getFilename()));
				b.appendChild(d.createElement("creator")).appendChild(d.createTextNode(i.getValue().getArtist()));
				b.appendChild(d.createElement("title")).appendChild(d.createTextNode(i.getValue().getTitle()));
				b.appendChild(d.createElement("album")).appendChild(d.createTextNode(i.getValue().getAlbum()));
				f.appendChild(b);

			}

			TransformerFactory tf =  TransformerFactory.newInstance();
			Transformer t ;

			try {
				t = tf.newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource ds = new DOMSource(d);
				StreamResult st = new StreamResult(new File(String.format("./%s.xspf", Name.replace("/", "").replace("%", "").replace(".xspf", ""))));
				t.transform(ds, st);

			} catch (Exception e2) {
				// TODO: handle exception
			}


		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
	/**
	 * Ajoute un fichier selectionne a la playlist
	 * 
	 * @param file Mp3File
	 */
	public void addP(Mp3File file) {
		playlist.put(file, new ExtractionMetaData(Paths.get(file.getFilename())));
	}
	
	
	@Override
	/**
	 * Affichage de la playlist
	 * 
	 * @return chaine de caractere
	 */
	public String toString() {
		return "Playlist [playlist=" + playlist + "]";
	}


	/**
	 * Parcours de la playlist pour afficher tous les elements
	 * 
	 * @return liste des metadonnees de chaque fichiers de la playlist
	 */
	public HashMap <Mp3File, ExtractionMetaData> getPlaylist() {
		return playlist;
	}

}