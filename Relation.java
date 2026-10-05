import java.util.ArrayList;

public class Relation {
    private String nom;
    private ArrayList<Attribut> attributs;
    private ArrayList<Uplet> uplets;

    public Relation() {
        this.attributs = new ArrayList<Attribut>();
        this.uplets = new ArrayList<Uplet>();
    }

    public Relation(String nom) {
        this.nom = nom;
        this.attributs = new ArrayList<Attribut>();
        this.uplets = new ArrayList<Uplet>();
    }

    public String getNom() {return nom;}
    public ArrayList<Attribut> getAttributs() {return attributs;}
    public ArrayList<Uplet> getUplets() {return uplets;}

    public void setNom(String nom) {this.nom = nom;}
    public void setAttributs(ArrayList<Attribut> attributs) {this.attributs = attributs;}
    public void setUplets(ArrayList<Uplet> uplet) {this.uplets = uplet;}

    public void ajouterUplet(Uplet uplet) {
        Object[] valeurs = uplet.getValeurs();

        if (valeurs == null) {
            System.out.println("Uplet invalide. Verifiez!");
            System.exit(1);
        }

        for (int i = 0; i < this.attributs.size(); i++) {
            if (!attributs.get(i).getDomaine().valider(valeurs[i])) {
                System.out.println("Valeur invalide. Verifiez!");
                System.exit(1);
            }
        }

        this.uplets.add(uplet);
    }

    public void afficherRelation() {
        System.out.println("       " + nom.toUpperCase());

        for (int i = 0; i < attributs.size(); i++) {
            System.out.printf("%-20s|", attributs.get(i).getNom());
        }
        System.out.println();

    for (int i = 0; i < attributs.size(); i++) {
        System.out.print("--------------------+");
    }
    System.out.println();

        for (int i = 0; i < uplets.size(); i++) {
            uplets.get(i).afficherUplet();
        }
    }

    public Relation projection(String[] nomsAttributs) {
        String nomRelation = "";
        for (int i = 0; i < nomsAttributs.length; i++) {
            nomRelation += nomsAttributs[i];
            if (i + 1 < nomsAttributs.length) nomRelation += ", ";
        }

        Relation newRelation = new Relation(nomRelation);

        // Attributs
        ArrayList<Attribut> newAttributs = new ArrayList<Attribut>();
        for (int i = 0; i < nomsAttributs.length; i++) {
            boolean trouve = false;
            for (int j = 0; j < this.attributs.size(); j++) {
                if (this.attributs.get(j).getNom().equals(nomsAttributs[i])) {
                    newAttributs.add(this.attributs.get(j));
                    trouve = true;
                }
            }
            if (!trouve) {
                System.out.println("Attribut inexistant : " + nomsAttributs[i]);
                System.exit(1);
            }
        }
        newRelation.setAttributs(newAttributs);

        // Uplets
        for (int u = 0; u < this.uplets.size(); u++) {
            Uplet ancien = this.uplets.get(u);
            Object[] nouvellesValeurs = new Object[nomsAttributs.length];

            for (int i = 0; i < nomsAttributs.length; i++) {
                for (int j = 0; j < this.attributs.size(); j++) {
                    if (this.attributs.get(j).getNom().equals(nomsAttributs[i])) {
                        nouvellesValeurs[i] = ancien.getValeurs()[j];
                    }
                }
            }

            Uplet nouveau = new Uplet(newRelation, nomsAttributs, nouvellesValeurs);

            // Doublons
            boolean doublon = false;
            for (int k = 0; k < newRelation.getUplets().size(); k++) {
                if (newRelation.getUplets().get(k).memesValeurs(nouveau)) {
                    doublon = true;
                }
            }
            if (!doublon) {
                newRelation.getUplets().add(nouveau);
            }
        }

        return newRelation;
    }

    public Relation selection(String nomAttribut, String operateur, Object valeur) {
        Relation newRelation = new Relation(this.nom);
        newRelation.setAttributs(this.attributs);

        // 1. Retrouver la position de l'attribut
        int position = -1;
        for (int i = 0; i < this.attributs.size(); i++) {
            if (this.attributs.get(i).getNom().equals(nomAttribut)) {
                position = i;
            }
        }
        if (position == -1) {
            System.out.println("Attribut inexistant : " + nomAttribut);
            System.exit(1);
        }

        // 2. Parcourir les uplets
        for (int u = 0; u < this.uplets.size(); u++) {
            Uplet uplet = this.uplets.get(u);
            Object val = uplet.getValeurs()[position];

            if (val == null) continue;

            boolean garde = false;

            if (operateur.equals("=")) {
                garde = val.equals(valeur);
            } else if (operateur.equals("!=")) {
                garde = !val.equals(valeur);
            } else {
                if (!(val instanceof Number) || !(valeur instanceof Number)) {
                    System.out.println("Operateur " + operateur + " valable seulement pour les nombres");
                    System.exit(1);
                }

                double a = ((Number) val).doubleValue();
                double b = ((Number) valeur).doubleValue();

                if (operateur.equals("<")) garde = a < b;
                else if (operateur.equals(">")) garde = a > b;
                else if (operateur.equals("<=")) garde = a <= b;
                else if (operateur.equals(">=")) garde = a >= b;
                else {
                    System.out.println("Operateur inconnu : " + operateur);
                    System.exit(1);
                }
            }

            if (garde) {
                newRelation.getUplets().add(uplet);
            }
        }

        return newRelation;
    }
}
