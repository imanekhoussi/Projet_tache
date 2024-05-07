package View;

import Model.Tache;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;

public class TacheModifierVue extends JFrame {
    private JTextField champTitre;
    private JTextField champDescription;
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
        champDescription = new JTextField(tache.getDescription());
        String[] prioriteOptions = {"Élevée", "Moyenne", "Faible"};
        comboBoxPriorite = new JComboBox<>(prioriteOptions);
        comboBoxPriorite.setSelectedItem(tache.getPriorite());
        datePicker = new JDateChooser();
        datePicker.setDate(tache.getDate());
        String[] etatOptions = {"À faire", "En cours", "Terminée"};
        comboBoxEtat = new JComboBox<>(etatOptions);
        comboBoxEtat.setSelectedItem(tache.getEtat());
        boutonConfirmer = new JButton("Confirmer");
        boutonAnnuler = new JButton("Annuler");

        // Configuration de la disposition des composants
        setLayout(new GridLayout(7, 2));
        add(new JLabel("Titre :"));
        add(champTitre);
        add(new JLabel("Description :"));
        add(champDescription);
        add(new JLabel("Priorité :"));
        add(comboBoxPriorite);
        add(new JLabel("Date :"));
        add(datePicker);
        add(new JLabel("État :"));
        add(comboBoxEtat);
        add(boutonConfirmer);
        add(boutonAnnuler);

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Modifier tâche");
        pack();
        setLocationRelativeTo(null);
    }

    public JTextField getChampTitre() {
        return champTitre;
    }

    public JTextField getChampDescription() {
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