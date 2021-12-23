Projet réalisé par BERANGER Christian et MOSQUERA Alexis
groupe_projet_4, L2-I-C 

--------------------------------

Informations utiles

--------------------------------

Les livrables sont :
	- readme.txt
	- Rapport Projet : version docx et PDF
	- ProjetPOO : ensemble des fichiers sources java, javadoc (intitulé doc), fichiers jar
	- Présentation.mp4 : une vidéo scénarisée pour présenter le projet final

--------------------------------

En mode Console :
Instructions à saisir sur le terminal pour : 

- Afficher l'aide :
java -jar CLI.jar -h

- Lister et analyser tous les fichiers d'un dossier : 
java –jar CLI.jar –d Emplacement du dossier à analyser

- Afficher les métadonnées d'un fichier MP3 spécifié : 
java –jar CLI.jar –f Chemin du dossier dans lequel est stocké le fichier\fichier.mp3

- Sauvegarder un dossier en tant que playlist au format XSPF :
java –jar CLI.jar –d Emplacement du dossier sélectionné -o nom de la playlist.xspf

--------------------------------

En mode graphique :
Instructions à saisir sur le terminal pour lancer l’application :	 

java –jar GUI.jar

--------------------------------

Explication de l'utilisation du mode graphique :

	- Une fois l'application lancée, il y a une fonctionnalité d'aide disponible dans la barre de menu. Cette aide explique le fonctionnement de l'application et son utilité.

	- Dans l'onglet fichier de la barre de menu, il est possible d'ouvrir un seul fichier MP3 ou bien un dossier de fichiers MP3. Une nouvelle fenêtre s'ouvre alors dans les deux cas, afin d'indiquer le chemin de votre/vos fichier.s.
	  Une fois le.s fichier.s ouvert.s, une liste s'affiche à gauche dans l'application et il est possible de sélectionner un fichier précis.

	- Lorsqu'un fichier est sélectionné, il est alors possible de cliquer sur le bouton "Informations Fichier", afin d'afficher les métadonnées de ce dernier à droite dans l'application.

	- Lorsqu'un dossier est ouvert, une autre option possible est d'afficher les métadonnées de tous les fichiers MP3 en interagissant avec "Informations liste".

	- Lorsqu'un fichier est sélectionné, il est possible de l'écouter en interagissant avec "Play", puis de stopper son écoute en interagissant avec "Pause".

	- Lorsqu'une liste de fichiers MP3 est affichée, il est possible d'interagir avec "Sauvegarder la liste en tant que Playlist", afin de sauvegarder cette dernière en Playlist au format xspf.
	  Ce fichier xspf sera sauvegardé à l'emplacement de l'exécutable de l'application.
	
	- Il est possible de sélectionner le thème d'affichage de l'application avec l'option "Apparence" dans la barre de menu.
	  Au choix : "thème clair" ou "thème sombre".

	- Si une mauvaise sélection de fichier a été effectuée ou pour créer une nouvelle playlist, une fonctionnalité "clear" est disponible dans "Fichier" de la barre de menu.

	- L'application peut se fermer de manière traditionnelle avec la croix, ou bien en allant dans "Fichier" dans la barre de menu, puis, en interagissant avec "Quitter/Fermer Rosplay Music Finder".

--------------------------------

	Avec ces informations, il est désormais possible d'utiliser Rosplay Music Finder sans aucun souci ! Bon divertissement sur cette application !
