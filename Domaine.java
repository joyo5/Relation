import java.util.ArrayList;

public class Domaine {
    private String type;
    private int taille = 0;
    private ArrayList<Object> valeursPossible;

    public Domaine(String type, int taille) {
        this.type = type;
        this.taille = taille;
    }

    public Domaine(String type) {
        this.type = type;
    }

    public Domaine(String type, Object[] valObjects) {
        this.type = type;
        this.valeursPossible = new ArrayList();
        for (int i = 0; i < valObjects.length; i++) {
            this.valeursPossible.add(valObjects[i]);
        }
    }

    public Domaine() {}
    
    public void setType(String type) {this.type = type;}
    public void setTaille(int taille) {this.taille = taille;}
    public void setValeurs(ArrayList<Object> valeursPossible) {this.valeursPossible = valeursPossible;}
    
    public String getType() {return type;}
    public int getTaille() {return taille;}
    public ArrayList<Object> getValeurs() {return valeursPossible;}

    public boolean valider(Object valeur) {
        if (valeur == null) return true;

        if (this.type.equals("int")) {
            if (valeur instanceof Integer) {
                return true;
            }
        }

        if (this.type.equals("varchar")) {
            if (this.taille == 0) return true;

            if (String.valueOf(valeur).length() <= this.taille) {
                return true;
            }
        }

        if (this.type.equals("double")) {
            if (valeur instanceof Double) {
                return true;
            }
        }

        if (this.type.equals("boolean")) {
            if (valeur instanceof Boolean) {
                return true;
            }
        }

        if (this.type.equals("enum")) {
            if (valeursPossible == null) return false;
            for (int i = 0; i < valeursPossible.size(); i++) {
                if (valeursPossible.get(i).equals(valeur)) {
                    return true;
                }
            }
        }

        return false;
    }
}
