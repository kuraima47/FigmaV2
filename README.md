﻿# Éditeur de Formes Géométriques (FigmaV2)

## Description du Projet
Ce projet développe un éditeur de formes géométriques destiné à faciliter la création, la manipulation et la gestion de diverses formes géométriques au sein d'une interface utilisateur graphique. Le logiciel permet aux utilisateurs de dessiner, grouper, modifier et sauvegarder des configurations de formes personnalisées.

## Fonctionnalités
- **Sélection et positionnement** : Sélectionner des formes depuis une toolbar et les placer sur le whiteboard via drag and drop.
- **Groupage et dissociation de formes** : Créer des groupes de formes pour une manipulation simplifiée et dissociation pour les réutiliser séparément.
- **Modification des attributs** : Modifier la taille, la position, et d'autres propriétés des formes une fois incorporées sur le dessin.
- **Gestion avancée de la toolbar** : Ajouter des objets personnalisés à la toolbar pour une utilisation future.
- **Annulation et rétablissement d'actions** : Undo et redo des opérations pour faciliter l'édition.
- **Sauvegarde et chargement** : Sauvegarder et charger les configurations de dessins, ainsi que l'état du logiciel, y compris la toolbar.

## Cas d'utilisation
### Cas 1: Glissé-déposer depuis la toolbar
- **Sélection**: L'utilisateur choisit une forme sur la toolbar.
- **Action**: Glisse la forme sur le whiteboard.
- **Résultat**: La forme est ajoutée à la zone de dessin ou supprimée si déposée sur une icône "Poubelle".

### Cas 2: Groupage et dissociation
- **Groupage**: Sélection multiple pour créer un groupe consolidé.
- **Dissociation**: Sélection d'un groupe existant pour le décomposer en éléments individuels.

### Cas 3: Édition des propriétés
- **Modification**: Accès via clic droit à un menu pour éditer les propriétés des formes.
- **Options**: Appliquer, Ok, Annuler pour confirmer ou révoquer les changements.

### Cas 4: Manipulations via toolbar
- **Ajout à la toolbar**: Drag and drop d'un objet vers la toolbar pour sa réutilisation.
- **Suppression**: Éliminer un objet de la toolbar.

### Cas 5: Undo/Redo
- **Undo**: Annuler la dernière action effectuée.
- **Redo**: Rétablir l'action annulée.

### Cas 6: Sauvegarde et chargement
- **Sauvegarde**: Enregistrement du projet à un emplacement spécifié.
- **Chargement**: Ouverture d'un projet sauvegardé pour reprise du travail.

### Cas 7: Sauvegarde de l'état du logiciel
- **Conservation de la toolbar**: Les éléments personnalisés restent accessibles après redémarrage de l'application.

## Commandes de Contrôle Clavier
- `CTRL+S` : Sauvegarde rapide du projet en cours.
- `CTRL+O` : Ouvrir un projet existant.
- `CTRL+Z` : Annuler la dernière action.
- `CTRL+Y` : Refaire une action précédemment annulée.
- `CTRL+G` : Grouper les objets sélectionnés.
- `Suppr` : Supprimer une forme sélectionnée de la toolbar ou du canvas.

## Pistes d'amélioration
- **Déplacement simultané** : Ajout de la capacité à déplacer plusieurs formes non groupées en même temps.
- **Gestion de la mémoire cache** : Améliorer les performances et la gestion de l'undo/redo en intégrant un système de cache.
- **Multithreading** : Utiliser le multithreading pour améliorer la réactivité et la vitesse de traitement des tâches.

## Prérequis


## Installation
Pour installer et exécuter l'éditeur de formes géométriques, suivez les étapes ci-dessous :
```bash
git clone https://github.com/kuraima47/FigmaV2.git
cd FigmaV2
```
**⚠️ Avertissement :** Si vous n'avez pas accès à l'installation par git, contactez Kuraima (Thibault Pottier) pour obtenir l'accès au dépôt.



## Exécution

### Exécution .exe

#### Commande d'exécution

Il est possible d'exécuter l'application via le fichier .exe, en utilisant la commande suivante :

```bash
cd ./executable
./FigmaV2.exe
```

### Exécution .jar
#### Prérequis
**JDK Requis** : JDK 21.

#### Commande d'exécution
Pour exécuter l'application via le fichier .jar, vous pouvez utiliser la commande suivante :

```bash
& "C:\Program Files\Java\jdk-21\bin\java.exe" -cp ".\FigmaV2-1.0-SNAPSHOT.jar" thibault.kuraima.Main
```
**⚠️ Avertissement :** Assurez-vous que le JDK 21 est correctement installé pour éviter des problèmes d'exécution, n'oubliez pas d'adapter le PATH par rapport à vôtre installation.

## Rapport

Le rapport de ce projet est disponible dans le fichier `Rapport.pdf` dans le dossier `/rapport` supporté d'une vidéo permettant de visualiser le fonctionnement de l'application, celle-ci se trouve dans l'emplacement : `/rapport/video/`.
