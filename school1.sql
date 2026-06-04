-- ============================================================
--  Script SQL  –  Base de données school1
--  Exécuter dans phpMyAdmin ou la console MySQL
-- ============================================================

CREATE DATABASE IF NOT EXISTS school1
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE school1;

CREATE TABLE IF NOT EXISTS Etudiant (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    nom    VARCHAR(50)  NOT NULL,
    prenom VARCHAR(50)  NOT NULL,
    ville  VARCHAR(50)  NOT NULL,
    sexe   VARCHAR(10)  NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Données de test
INSERT INTO Etudiant (nom, prenom, ville, sexe) VALUES
    ('Lachgar',  'Mohamed', 'Rabat',      'homme'),
    ('Safi',     'Amine',   'Marrakech',  'homme'),
    ('Benali',   'Sara',    'Casablanca', 'femme'),
    ('Chraibi',  'Yasmine', 'Fès',        'femme');
