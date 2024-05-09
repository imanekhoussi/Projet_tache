package View;

import Model.Tache;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import java.awt.*;

public class TacheVue extends JFrame {
    private JTextField champTitre;
    private JTextArea champDescription;
    private JComboBox<String> comboBoxPriorite;
    private JDateChooser datePicker;
    private JComboBox<String> comboBoxEtat;
    private JButton boutonEnregistrer;
    private JButton boutonAnnuler;

    private static final Dimension FENETRE_DIMENSION = new Dimension(900, 700); // Ajustez ces valeurs selon vos préférences

    public TacheVue() {
        initComponents();
    }

    private void initComponents() {
        // Initialisation des composants graphiques
        champTitre = new JTextField();
        champTitre.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        champDescription = new JTextArea();
        champDescription.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);
        champDescription.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] prioriteOptions = {"Élevée", "Moyenne", "Faible"};
        comboBoxPriorite = new JComboBox<>(prioriteOptions);
        comboBoxPriorite.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        datePicker = new JDateChooser();
        datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        String[] etatOptions = {"À faire", "En cours", "Terminée"};
        comboBoxEtat = new JComboBox<>(etatOptions);
        comboBoxEtat.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boutonEnregistrer.setPreferredSize(new Dimension(120, 40));
        boutonEnregistrer.setBackground(Color.decode("#C38EB4"));
        boutonEnregistrer.setForeground(Color.BLACK);

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
        panelBoutons.add(boutonEnregistrer);
        panelBoutons.add(boutonAnnuler);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(panelBoutons, gbc);

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Créer une tâche");
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

    public JButton getBoutonEnregistrer() {
        return boutonEnregistrer;
    }

    public JButton getBoutonAnnuler() {
        return boutonAnnuler;
    }
}