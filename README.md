GestionProgrammeurs
===================

-------------------

-------------------

## Description du projet :

Ce projet permet la gestion d'une BDD de programmeurs.<br>
Depuis un menu, il permet d'afficher différents types de données, de les modifier, de les supprimer, et d'en ajouter.


## Prérequis :

>⚠️ Ce guide est destiné aux utilisateurs de Windows, IntelliJ et MySQL et une connexion Internet.

Ceci dit, vérifiez que vous ayez bien téléchargé :
+ [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows)
+ [JDK](https://www.jetbrains.com/idea/guide/tips/download-jdk/)
+ [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) (< 8.0.22 pour les utilisateurs non-anglophones)
+ [MySQL Server](https://dev.mysql.com/downloads/mysql/)
-----------------
## Setup le projet :


### Sur MySQL Workbench :

+ Créer une nouvelle connexion :
  + Sur la page d'accueil, près de MySQL Connections, cliquer sur "+" (Setup New Connection)
  + Inscrire les informations suivantes et garder les autres valeurs par défaut :
    + **Connection name :** ConnexionBDD
    + **Hostname :** localhost
    + **Username :** root
    + **Password** > Store in Vault : *[ votre mdp root ]*
+ Configurer la BDD :
  + Importer le script SQL situé dans le code source du projet de la branche GUI: src > main > java > utils > init.sql
  + Sélectionner l'ensemble du script, puis exécuter
  + Le script s'occupera du reste !

### Sur IntelliJ : 
+ Cloner le projet localement de la branche GUI : `git clone`
+ Importer le projet sur IntelliJ

+ *[OPTIONNEL]* : Pour manipuler la BDD depuis IntelliJ :
  + Cliquer sur Database (en haut à droite par défaut)
  + Puis "+" > Data Source > MySQL
  + Inscrire les informations suivantes et garder les autres valeurs par défaut :
    + **Host :** localhost
    + **User :** adm
    + **Password :** adm
    + **Database :** APTN61_BD
  + Cliquer sur "Test Connection" pour vérifier que la connexion est bien établie
  + Puis "OK"
  + Vous pouvez maintenant envoyer des requêtes SQL depuis la console MySQL !

### Tout est prêt :
Il y a 2 versions à votre disposition :
  + Pour lancer la version console de l'application : aller dans main > java > exec > Application.java. Cliquer sur `run`
  + Pour lancer la version graphique de l'application : aller dans main > java > app > GestionBddApp.java. Cliquer sur `run`

## Les améliorations

  + Nous avons créé une classe DataGenerator, qui pour un nombre de programmeurs et un nombre de managers donné fera un premier appel API vers `randomuser.me/api` qui génère des données de personnes aléatoires. De ce JSON, nous récupérons les données qui nous intéressent pour mapper notre Personne. En ce qui concerne l'adresse générée aléatoirement, nous faisons un second appel API `nominatim.openstreetmap.org/search.php` afin de récupérer les coordonnées approximatives de l'adresse générée aléatoirement. Nous faisons une recherche à partir de la ville et du pays de l'adresse. Une fois toutes les données récupérées, nous mappons notre Objet Personne et l'ajoutons en base de données en utilisant le design pattern DAO pour faciliter la maintenabilité de notre application. Ainsi si un nouveau type de personne est créée, nous ne devons pas modifier la méthode `add` dans notre classe d'actions mais simplement créer un nouveau dao pour ce type de personne.
  + Pour les personnes, nous avons donc rajouté une image de profil que l'on peut ajouter via un fichier.
  + En ce qui concerne les requêtes SQL, nous avons rajouté quelques statistiques basiques pour un type de personne donnée. Nous pouvons donc afficher la personne avec le salaire le plus élevé, la personne avec le salaire le moins élevé. Nous avons le salaire moyen pour tous les ages distincts d'un type de personné donnée. Nous pouvons afficher une personne par son nom complet. Nous avons le rang de chaque personne en fonction de son salaire, l'histogramme du nombre de personnes possédant un salaire dans un intervalle donné (par tranche de 1000€). Finalement, nous avons le salaire moyen par genre.
  + Nous avons ajouté la possibilité d'exporter toutes les données pour un type de personnes défini au format csv ou pdf.
  + En ce qui concerne le lancement de l'application, nous avons ajouté un gestionnaire d'application qui gère les arguments passés par l'utilisateur dans le main. Ainsi, pour que notre application démarre correctement, il faut renseigner le nombre de programmeurs et également le nombre de managers souhaité. C'est pourquoi, si aucun arguments n'est passé à la méthode main, alors un menu apparaît afin de permettre à l'utilisateur de renseigner ces paramètres. Par exemple, si vous exécutez dans votre invite de commandes `java -jar application.jar 10 3`, l'application génèrera 10 programmeurs et 3 managers. Maintenant si vous exécutez `java -jar application.jar` directement ou avec uniquement le nombre de programmeurs souhaité, alors le menu vous permettra de renseigner le ou les paramètres manquants au bon fonctionnement de la génération automatique des données de personnes.
  + Nous avons créé une interface graphique permettant d'améliorer l'expérience utilisateur. Cette interface graphique comprend une barre de progression au chargement des données, un menu principal (Programmeur / Manager) qui délègue dans le sous-menu correspondant (le sous-menu est le même à qui nous donnons en paramètre le dao approprié afin d'effectuer les actions en base de données pour le bon type de personne).
  + Nous avons créé une classe PredictSalary qui utilise un modèle de régression linéaire qui s'entraîne sur les données présentes en base de données (le plus de données présentes en base de données, le plus précis sera le modèle de prédiction, nous vous recommandons 100 programmeurs et 50 managers). Ainsi, pour un âge donné et un genre donné, nous avons le salaire prédictif basé sur notre modèle entraîné.
  + Nous avons effectué des tests unitaires pour les différentes actions. Il y a 38 tests.

----------

<p>
    <img src="https://img.shields.io/badge/Java-11.0.11-orange?style=for-the-badge" alt=""/>
    <img src="https://img.shields.io/badge/IntelliJ-2021.1.2-blue?style=for-the-badge&logo=intellij-idea" alt=""/>
    <img src="https://img.shields.io/badge/MySQL-8.0.25-blue?style=for-the-badge&logo=mysql" alt=""/>
</p>

> **Cédric A.  | Jade H.** <br>
> *L3-APP LSI2* <br>
> *Septembre 2023* <br>
