

import Controller.TacheControleur;
import Model.TacheDAO;
import View.ListeTache;

public class Main {
    public static void main(String[] args) {
        // Création des instances des classes
        ListeTache listeTacheVue = new ListeTache();
        TacheDAO tacheDAO = new TacheDAO();
        TacheControleur tacheControleur = new TacheControleur(listeTacheVue, tacheDAO);

        // Affichage de la fenêtre principale
        listeTacheVue.setVisible(true);

        // Chargement initial des tâches depuis la base de données
        tacheControleur.mettreAJourListeTaches();
    }
}