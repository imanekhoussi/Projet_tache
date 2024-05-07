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
                mettreAJourListeTaches();
                tacheVue.dispose();
            }
        });
    }

    public void modifierTache(Tache tache) {
        TacheModifierVue tacheModifierVue = new TacheModifierVue(tache);
        tacheModifierVue.setVisible(true);

        tacheModifierVue.getBoutonConfirmer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titre = tacheModifierVue.getChampTitre().getText();
                String description = tacheModifierVue.getChampDescription().getText();
                String priorite = (String) tacheModifierVue.getComboBoxPriorite().getSelectedItem();
                Date date = tacheModifierVue.getDatePicker().getDate();
                String etat = (String) tacheModifierVue.getComboBoxEtat().getSelectedItem();

                tache.setTitre(titre);
                tache.setDescription(description);
                tache.setPriorite(priorite);
                tache.setDate(date);
                tache.setEtat(etat);

                tacheDAO.modifierTache(tache);
                mettreAJourListeTaches();
                tacheModifierVue.dispose();
            }
        });
    }

    public void supprimerTache(Tache tache) {
        int confirmationSuppression = JOptionPane.showConfirmDialog(listeTacheVue, "Êtes-vous sûr de vouloir supprimer cette tâche ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
        if (confirmationSuppression == JOptionPane.YES_OPTION) {
            tacheDAO.supprimerTache(tache.getId());
            mettreAJourListeTaches();
        }
    }

    public void mettreAJourListeTaches() {
        List<Tache> taches = tacheDAO.listerTaches();
        listeTacheVue.afficherTaches(taches);
        listeTacheVue.getChampRecherche().setText(""); // Réinitialiser le champ de recherche
    }

    public void afficherNotifications() {
        List<Tache> taches = tacheDAO.listerTaches();
        List<String> notifications = Controller.NotificationManager.obtenirNotifications(taches);

        if (!notifications.isEmpty()) {
            NotificationsVue notificationsVue = new NotificationsVue(notifications);
            notificationsVue.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(listeTacheVue, "Aucune notification à afficher.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}