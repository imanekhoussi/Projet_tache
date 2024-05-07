

import Controller.TacheControleur;
import Model.TacheDAO;
import View.ListeTache;

public class Main {
    public static void main(String[] args) {
        ListeTache listeTacheVue = new ListeTache();
        TacheDAO tacheDAO = new TacheDAO();
        TacheControleur tacheControleur = new TacheControleur(listeTacheVue, tacheDAO);

        listeTacheVue.setTacheControleur(tacheControleur); // Ajoutez cette ligne

        listeTacheVue.setVisible(true);
        tacheControleur.mettreAJourListeTaches();
    }
}