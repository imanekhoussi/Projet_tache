package View;

import Model.Tache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.*;

public class TacheModifierVue extends JFrame {
    private JTextField champTitre;
    private JTextArea champDescription;
    private JComboBox<String> comboBoxPriorite;
    private JDateChooser datePicker;
    private JComboBox<String> comboBoxEtat;
    private JButton boutonConfirmer;
    private JButton boutonAnnuler;

    private static final Dimension FENETRE_DIMENSION = new Dimension(800, 600); // Ajustez ces valeurs selon vos préférences

    public TacheModifierVue(Tache tache) {
        initComponents(tache);
    }

    private void initComponents(Tache tache) {
        // Initialisation des composants graphiques
        champTitre = new JTextField(tache.getTitre());
        champTitre.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        champDescription = new JTextArea(tache.getDescription());
        champDescription.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);
        champDescription.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] prioriteOptions = {"Élevée", "Moyenne", "Faible"};
        comboBoxPriorite = new JComboBox<>(prioriteOptions);
        comboBoxPriorite.setSelectedItem(tache.getPriorite());
        comboBoxPriorite.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        datePicker = new JDateChooser();
        datePicker.setDate(tache.getDate());
        datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        String[] etatOptions = {"À faire", "En cours", "Terminée"};
        comboBoxEtat = new JComboBox<>(etatOptions);
        comboBoxEtat.setSelectedItem(tache.getEtat());
        comboBoxEtat.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        boutonConfirmer = new JButton("Confirmer");
        boutonConfirmer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boutonConfirmer.setPreferredSize(new Dimension(120, 40));
        boutonConfirmer.setBackground(Color.decode("#C38EB4"));
        boutonConfirmer.setForeground(Color.BLACK);

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boutonAnnuler.setPreferredSize(new Dimension(120, 40));
        boutonAnnuler.setBackground(Color.decode("#C38EB4"));
        boutonAnnuler.setForeground(Color.BLACK);

        // Configuration de la disposition des composants
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Titre :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        add(champTitre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Description :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(new JScrollPane(champDescription), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weighty = 0.0;
        add(new JLabel("Priorité :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboBoxPriorite, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Date :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("État :"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(comboBoxEtat, gbc);

        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoutons.add(boutonConfirmer);
        panelBoutons.add(boutonAnnuler);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(panelBoutons, gbc);

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Modifier tâche");
        setPreferredSize(FENETRE_DIMENSION); // Définir la taille préférée
        pack(); // Dimensionner la fenêtre en fonction des composants et de la taille préférée
        setLocationRelativeTo(null);
    }

    public JTextField getChampTitre() {
        return champTitre;
    }

    public JTextArea getChampDescription() {
        return champDescription;
    }

    public JComboBox<String> getComboBoxPriorite() {
        return comboBoxPriorite;
    }

    public JDateChooser getDatePicker() {
        return datePicker;
    }

    public JComboBox<String> getComboBoxEtat() {
        return comboBoxEtat;
    }

    public JButton getBoutonConfirmer() {
        return boutonConfirmer;
    }

    public JButton getBoutonAnnuler() {
        return boutonAnnuler;
    }
}