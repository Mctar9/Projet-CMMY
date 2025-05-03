/**
 * Enumération QuadBool représentant une logique à quatre valeurs :
 * NOTHING (absence d'information), FALSE, TRUE, et ERR (erreur/contradiction).
 * Implémente les opérations décrites dans la spécification "demande1.pdf" fournie par Riyad Derguini.
 */
public enum QuadBool {
    NOTHING, // Absence d'information
    FALSE, // Faux classique
    TRUE, // Vrai classique
    ERR; // Erreur ou contradiction

    /**
     * Opération supremum combinant deux valeurs avec propagation d'incertitude.
     * 
     * @param other L'autre valeur QuadBool à combiner
     * @return Le résultat de l'opération supremum
     * 
     * @author Riyad Derguini
     */
    public QuadBool sup(QuadBool other) {
        // ERR est absorbant
        if (this == QuadBool.ERR || other == QuadBool.ERR)
            return QuadBool.ERR;

        // NOTHING est neutre
        if (this == QuadBool.NOTHING)
            return other;
        if (other == QuadBool.NOTHING)
            return this;

        // TRUE et FALSE ensemble produisent ERR
        if ((this == QuadBool.TRUE && other == QuadBool.FALSE) || (this == QuadBool.FALSE && other == QuadBool.TRUE)) {
            return QuadBool.ERR;
        }

        // Mêmes valeurs retournent la même valeur
        return this;
    }

    /**
     * ET logique non-commutatif (&z) avec priorité gauche.
     * 
     * @param other L'autre valeur QuadBool pour l'opération ET
     * @return Le résultat de l'opération ET
     * 
     * @author Riyad Derguini
     */
    public QuadBool andz(QuadBool other) {
        // Priorité gauche
        switch (this) {
            case TRUE:
                // TRUE propage l'autre valeur, sauf NOTHING
                return other == QuadBool.NOTHING ? QuadBool.ERR : other;
            case FALSE:
                // FALSE écrase sauf pour ERR
                return other == QuadBool.ERR ? QuadBool.ERR : QuadBool.FALSE;
            case NOTHING:
                // NOTHING est traité comme dangereux
                return QuadBool.ERR;
            case ERR:
                // ERR domine
                return QuadBool.ERR;
            default:
                return QuadBool.ERR;
        }
    }

    /**
     * Négation partielle.
     * 
     * @param vBool La valeur QuadBool à négativer
     * @return La valeur négative
     * 
     * @author Riyad Derguini
     */
    public static QuadBool neg(QuadBool vBool) {
        switch (vBool) {
            case TRUE:
                return FALSE;
            case FALSE:
                return TRUE;
            case NOTHING:
                return ERR; // L'absence devient incohérence
            case ERR:
                return ERR; // La contradiction persiste
            default:
                return ERR;
        }
    }

    /**
     * Convertit un booléen classique en QuadBool.
     * 
     * @param b Valeur booléenne classique
     * @return TRUE si b est vrai, FALSE sinon
     */
    public static QuadBool fromBoolean(boolean b) {
        return b ? TRUE : FALSE;
    }
}