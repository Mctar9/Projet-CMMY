/**
 * Enumération QuadBool représentant une logique à quatre valeurs :
 * NOTHING (absence d'information), FALSE, TRUE, et ERR (erreur/contradiction).
 * Implémente les opérations décrites dans la spécification "demande1.pdf".
 */
public enum QuadBool {
    NOTHING,  // Absence d'information
    FALSE,    // Faux classique
    TRUE,     // Vrai classique
    ERR;      // Erreur ou contradiction

    /**
     * Opération supremum combinant deux valeurs avec propagation d'incertitude.
     * @param other L'autre valeur QuadBool à combiner
     * @return Le résultat de l'opération supremum
     */
    public QuadBool sup(QuadBool other) {
        // ERR est absorbant
        if (this == ERR || other == ERR) return ERR;
        
        // NOTHING est neutre
        if (this == NOTHING) return other;
        if (other == NOTHING) return this;
        
        // TRUE et FALSE ensemble produisent ERR
        if ((this == TRUE && other == FALSE) || (this == FALSE && other == TRUE)) {
            return ERR;
        }
        
        // Mêmes valeurs retournent la même valeur
        return this;
    }

    /**
     * ET logique non-commutatif (&z) avec priorité gauche.
     * @param other L'autre valeur QuadBool pour l'opération ET
     * @return Le résultat de l'opération ET
     */
    public QuadBool andz(QuadBool other) {
        // Priorité gauche
        switch (this) {
            case TRUE:
                // TRUE propage l'autre valeur, sauf NOTHING
                return other == NOTHING ? ERR : other;
            case FALSE:
                // FALSE écrase sauf pour ERR
                return other == ERR ? ERR : FALSE;
            case NOTHING:
                // NOTHING est traité comme dangereux
                return ERR;
            case ERR:
                // ERR domine
                return ERR;
            default:
                return ERR;
        }
    }

    /**
     * Négation partielle.
     * @return La valeur négative
     */
    public QuadBool neg() {
        switch (this) {
            case TRUE: return FALSE;
            case FALSE: return TRUE;
            case NOTHING: return ERR;  // L'absence devient incohérence
            case ERR: return ERR;      // La contradiction persiste
            default: return ERR;
        }
    }

    /**
     * Convertit un booléen classique en QuadBool.
     * @param b Valeur booléenne classique
     * @return TRUE si b est vrai, FALSE sinon
     */
    public static QuadBool fromBoolean(boolean b) {
        return b ? TRUE : FALSE;
    }
}