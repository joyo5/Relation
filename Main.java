import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Relation relation = new Relation("olona");

        ArrayList<Attribut> attributs = new ArrayList<Attribut>();
        attributs.add(new Attribut("id", new Domaine("int")));
        attributs.add(new Attribut("nom", new Domaine("varchar", 10)));
        relation.setAttributs(attributs);

        String[] nomsV1 = {"id", "nom"};
        Object[] objV1 = {1, "Jea Balita"};
        relation.ajouterUplet(new Uplet(relation, nomsV1, objV1));

        String[] nomsV2 = {"id"};
        Object[] objV2 = {2};
        relation.ajouterUplet(new Uplet(relation, nomsV2, objV2));

        String[] noms = {"id", "nom"};
        relation.selection("id", ">", 0).projection(noms).afficherRelation();

    }
}
