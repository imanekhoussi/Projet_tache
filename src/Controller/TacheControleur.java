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
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheControleur implements ActionListener {
    private ListeTache listeTacheVue;
    private TacheDAO tacheDAO;

    public TacheControleur(ListeTache listeTacheVue, TacheDAO tacheDAO) {
        this.listeTacheVue = listeTacheVue;
        this.tacheDAO = tacheDAO;
        listeTacheVue.getBoutonCreer().addActionListener(this);
        listeTacheVue.getBoutonModifier().addActionListener(this);
        listeTacheVue.getBoutonSupprimer().addActionListener(this);
        listeTacheVue.getBoutonTrierParDate().addActionListener(this);
        listeTacheVue.getBoutonTrierParPriorite().addActionListener(this);

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
        } else if (e.getSource() == listeTacheVue.getBoutonModifier()) {
            modifierTache();
        } else if (e.getSource() == listeTacheVue.getBoutonSupprimer()) {
            supprimerTache();
        } else if (e.getSource() == listeTacheVue.getBoutonTrierParDate()) {
            trierTachesParDate();
        } else if (e.getSource() == listeTacheVue.getBoutonTrierParPriorite()) {
            trierTachesParPriorite();
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

    private void modifierTache() {
        int indexSelectionne = listeTacheVue.getTableTaches().getSelectedRow();
        if (indexSelectionne != -1) {
            Tache tacheSelectionnee = getTachesAffichees(listeTacheVue.getTableTaches()).get(indexSelectionne);
            TacheModifierVue tacheModifierVue = new TacheModifierVue(tacheSelectionnee);
            tacheModifierVue.setVisible(true);

            tacheModifierVue.getBoutonConfirmer().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String titre = tacheModifierVue.getChampTitre().getText();
                    String description = tacheModifierVue.getChampDescription().getText();
                    String priorite = (String) tacheModifierVue.getComboBoxPriorite().getSelectedItem();
                    Date date = tacheModifierVue.getDatePicker().getDate();
                    String etat = (String) tacheModifierVue.getComboBoxEtat().getSelectedItem();

                    tacheSelectionnee.setTitre(titre);
                    tacheSelectionnee.setDescription(description);
                    tacheSelectionnee.setPriorite(priorite);
                    tacheSelectionnee.setDate(date);
                    tacheSelectionnee.setEtat(etat);

                    tacheDAO.modifierTache(tacheSelectionnee);
                    mettreAJourListeTaches();
                    tacheModifierVue.dispose();
                }
            });
        } else {
            JOptionPane.showMessageDialog(listeTacheVue, "Veuillez sélectionner une tâche à modifier.", "Aucune tâche sélectionnée", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void supprimerTache() {
        int indexSelectionne = listeTacheVue.getTableTaches().getSelectedRow();
        if (indexSelectionne != -1) {
            Tache tacheSelectionnee = getTachesAffichees(listeTacheVue.getTableTaches()).get(indexSelectionne);
            int confirmationSuppression = JOptionPane.showConfirmDialog(listeTacheVue, "Êtes-vous sûr de vouloir supprimer cette tâche ?", "Confirmation de suppression", JOptionPane.YES_NO_OPTION);
            if (confirmationSuppression == JOptionPane.YES_OPTION) {
                tacheDAO.supprimerTache(tacheSelectionnee.getId());
                mettreAJourListeTaches();
            }
        } else {
            JOptionPane.showMessageDialog(listeTacheVue, "Veuillez sélectionner une tâche à supprimer.", "Aucune tâche sélectionnée", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void trierTachesParDate() {
        List<Tache> taches = getTachesAffichees(listeTacheVue.getTableTaches());
        taches.sort((tache1, tache2) -> tache1.getDate().compareTo(tache2.getDate()));
        listeTacheVue.afficherTaches(taches);
    }

    private void trierTachesParPriorite() {
        List<Tache> taches = getTachesAffichees(listeTacheVue.getTableTaches());
        taches.sort((tache1, tache2) -> tache2.getPriorite().compareTo(tache1.getPriorite()));
        listeTacheVue.afficherTaches(taches);
    }

    public void mettreAJourListeTaches() {
        List<Tache> taches = tacheDAO.listerTaches();
        listeTacheVue.afficherTaches(taches);
        listeTacheVue.getChampRecherche().setText(""); // Réinitialiser le champ de recherche
    }

    private List<Tache> getTachesAffichees(JTable tableTaches) {
        DefaultTableModel model = (DefaultTableModel) tableTaches.getModel();
        List<Tache> taches = new ArrayList<>();

        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int id = (int) model.getValueAt(i, 0);
            String titre = (String) model.getValueAt(i, 1);
            String description = (String) model.getValueAt(i, 2);
            String priorite = (String) model.getValueAt(i, 3);
            Date date = (Date) model.getValueAt(i, 4);
            String etat = (String) model.getValueAt(i, 5);

            Tache tache = new Tache(titre, description, priorite, date, etat);
            tache.setId(id);
            taches.add(tache);
        }

        return taches;
    }
    public void afficherNotifications() {
        List<Tache> taches = tacheDAO.listerTaches();
        List<String> notifications = NotificationManager.obtenirNotifications(taches);

        if (!notifications.isEmpty()) {
            NotificationsVue notificationsVue = new NotificationsVue(notifications);
            notificationsVue.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(listeTacheVue, "Aucune notification à afficher.", "Notifications", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}