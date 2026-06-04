# ProjetWS — Application Android + Web Service PHP

> **Gestion des étudiants** : une application Android qui consomme un Web Service PHP (CRUD complet)
> via la bibliothèque **Volley** et le parseur **Gson**.

---

## 📁 Structure du dépôt

```
projetws/
├── php_project/          # Web Service PHP (déposer dans xampp/htdocs/projet/)
│   ├── classes/
│   │   └── Etudiant.php
│   ├── connexion/
│   │   └── Connexion.php
│   ├── dao/
│   │   └── IDao.php
│   ├── service/
│   │   └── EtudiantService.php
│   └── ws/
│       ├── createEtudiant.php
│       ├── loadEtudiant.php
│       ├── updateEtudiant.php
│       └── deleteEtudiant.php
├── android_project/      # Projet Android Studio
│   └── app/src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/projetws/
│       │   ├── beans/Etudiant.java
│       │   └── activities/
│       │       ├── ListEtudiantActivity.java
│       │       ├── AddEtudiantActivity.java
│       │       ├── EditEtudiantActivity.java
│       │       └── EtudiantAdapter.java
│       └── res/
│           ├── layout/ (4 fichiers XML)
│           ├── values/ (strings, colors, styles)
│           ├── drawable/ (backgrounds, avatars)
│           └── xml/network_security_config.xml
└── school1.sql           # Script de création de la base MySQL
```

---

## ⚙️ Prérequis

| Outil | Version minimale |
|-------|-----------------|
| XAMPP (Apache + MySQL) | 8.x |
| PHP | 8.0+ |
| Android Studio | Hedgehog (2023.1+) |
| Android SDK | API 26 (Android 8) |
| Java | 8+ |

---

## 🚀 Mise en place — étape par étape

### 1. Base de données MySQL

1. Démarrer **XAMPP** → activer **Apache** et **MySQL**.
2. Ouvrir [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
3. Aller dans l'onglet **SQL** et exécuter le fichier `school1.sql` fourni.

### 2. Web Service PHP

1. Copier le dossier `php_project/` dans :
   ```
   C:\xampp\htdocs\projet\
   ```
2. Vérifier la structure :
   ```
   C:\xampp\htdocs\projet\
   ├── classes\
   ├── connexion\
   ├── dao\
   ├── service\
   └── ws\
   ```
3. Tester dans le navigateur ou avec Postman :
   - **GET** → `http://localhost/projet/ws/loadEtudiant.php`
   - **POST** → `http://localhost/projet/ws/createEtudiant.php`  
     Paramètres : `nom`, `prenom`, `ville`, `sexe`

### 3. Application Android

1. Ouvrir Android Studio → **Open** → sélectionner `android_project/`.
2. Synchroniser Gradle (**Sync Now** si une bannière apparaît).
3. ⚠️ **IP du serveur** :
   - Émulateur Android Studio → laisser `10.0.2.2` (adresse de l'hôte).
   - Appareil physique → remplacer `10.0.2.2` par l'IP locale de votre PC  
     (ex. `192.168.1.X`) dans les trois activités Java.
4. Lancer l'application (**Run ▶**).

---

## 📱 Fonctionnalités implémentées

| Fonctionnalité | Statut |
|---|---|
| Lister tous les étudiants (RecyclerView) | ✅ |
| Recherche en temps réel (filtre) | ✅ |
| Ajouter un étudiant (formulaire) | ✅ |
| Modifier un étudiant | ✅ |
| Supprimer avec confirmation (AlertDialog) | ✅ |
| Validation des champs | ✅ |
| ProgressBar pendant les requêtes | ✅ |
| Avatar coloré selon le sexe | ✅ |
| Gestion des erreurs réseau | ✅ |

---

## 🌐 Endpoints Web Service

| URL | Méthode | Paramètres POST | Description |
|-----|---------|-----------------|-------------|
| `/ws/loadEtudiant.php` | GET | — | Récupère tous les étudiants |
| `/ws/createEtudiant.php` | POST | nom, prenom, ville, sexe | Ajoute un étudiant |
| `/ws/updateEtudiant.php` | POST | id, nom, prenom, ville, sexe | Modifie un étudiant |
| `/ws/deleteEtudiant.php` | POST | id | Supprime un étudiant |

Toutes les réponses sont en **JSON**.

---

## 🔧 Dépendances Android (`build.gradle`)

```groovy
implementation 'com.android.volley:volley:1.2.1'
implementation 'com.google.code.gson:gson:2.10.1'
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'com.google.android.material:material:1.11.0'
```

---

## 📝 Notes pour le correcteur

- L'architecture respecte le patron **DAO / Service / Controller** côté PHP.
- L'activité principale est `ListEtudiantActivity` (déclarée comme LAUNCHER).
- Le `RecyclerView` est couplé à un `EtudiantAdapter` personnalisé.
- La recherche est effectuée côté client (filtre sur la liste en mémoire).
- La sécurité réseau est configurée via `network_security_config.xml` pour autoriser le trafic HTTP local (Android 9+).
- Les couleurs de l'avatar changent selon le sexe (bleu = homme, rose = femme).

---

## 👨‍💻 Auteur

Étudiant : salma ait zidan  
Module : Développement Mobile  
Année : 2025–2026
