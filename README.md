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

 
