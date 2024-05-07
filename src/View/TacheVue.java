package View;

import Model.Tache;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;

public class TacheVue extends JFrame {
    private JTextField champTitre;
    private JTextArea champDescription;
    private JComboBox<String> comboBoxPriorite;
    private JDateChooser datePicker;
    private JComboBox<String> comboBoxEtat;
    private JButton boutonEnregistrer;

    public TacheVue() {
        initComponents();
    }

    private void initComponents() {
        // Initialisation des composants graphiques
        champTitre = new JTextField();
        champTitre.setFont(new Font("Arial", Font.PLAIN, 16));

        champDescription = new JTextArea();
        champDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);

        comboBoxPriorite = new JComboBox<>(new String[]{"Élevée", "Moyenne", "Faible"});
        comboBoxPriorite.setFont(new Font("Arial", Font.PLAIN, 16));

        datePicker = new JDateChooser();
        datePicker.setFont(new Font("Arial", Font.PLAIN, 16));

        comboBoxEtat = new JComboBox<>(new String[]{"À faire", "En cours", "Terminée"});
        comboBoxEtat.setFont(new Font("Arial", Font.PLAIN, 16));

        boutonEnregistrer = new JButton("Enregistrer");
        boutonEnregistrer.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonEnregistrer.setPreferredSize(new Dimension(120, 40));

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

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(boutonEnregistrer, gbc);

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Créer une tâche");
        pack();
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
}