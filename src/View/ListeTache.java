package View;

import Controller.TacheControleur;
import Model.Tache;
import Model.TacheDAO;

import javax.swing.*;
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
        setPreferredSize(new Dimension(1200, 800));

        // Initialisation des composants graphiques
        champRecherche = new JTextField();
        champRecherche.setPreferredSize(new Dimension(300, 40));
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 16));

        tableTaches = new JTable();
        tableTaches.setFont(new Font("Arial", Font.PLAIN, 16));
        tableTaches.setRowHeight(30);
        tableTaches.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableTaches.getTableHeader().setPreferredSize(new Dimension(tableTaches.getWidth(), 40));
        tableTaches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTaches.setAutoCreateRowSorter(true);

        boutonCreer = new JButton("Créer");
        boutonCreer.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonCreer.setPreferredSize(new Dimension(120, 40));

        boutonModifier = new JButton("Modifier");
        boutonModifier.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonModifier.setPreferredSize(new Dimension(120, 40));

        boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonSupprimer.setPreferredSize(new Dimension(120, 40));

        boutonNotifications = new JButton("Notifications");
        boutonNotifications.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonNotifications.setPreferredSize(new Dimension(150, 40));

        // Configuration de la table des tâches
        modeleTableTaches = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Titre", "Description", "Priorité", "Date", "État"}
        );
        tableTaches.setModel(modeleTableTaches);

        // Configuration de la disposition des composants
        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHaut.add(new JLabel("Recherche : "), BorderLayout.WEST);
        panelHaut.add(champRecherche, BorderLayout.CENTER);
        add(panelHaut, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableTaches);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBas = new JPanel();
        panelBas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBas.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        panelBas.add(boutonCreer, gbc);
        gbc.gridx++;
        panelBas.add(boutonModifier, gbc);
        gbc.gridx++;
        panelBas.add(boutonSupprimer, gbc);
        gbc.gridx++;
        panelBas.add(boutonNotifications, gbc);
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