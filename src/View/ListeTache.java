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
import java.io.File;
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

    // Load icons
    private ImageIcon editIcon ;
    private ImageIcon deleteIcon ;
    private ImageIcon notificationIcon ;

    public ListeTache(TacheDAO tacheDAO) {
        this.tacheDAO = tacheDAO;
        initComponents();
    }

    private void initComponents() {
        // Load icons
        editIcon = new ImageIcon("src/images/edit.png");
        deleteIcon = new ImageIcon("src/images/delete.png");
        notificationIcon = new ImageIcon("src/images/notification.png");
        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Liste des tâches");
        setPreferredSize(new Dimension(1200, 800));

        // Création du titre de l'application
        JLabel labelTitre = new JLabel("Gestion des tâches");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 36));
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);


        // Création du bouton de notification
        ImageIcon scaledNotificationIcon = new ImageIcon(notificationIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        boutonNotifications = new JButton(scaledNotificationIcon);
        boutonNotifications.setFont(new Font("Arial", Font.PLAIN, 14));
        boutonNotifications.setPreferredSize(new Dimension(40, 40));

        // Création du panel pour le titre et le bouton de notification
        JPanel panelTitre = new JPanel(new BorderLayout());
        panelTitre.add(labelTitre, BorderLayout.CENTER);
        panelTitre.add(boutonNotifications, BorderLayout.EAST);

        // Création de l'image
        ImageIcon originalIcon = new ImageIcon("C:/Users/HP/Desktop/géoinfo/S2/java/Projet_tache/Img/gestion-des-taches.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel labelImage = new JLabel(resizedIcon);
        labelImage.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuration du panelHaut avec GridBagLayout
        JPanel panelHaut = new JPanel(new GridBagLayout());
        panelHaut.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbcTitre = new GridBagConstraints();
        gbcTitre.gridx = 0;
        gbcTitre.gridy = 0;
        gbcTitre.fill = GridBagConstraints.HORIZONTAL;
        gbcTitre.weightx = 1.0;
        panelHaut.add(panelTitre, gbcTitre);

        GridBagConstraints gbcImage = new GridBagConstraints();
        gbcImage.gridx = 0;
        gbcImage.gridy = 1;
        gbcImage.insets = new Insets(10, 0, 0, 0);
        panelHaut.add(labelImage, gbcImage);

        // Initialisation des composants graphiques
        champRecherche = new JTextField();
        champRecherche.setPreferredSize(new Dimension(200, 30));
        champRecherche.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel panelRecherche = new JPanel(new BorderLayout());
        panelRecherche.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelRecherche.add(new JLabel(" 🔍  "), BorderLayout.WEST);
        panelRecherche.add(champRecherche, BorderLayout.CENTER);

        GridBagConstraints gbcRecherche = new GridBagConstraints();
        gbcRecherche.gridx = 0;
        gbcRecherche.gridy = 2;
        gbcRecherche.insets = new Insets(10, 0, 0, 0);
        panelHaut.add(panelRecherche, gbcRecherche);

        add(panelHaut, BorderLayout.NORTH);

        // Configuration de la table des tâches
        tableTaches = new JTable();
        tableTaches.setFont(new Font("Arial", Font.PLAIN, 16));
        tableTaches.setRowHeight(30);
        tableTaches.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableTaches.getTableHeader().setPreferredSize(new Dimension(tableTaches.getWidth(), 40));
        tableTaches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTaches.setAutoCreateRowSorter(true);
        tableTaches.setDragEnabled(true);
        tableTaches.setDropMode(DropMode.INSERT_ROWS);

        modeleTableTaches = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Titre", "Description", "Priorité", "Date", "État", "", ""}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };
        tableTaches.setModel(modeleTableTaches);

        tableTaches.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableTaches.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(new JCheckBox()));
        tableTaches.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tableTaches.getColumnModel().getColumn(6).setCellEditor(new ButtonCellEditor(new JCheckBox()));

        // Centrage du tableau avec GridBagLayout
        JPanel panelCentre = new JPanel(new GridBagLayout());
        panelCentre.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelCentre.add(new JScrollPane(tableTaches), gbc);
        add(panelCentre, BorderLayout.CENTER);

        boutonCreer = new JButton("+");
        boutonCreer.setFont(new Font("Arial", Font.PLAIN, 20));
        boutonCreer.setPreferredSize(new Dimension(100, 40));
        boutonCreer.setBackground(Color.decode("#C38EB4"));

        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelBas.add(boutonCreer, BorderLayout.EAST);
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
                    "Supprimer "
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
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Table.background"));
            }

            setIcon(null); // Reset the icon

            if (column == 5) {
                ImageIcon scaledEditIcon = new ImageIcon(editIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(scaledEditIcon);
            } else if (column == 6) {
                ImageIcon scaledDeleteIcon = new ImageIcon(deleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(scaledDeleteIcon);
            }

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
                        stopCellEditing();
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
            label = (value == null) ? "" : value.toString();
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