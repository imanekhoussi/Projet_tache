// Controller.TacheControleur.java
package Controller;

import Model.Tache;
import Model.TacheDAO;
import View.ListeTache;
import View.NotificationsVue;
import View.TacheModifierVue;
import View.TacheVue;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TacheControleur implements ActionListener {
    private ListeTache listeTacheVue;
    private TacheDAO tacheDAO;

    public TacheControleur(ListeTache listeTacheVue, TacheDAO tacheDAO) {
        this.listeTacheVue = listeTacheVue;
        this.tacheDAO = tacheDAO;
        listeTacheVue.getBoutonCreer().addActionListener(this);

        // Ajouter l'écouteur d'événements pour la recherche
        listeTacheVue.getChampRecherche().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listeTacheVue.filtrerTaches(tacheDAO);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listeTacheVue.filtrerTaches(tacheDAO);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listeTacheVue.filtrerTaches(tacheDAO);
            }
        });

        // Ajouter les écouteurs d'événements pour les boutons de tri
        listeTacheVue.getBoutonTrierPriorite().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourListeTaches(PRIORITE_COMPARATOR);
            }
        });

        listeTacheVue.getBoutonTrierEtat().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourListeTaches(ETAT_COMPARATOR);
            }
        });

        listeTacheVue.getBoutonTrierDate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mettreAJourListeTaches(DATE_COMPARATOR);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == listeTacheVue.getBoutonCreer()) {
            creerTache();
        }
    }

    private void creerTache() {
        TacheVue tacheVue = new TacheVue();
        tacheVue.setVisible(true);

        tacheVue.getBoutonEnregistrer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = tacheVue.getChampTitre().getText();
                String description = tacheVue.getChampDescription().getText();
                String priorite = (String) tacheVue.getComboBoxPriorite().getSelectedItem();
                Date date = tacheVue.getDatePicker().getDate();
                String etat = (String) tacheVue.getComboBoxEtat().getSelectedItem();

                Tache nouvelleTache = new Tache(titre, description, priorite, date, etat);
                tacheDAO.insererTache(nouvelleTache);
                mettreAJourListeTaches(null);
                tacheVue.dispose();
            }
        });
    }

    public void modifierTache(int tacheId) {
        System.out.println("Entrée dans modifierTache"); // Débogage

        // Récupérer la tâche à modifier par son ID
        Tache tacheAModifier = tacheDAO.getTacheById(tacheId);

        if (tacheAModifier != null) {
            System.out.println("Valeurs initiales de la tâche : " + tacheAModifier); // Débogage

            TacheModifierVue tacheModifierVue = new TacheModifierVue(tacheAModifier);
            tacheModifierVue.setVisible(true);

            tacheModifierVue.getBoutonConfirmer().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Bouton Confirmer cliqué"); // Débogage
                    String titre = tacheModifierVue.getChampTitre().getText();
                    String description = tacheModifierVue.getChampDescription().getText();
                    String priorite = (String) tacheModifierVue.getComboBoxPriorite().getSelectedItem();
                    Date date = tacheModifierVue.getDatePicker().getDate();
                    String etat = (String) tacheModifierVue.getComboBoxEtat().getSelectedItem();

                    System.out.println("Nouvelles valeurs de la tâche :"); // Débogage
                    System.out.println("Titre : " + titre); // Débogage
                    System.out.println("Description : " + description); // Débogage
                    System.out.println("Priorité : " + priorite); // Débogage
                    System.out.println("Date : " + date); // Débogage
                    System.out.println("État : " + etat); // Débogage

                    tacheAModifier.setTitre(titre);
                    tacheAModifier.setDescription(description);
                    tacheAModifier.setPriorite(priorite);
                    tacheAModifier.setDate(date);
                    tacheAModifier.setEtat(etat);

                    System.out.println("Avant d'appeler modifierTache du DAO"); // Débogage
                    tacheDAO.modifierTache(tacheAModifier);
                    System.out.println("Après l'appel à modifierTache du DAO"); // Débogage
                    mettreAJourListeTaches(null); // Appel déplacé ici
                    tacheModifierVue.dispose();
                }
            });
        } else {
            System.out.println("La tâche à modifier n'a pas été trouvée dans la base de données."); // Débogage
        }
    }
    public void supprimerTache(Tache tache) {
        System.out.println("Entrée dans supprimerTache"); // Débogage

        // Créer un tableau avec les options personnalisées
        Object[] options = {"Oui", "Non"};

        // Afficher la boîte de dialogue de confirmation avec les options personnalisées
        int confirmationSuppression = JOptionPane.showOptionDialog(listeTacheVue,
                "Êtes-vous sûr de vouloir supprimer cette tâche ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]); // Option "Non" sélectionnée par défaut

        // Vérifier la réponse de l'utilisateur
        if (confirmationSuppression == JOptionPane.YES_OPTION) {
            System.out.println("Suppression confirmée"); // Débogage
            System.out.println("Avant d'appeler supprimerTache du DAO"); // Débogage
            tacheDAO.supprimerTache(tache.getId());
            System.out.println("Après l'appel à supprimerTache du DAO"); // Débogage
            mettreAJourListeTaches(null);
        } else {
            System.out.println("Suppression annulée"); // Débogage
        }
    }
    public void mettreAJourListeTaches(Comparator<Tache> comparator) {
        List<Tache> taches = tacheDAO.listerTaches();

        if (comparator != null) {
            Collections.sort(taches, comparator);
        }

        listeTacheVue.afficherTaches(taches);
        listeTacheVue.getChampRecherche().setText(""); // Réinitialiser le champ de recherche
    }

    public void afficherNotifications() {
        List<Tache> taches = tacheDAO.listerTaches();
        if (!taches.isEmpty()) {
            NotificationsVue notificationsVue = new NotificationsVue(taches, tacheDAO);
            notificationsVue.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(listeTacheVue, "Aucune notification à afficher.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // Dans la classe TacheControleur
    private static final Comparator<Tache> PRIORITE_COMPARATOR = new Comparator<Tache>() {
        @Override
        public int compare(Tache t1, Tache t2) {
            return t1.getPriorite().compareTo(t2.getPriorite());
        }
    };

    private static final Comparator<Tache> ETAT_COMPARATOR = new Comparator<Tache>() {
        @Override
        public int compare(Tache t1, Tache t2) {
            return t1.getEtat().compareTo(t2.getEtat());
        }
    };

    private static final Comparator<Tache> DATE_COMPARATOR = new Comparator<Tache>() {
        @Override
        public int compare(Tache t1, Tache t2) {
            return t1.getDate().compareTo(t2.getDate());
        }
    };

}