package lecture;

import java.io.IOException; 
import java.nio.file.Path;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

/**
 * Realisation de l'extraction de metadonnees MP3
 *
 * @author BERANGER - MOSQUERA
 * @version 3
 *
 */

public class ExtractionMetaData {

        private Mp3File mp3file ;
        private ID3v2 tags;

        /**
         * Mise en place de l'extraction de metadonnees
         * 
         * @param path Path
         */
        public ExtractionMetaData (Path path) {
            try {
                mp3file =  new Mp3File(path);
                tags = mp3file.getId3v2Tag();
            } catch (UnsupportedTagException | InvalidDataException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /**
         * retourne le titre du fichier MP3
         *  
         * @return titre
         */
        public String getTitle() {
            return tags.getTitle();
        }

        /**
         * Retourne l'artiste du fichier MP3
         * 
         * @return artiste
         */
        public String getArtist() {
            return tags.getArtist();
        }

        /**
         * Retourne le nom de l'album du fichier MP3
         * 
         * @return album
         */
        public String getAlbum() {
            return tags.getAlbum();
        }

        /**
         * Retourne l'annee de sortie du fichier MP3
         * 
         * @return annee
         */
        public String getAnnee() {
            return tags.getYear();
        }
        
        /**
         * Retourne l'image de l'album du fichier MP3
         * 
         * @return image
         */
        public String getAlbumImage() {
            return tags.getAlbumImageMimeType();
        }

        /**
         * Retourne le genre de musique du fichier MP3
         * 
         * @return genre
         */
        public String getGenre() {
            return tags.getGenreDescription();
        }

        /**
         * Retourne toutes les metadonnees d'un fichier MP3
         * 
         * @return donnees
         */
        public String GetDta() {
            return String.format("Titre : %s\nArtiste : %s\nAlbum : %s\nAnnee : %s\nGenre : %s\n", getTitle(),getArtist(),getAlbum(),getAnnee(),getGenre());
        }
}