# Énoncé : Simulation de propagation d'un feu de forêt

## Objectif
Implémenter une simulation de la propagation d'un feu de forêt.

## Durée indicative
Environ 2h à 3h

## Description du modèle

La forêt est représentée par une grille de dimension **h × l**.

La dimension temporelle est discrétisée. Le déroulement de la simulation se fait étape par étape.

### État initial
- Une ou plusieurs cases sont en feu

### Règles d'évolution
Si une case est en feu à l'étape **t**, alors à l'étape **t+1** :
- Le feu s'éteint dans cette case (la case est remplie de cendre et ne peut ensuite plus brûler)
- Il y a une probabilité **p** que le feu se propage à chacune des 4 cases adjacentes

### Condition d'arrêt
La simulation s'arrête lorsqu'il n'y a plus aucune case en feu.

## Paramètres
Les éléments suivants sont configurables via un fichier de configuration (format libre) :
- Dimensions de la grille
- Position des cases initialement en feu
- Probabilité de propagation **p**

# 🔥 Simulation de propagation d'un feu de forêt

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1%2B-brightgreen)](https://spring.io/projects/spring-boot)

Une application Spring Boot simulant la propagation d'un feu de forêt sur une grille 2D avec visualisation web interactive.

![image](https://github.com/user-attachments/assets/5560ad76-2345-49a5-a286-e95809138ef4)

## 🌟 Fonctionnalités

- Modélisation discrète de la propagation du feu
- Paramètres configurables (taille de grille, probabilité de propagation)
- Interface web interactive
- API REST pour intégration
- Visualisation en temps réel avec code couleur :
  - 🟢  Arbre sain
  - 🔴  Arbre en feu
  - ⚫  Cendres

## 🛠️ Technologies

- **Backend**:
  - Java 17+
  - Spring Boot 3.1+
  - Maven

- **Frontend**:
  - HTML5/CSS3
  - JavaScript 

## 🚀 Installation

### Prérequis
- JDK 17+
- Maven 3.8+
- Navigateur web moderne

### Étapes
1. Cloner le dépôt :
```bash
git clone https://github.com/phenix23/fires-simulation.git
cd fires-simulation
```

2. Configurer les paramètres (optionnel) :
   Éditer le fichier src/main/resources/application.yml :
```yaml
forest:
  fire:
    height: 10
    width: 10
    propagation-probability: 0.7
    initial-fires:
      - x : 1
        y : 4
      - x : 5
        y : 7
```

3. Lancer l'application :
```bash
mvn spring-boot:run
```

4. Accéder à l'interface :
   Ouvrir http://localhost:8080 dans votre navigateur préféré

## 📝 Mode d'emploi
### Interface web
- Étape suivante : Avance d'un pas de simulation
- Réinitialiser : Recharge la configuration initiale

### API REST
-Endpoints disponibles :
| Méthode | Endpoint  | Description |
|----------|----------|----------|
| GET  | /api/simulation/current  | 	Récupère l'état courant de la grille |
| GET | /api/simulation/next | Passe à l'étape suivante |
| GET  | /api/simulation/reset | Réinitialise la simulation  |

Exemple de réponse JSON :
```json
{
  "width": 30,
  "height": 30,
  "grid": [
    ["TREE", "BURNING", "ASHE"],
    ["TREE", "TREE", "BURNING"],
    ...
  ]
}
```
## 🏠 Architecture 

Cette solution offre une séparation de résponablité entre :
  - La Visualisation (frontend)
  - La logique métier / calcul des règles de simulation (backend)
  - La configuration (fichier yaml)
    
![image](https://github.com/user-attachments/assets/eb46161f-5f55-47ad-9a79-cb5f34b2819a)

### Diagramme de composant 

![image](https://github.com/user-attachments/assets/0e123850-16bb-4440-9fb7-e233d69c0199)

Au niveau de l'implémentation des fonctionnalités décrites dans le schèma d'architecture précedent , j'ai opté pour le pattern classic MVC (Modèle-View-Controller) : 
  - Couche View :
    - `index.html/app.js` : Gère l'affichage et les interactions avec l'utilisateur
  - Couche Web (Controller)
    - `ForestFireController` : Expose les endpoints REST pour interagir avec la simulation.
  - Logique métier (Service)
    - `ForestFireService` :  Implémente la logique de simulation étape par étape.
  - Modèle de données 
    - `Cell` : Représente les différents états possibles d'une cellule.
    - `Simulation` : Représente la grille de la forêt et son état actuel.
    - `Position` : Représente la position de la cellule par les coordonnées X, Y.
  - Configuration
    - `ForestFireConfig`: Charge les paramètres de simulation depuis un fichier de configuration `application.yml`.
      
## 🔄 Explication du flux

### 1. Initialisation

- **Chargement configuration**  
  Le fichier `application.yml` est chargé par `ForestFireConfig`

- **Création grille initiale**  
  `ForestFireService` initialise la grille avec les positions des feux de départ

- **Chargement frontend**  
  L'interface web récupère l'état initial via le `ForestFireController`

### 2. Étape de simulation

1. **Action utilisateur**  
   ```bash
   Clicker sur "Étape suivante" ```
   
2. **Appel API**
   ``` http
   GET /api/simulation/next ```

3. **Calcul nouvel état**
    - Transformation : BURNING → ASHE
    - Propagation aléatoire aux 4 voisins (probabilité p)
  
3. **Retour résultat**
   ```json
   {"width": 30,"height": 30,"grid": []}
   ```
   
### 3. Fin de simulation
- **Condition d'arrêt**
  La simulation s'arrête automatiquement quand il reste aucune case en état de BURNING
- **Réinitialisation manuelle**
  ``` http
   GET /api/simulation/reset
  ```

