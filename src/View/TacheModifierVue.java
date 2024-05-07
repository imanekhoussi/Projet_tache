package View;

import Model.Tache;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;

public class TacheModifierVue extends JFrame {
    private JTextField champTitre;
    private JTextArea champDescription;
    private JComboBox<String> comboBoxPriorite;
    private JDateChooser datePicker;
    private JComboBox<String> comboBoxEtat;
    private JButton boutonConfirmer;
    private JButton boutonAnnuler;

    public TacheModifierVue(Tache tache) {
        initComponents(tache);
    }

    private void initComponents(Tache tache) {
        // Initialisation des composants graphiques
        champTitre = new JTextField(tache.getTitre());
        champTitre.setFont(new Font("Arial", Font.PLAIN, 16));

        champDescription = new JTextArea(tache.getDescription());
        champDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        champDescription.setLineWrap(true);
        champDescription.setWrapStyleWord(true);

        String[] prioriteOptions = {"Élevée", "Moyenne", "Faible"};
        comboBoxPriorite = new JComboBox<>(prioriteOptions);
        comboBoxPriorite.setSelectedItem(tache.getPriorite());
        comboBoxPriorite.setFont(new Font("Arial", Font.PLAIN, 16));

        datePicker = new JDateChooser();
        datePicker.setDate(tache.getDate());
        datePicker.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] etatOptions = {"À faire", "En cours", "Terminée"};
        comboBoxEtat = new JComboBox<>(etatOptions);
        comboBoxEtat.setSelectedItem(tache.getEtat());
        comboBoxEtat.setFont(new Font("Arial", Font.PLAIN, 16));

        boutonConfirmer = new JButton("Confirmer");
        boutonConfirmer.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonConfirmer.setPreferredSize(new Dimension(120, 40));

        boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.setFont(new Font("Arial", Font.PLAIN, 16));
        boutonAnnuler.setPreferredSize(new Dimension(120, 40));

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

    public JButton getBoutonConfirmer() {
        return boutonConfirmer;
    }

    public JButton getBoutonAnnuler() {
        return boutonAnnuler;
    }
}