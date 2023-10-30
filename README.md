GestionProgrammeurs
===================

-------------------

-------------------

## Description du projet :

Ce projet permet la gestion d'une BDD de programmeurs.<br>
Depuis un menu, il permet d'afficher différents types de données, de les modifier, de les supprimer, et d'en ajouter.


## Prérequis :

>⚠️ Ce guide est destiné aux utilisateurs de Windows, IntelliJ et MySQL.

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
  + Importer le script SQL situé dans le code source du projet : src > utils > programmeurs.sql
  + Sélectionner l'ensemble du script, puis exécuter
  + Le script s'occupera du reste !

### Sur IntelliJ : 
+ Cloner le projet localement : `git clone`
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

----------

<p>
    <img src="https://img.shields.io/badge/Java-11.0.11-orange?style=for-the-badge" alt=""/>
    <img src="https://img.shields.io/badge/IntelliJ-2021.1.2-blue?style=for-the-badge&logo=intellij-idea" alt=""/>
    <img src="https://img.shields.io/badge/MySQL-8.0.25-blue?style=for-the-badge&logo=mysql" alt=""/>
</p>

> **Cédric A.  | Jade H.** <br>
> *L3-APP LSI2* <br>
> *Septembre 2023* <br>
