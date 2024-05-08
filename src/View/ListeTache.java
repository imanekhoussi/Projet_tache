// View.ListeTache.java
package View;

import Controller.TacheControleur;
import Model.Tache;
import Model.TacheDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListeTache extends JFrame {
    private JTable tableTaches;
    private JButton boutonCreer;
    private JTextField champRecherche;
    private JButton boutonNotifications;
    private DefaultTableModel modeleTableTaches;
    private TacheControleur tacheControleur;
    private TacheDAO tacheDAO;

    public ListeTache(TacheDAO tacheDAO) {
        this.tacheDAO = tacheDAO;
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
        tableTaches.setDragEnabled(true);
        tableTaches.setDropMode(DropMode.INSERT_ROWS);
        tableTaches.setTransferHandler(new TransferHandler() {
            // Implémentation du glisser-déposer des lignes de la table
            // ...
        });

        boutonCreer = new JButton("Créer");
        boutonCreer.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonCreer.setPreferredSize(new Dimension(120, 40));
        boutonCreer.setBackground(Color.decode("#C38EB4")); // Changement de la couleur du bouton "Ajouter"

        boutonNotifications = new JButton("notification");
        boutonNotifications.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonNotifications.setPreferredSize(new Dimension(150, 40));
        boutonNotifications.setBackground(Color.decode("#C38EB4")); // Changement de la couleur du bouton "Notifications"

        // Configuration de la table des tâches
        modeleTableTaches = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Titre", "Description", "Priorité", "Date", "État", "Modifier", "Supprimer"}
        ) {
            // Rendre les cellules "Modifier" et "Supprimer" cliquables
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };
        tableTaches.setModel(modeleTableTaches);

        // Configuration des boutons "Modifier" et "Supprimer" dans chaque ligne
        tableTaches.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableTaches.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(new JCheckBox()));
        tableTaches.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tableTaches.getColumnModel().getColumn(6).setCellEditor(new ButtonCellEditor(new JCheckBox()));

        // Configuration de la disposition des composants
        setLayout(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHaut.add(new JLabel("Recherche \uD83D\uDD0D\n: "), BorderLayout.WEST);
        panelHaut.add(champRecherche, BorderLayout.CENTER);
        panelHaut.add(boutonNotifications, BorderLayout.EAST); // Ajout du bouton "Notifications" à droite
        add(panelHaut, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(tableTaches);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBas = new JPanel(new BorderLayout()); // Utilisation de BorderLayout
        panelBas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBas.add(Box.createHorizontalGlue(), BorderLayout.CENTER); // Ajout d'un espace horizontal extensible
        panelBas.add(boutonCreer, BorderLayout.EAST); // Alignement du bouton "Créer" à droite
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
                    tache.getTitre(),
                    tache.getDescription(),
                    tache.getPriorite(),
                    tache.getDate(),
                    tache.getEtat(),
                    "Modifier",
                    "Supprimer"
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

    public JButton getBoutonNotifications() {
        return boutonNotifications;
    }

    public JTextField getChampRecherche() {
        return champRecherche;
    }

    private Tache getTacheFromRow(int modelRow) {
        String titre = (String) modeleTableTaches.getValueAt(modelRow, 0);
        String description = (String) modeleTableTaches.getValueAt(modelRow, 1);
        String priorite = (String) modeleTableTaches.getValueAt(modelRow, 2);
        Date date = (Date) modeleTableTaches.getValueAt(modelRow, 3);
        String etat = (String) modeleTableTaches.getValueAt(modelRow, 4);
        Tache tache = new Tache(titre, description, priorite, date, etat);

        // Récupérer l'ID de la tâche depuis le DAO
        int id = tacheDAO.getTacheId(titre, description, priorite, (java.sql.Date) date, etat);
        tache.setId(id);

        return tache;
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

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(Color.decode("#C38EB4")); // Changement de la couleur de fond des boutons "Modifier" et "Supprimer"
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private class ButtonCellEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonCellEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            label = "";
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int selectedRow = tableTaches.getSelectedRow();
                    if (selectedRow >= 0) {
                        System.out.println("Ligne sélectionnée : " + selectedRow);
                        int modelRow = tableTaches.convertRowIndexToModel(selectedRow);
                        System.out.println("Ligne du modèle : " + modelRow);
                        Tache tache = getTacheFromRow(modelRow);
                        if (label.equals("Modifier")) {
                            System.out.println("Modification de la tâche : " + tache.getTitre());
                            tacheControleur.modifierTache(tache.getId());
                        } else if (label.equals("Supprimer")) {
                            System.out.println("Suppression de la tâche : " + tache.getTitre());
                            tacheControleur.supprimerTache(tache);
                        }
                        stopCellEditing(); // Appeler stopCellEditing pour terminer l'édition de la cellule
                    } else {
                        System.out.println("Aucune ligne sélectionnée");
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString(); // Attribuer value à label
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return button.getText();
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}