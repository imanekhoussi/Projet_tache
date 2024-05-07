package View;

import Model.Tache;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.Date;

public class TacheVue extends JFrame {
    private JTextField champTitre;
    private JTextField champDescription;
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
        champDescription = new JTextField();
        comboBoxPriorite = new JComboBox<>(new String[]{"Élevée", "Moyenne", "Faible"});
        datePicker = new JDateChooser();
        comboBoxEtat = new JComboBox<>(new String[]{"À faire", "En cours", "Terminée"});
        boutonEnregistrer = new JButton("Enregistrer");

        // Configuration de la disposition des composants
        setLayout(new GridLayout(6, 2));
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
        add(new JLabel());
        add(boutonEnregistrer);

        // Configuration de la fenêtre
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Créer une tâche");
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

    public JButton getBoutonEnregistrer() {
        return boutonEnregistrer;
    }
}