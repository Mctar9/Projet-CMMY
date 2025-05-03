package com.fightclub.logisim;

/**
 * Cette classe représente les types de composants logiques.
 * Elle est utilisée pour identifier le type de logique (ET, OU, NON, etc.)
 * d'un composant dans un circuit logique.
 * Chaque type de composant est représenté par une constante énumérée.
 * 
 * @author Riyad Derguini
 */
public enum ComponentType {
    AND,
    OR,
    NOT,
    NAND,
    NOR,
    XOR,
    XNOR,
    HIGH,
    LOW,
    LED,
}
