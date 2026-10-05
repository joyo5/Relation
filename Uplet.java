import java.util.ArrayList;

public class Uplet {
    private String[] nomsAttribut;
    private Object[] valeurs;

    public Uplet(Relation relation, String[] nomsAttribut, Object[] valeurs) {
        if (nomsAttribut.length != valeurs.length) {
            if (nomsAttribut.length != valeurs.length) {
                System.out.println("Verifiez! la taille des noms d attribut sont differents des valeurs");
                return;
            }
        }

        ArrayList<Attribut> attributs = relation.getAttributs();

        for (int j = 0; j < nomsAttribut.length; j++) {
            boolean trouve = false;
            for (int i = 0; i < attributs.size(); i++) {
                if (nomsAttribut[j].equals(attributs.get(i).getNom())) {
                    trouve = true;
                }
            }
            if (!trouve) {
                System.out.println("Attribut inexistant : " + nomsAttribut[j]);
                return;
            }
        }

        this.nomsAttribut = new String[attributs.size()];
        this.valeurs = new Object[attributs.size()];

        for (int i = 0; i < attributs.size(); i++) {
            this.nomsAttribut[i] = attributs.get(i).getNom();

            for (int j = 0; j < nomsAttribut.length; j++) {
                if (nomsAttribut[j].equals(attributs.get(i).getNom())) {
                    this.valeurs[i] = valeurs[j];
                }
            }
        }
    }

    public String[] getNomsAttribut() {return nomsAttribut;}
    public Object[] getValeurs() {return valeurs;}
    
    public void setNomsAttribut(String[] nomsAttribut) {this.nomsAttribut = nomsAttribut;}
    public void setValeurs(Object[] valeurs) {this.valeurs = valeurs;}

    public void afficherUplet() {
        for (int i = 0; i < this.valeurs.length; i++) {
            String texte;
            if (this.valeurs[i] == null) {
                texte = "NULL";
            } else {
                texte = String.valueOf(this.valeurs[i]);
            }

            if (texte.length() > 20) {
                texte = texte.substring(0, 20);
            }

            System.out.printf("%-20s|", texte);
        }
        System.out.println();

        for (int i = 0; i < this.valeurs.length; i++) {
            System.out.print("--------------------+");
        }
        System.out.println();
    }

    public boolean memesValeurs(Uplet autre) {
        for (int i = 0; i < this.valeurs.length; i++) {
            Object a = this.valeurs[i];
            Object b = autre.valeurs[i];

            if (a == null || b == null) {
                if (a != b) return false;
            } else if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }
}
