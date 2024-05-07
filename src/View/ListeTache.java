package View;

import Controller.TacheControleur;
import Model.Tache;
import Model.TacheDAO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListeTache extends JFrame {
    private JTable tableTaches;
    private JButton boutonCreer;
    private JButton boutonModifier;
    private JButton boutonSupprimer;
    private JButton boutonTrierParDate;
    private JButton boutonTrierParPriorite;
    private JTextField champRecherche;
    private JButton boutonNotifications;
    private DefaultTableModel modeleTableTaches;
    private TacheControleur tacheControleur;

    public ListeTache() {
        initComponents();
    }

    private void initComponents() {
        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Liste des tâches");
        setPreferredSize(new Dimension(800, 600));

        // Initialisation des composants graphiques
        champRecherche = new JTextField();
        champRecherche.setPreferredSize(new Dimension(200, 30));
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 14));

        tableTaches = new JTable();
        tableTaches.setFont(new Font("Arial", Font.PLAIN, 14));
        tableTaches.setRowHeight(25);
        tableTaches.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        boutonCreer = new JButton("➕");
        boutonCreer.setToolTipText("Créer une nouvelle tâche");

        boutonModifier = new JButton("✏️");
        boutonModifier.setToolTipText("Modifier la tâche sélectionnée");

        boutonSupprimer = new JButton("🗑️");
        boutonSupprimer.setToolTipText("Supprimer la tâche sélectionnée");

        boutonTrierParDate = new JButton("📅");
        boutonTrierParDate.setToolTipText("Trier les tâches par date");

        boutonTrierParPriorite = new JButton("⭐");
        boutonTrierParPriorite.setToolTipText("Trier les tâches par priorité");

        boutonNotifications = new JButton("🔔");
        boutonNotifications.setToolTipText("Notifications");

        boutonNotifications.setToolTipText("Afficher les notifications");

        // Configuration de la table des tâches
        modeleTableTaches = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Titre", "Description", "Priorité", "Date", "État"}
        );
        tableTaches.setModel(modeleTableTaches);

        // Configuration de la disposition des composants
        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelHaut.add(new JLabel("Recherche : "), BorderLayout.WEST);
        panelHaut.add(champRecherche, BorderLayout.CENTER);
        add(panelHaut, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableTaches);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBas = new JPanel();
        panelBas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBas.add(boutonCreer);
        panelBas.add(boutonModifier);
        panelBas.add(boutonSupprimer);
        panelBas.add(boutonTrierParDate);
        panelBas.add(boutonTrierParPriorite);
        panelBas.add(boutonNotifications);
        add(panelBas, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        boutonNotifications.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tacheControleur.afficherNotifications();
            }
        });
    }

    public void afficherTaches(List<Tache> taches) {
        modeleTableTaches.setRowCount(0);
        for (Tache tache : taches) {
            Object[] rowData = {
                    tache.getId(),
                    tache.getTitre(),
                    tache.getDescription(),
                    tache.getPriorite(),
                    tache.getDate(),
                    tache.getEtat()
            };
            modeleTableTaches.addRow(rowData);
        }
    }

    public JTable getTableTaches() {
        return tableTaches;
    }

    public JButton getBoutonCreer() {
        return boutonCreer;
    }

    public JButton getBoutonModifier() {
        return boutonModifier;
    }

    public JButton getBoutonSupprimer() {
        return boutonSupprimer;
    }

    public JButton getBoutonTrierParDate() {
        return boutonTrierParDate;
    }

    public JButton getBoutonTrierParPriorite() {
        return boutonTrierParPriorite;
    }

    public JButton getBoutonNotifications() {
        return boutonNotifications;
    }

    public JTextField getChampRecherche() {
        return champRecherche;
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

    public void filtrerTaches(TacheDAO tacheDAO) {
        String recherche = champRecherche.getText().trim().toLowerCase();
        List<Tache> tachesFiltrees = new ArrayList<>();

        for (Tache tache : tacheDAO.listerTaches()) {
            String titre = tache.getTitre().toLowerCase();
            String description = tache.getDescription().toLowerCase();
            String priorite = tache.getPriorite().toLowerCase();
            String etat = tache.getEtat().toLowerCase();

            if (titre.contains(recherche) || description.contains(recherche) || priorite.contains(recherche) || etat.contains(recherche)) {
                tachesFiltrees.add(tache);
            }
        }

        afficherTaches(tachesFiltrees);
    }

    public void setTacheControleur(TacheControleur tacheControleur) {
        this.tacheControleur = tacheControleur;
    }
}