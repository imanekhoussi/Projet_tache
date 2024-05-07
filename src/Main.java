

import Controller.TacheControleur;
import Model.TacheDAO;
import View.ListeTache;

public class Main {
    public static void main(String[] args) {

        TacheDAO tacheDAO = new TacheDAO();
        ListeTache listeTacheVue = new ListeTache(tacheDAO);
        TacheControleur tacheControleur = new TacheControleur(listeTacheVue, tacheDAO);

        listeTacheVue.setTacheControleur(tacheControleur); // Ajoutez cette ligne

        listeTacheVue.setVisible(true);
        tacheControleur.mettreAJourListeTaches();
    }
}