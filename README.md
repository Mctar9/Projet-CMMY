# Simulation de Circuits - README

## Table des Matières
1. [Introduction](#introduction)
2. [Structure des Classes](#structure-des-classes)
3. [Relations entre les Classes](#relations-entre-les-classes)
4. [Installation](#installation)
5. [Utilisation](#utilisation)

## Introduction
Ce projet est une simulation de circuits électroniques, permettant de placer, manipuler et connecter des composants via une interface graphique développée en Java Swing. L’objectif est de fournir un outil pédagogique simple pour visualiser et comprendre le fonctionnement de circuits logiques.

## Structure des Classes
Voici les classes principales et leurs responsabilités :

### Classe `MainApp`
- **Rôle** : Point d’entrée de l’application.
- **Responsabilité principale** : Initialiser l’interface graphique en lançant la classe `CircuitUI`.

### Classe `CircuitUI`
- **Rôle** : Gérer l’interface principale de l’application.
- **Responsabilités principales** :
  - Initialiser la fenêtre principale (`JFrame`).
  - Intégrer le panneau de simulation (`PlacementPanel`).
  - Ajouter des fonctionnalités globales comme des boutons et des contrôles utilisateur.
- **Principaux attributs** :
  - `frame` : Fenêtre principale de l’application.
  - `placementPanel` : Zone de placement pour les composants.
- **Principales méthodes** :
  - `CircuitUI()` : Initialise l’interface principale.
  - `createToolbar()` : Ajoute une barre d’outils contenant des contrôles utilisateur.

### Classe `PlacementPanel`
- **Rôle** : Représenter la zone de simulation où les composants sont placés.
- **Responsabilités principales** :
  - Gérer l’ajout et l’affichage des composants.
  - Fournir une interface graphique pour manipuler les composants.
- **Principaux attributs** :
  - `components` : Liste des composants placés dans la zone.
- **Principales méthodes** :
  - `PlacementPanel()` : Configure la zone de simulation.
  - `addNewComponent(int x, int y)` : Ajoute un nouveau composant aux coordonnées spécifiées.
  - `paintComponent(Graphics g)` : Redessine les composants sur le panneau.

### Classe `Component`
- **Rôle** : Représenter un composant individuel dans la simulation (ex. : une porte logique).
- **Responsabilités principales** :
  - Stocker les coordonnées et l’état du composant.
  - Gérer son affichage graphique.
- **Principaux attributs** :
  - `x` : Coordonnée X du composant.
  - `y` : Coordonnée Y du composant.
- **Principales méthodes** :
  - `Component(int x, int y)` : Initialise un composant à une position donnée.
  - `draw(Graphics g)` : Affiche graphiquement le composant.

## Relations entre les Classes
- **MainApp → CircuitUI** :
  - `MainApp` crée une instance de `CircuitUI`, qui initialise l’interface utilisateur.
- **CircuitUI → PlacementPanel** :
  - `CircuitUI` contient une instance de `PlacementPanel`, qui gère la zone de simulation.
- **PlacementPanel → Component** :
  - `PlacementPanel` stocke et manipule une liste de `Component` pour gérer les éléments de la simulation.

## Installation
1. Clonez le dépôt Git :
   ```bash
   git clone <lien-du-dépôt>
   ```
2. Ouvrez le projet dans votre IDE préféré (Eclipse, IntelliJ, etc.).
3. Compilez et exécutez le fichier `MainApp.java`.

## Utilisation
1. Lancez l’application.
2. Une fenêtre s’ouvre avec une zone grise représentant la zone de simulation.
3. Les fonctionnalités avancées (ajout, rotation, connexions) peuvent être ajoutées au fur et à mesure.

---

Ce projet est conçu pour être extensible et pédagogique. Chaque classe est modulaire et peut être enrichie avec des fonctionnalités supplémentaires comme la gestion des fils, la rotation des composants, ou encore la simulation dynamique.
