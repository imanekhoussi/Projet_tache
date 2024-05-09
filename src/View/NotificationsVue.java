package View;

import Model.Tache;
import Model.TacheDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class NotificationsVue extends JFrame {
    private JTable tableNotifications;
    private DefaultTableModel tableModel;
    private JButton buttonFiltrer;
    private TacheDAO tacheDAO;

    public NotificationsVue(List<Tache> taches, TacheDAO tacheDAO) {
        this.tacheDAO = tacheDAO;
        initComponents(taches);
        initStyle();
        initListeners();
    }

    private void initComponents(List<Tache> taches) {
        setTitle("Notifications");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"Statut", "Tâche", "Date"}, 0);
        populateTableData(taches);

        tableNotifications = new JTable(tableModel);
        tableNotifications.setRowHeight(40);
        tableNotifications.setFont(new Font("Arial", Font.PLAIN, 14));
        tableNotifications.setShowGrid(false);
        tableNotifications.setIntercellSpacing(new Dimension(10, 10));
        tableNotifications.setDefaultRenderer(Object.class, new NotificationCellRenderer());

        JScrollPane scrollPane = new JScrollPane(tableNotifications);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        buttonFiltrer = new JButton("Filtrer");

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonFiltrer, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    private void initStyle() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        UIDefaults defaults = UIManager.getDefaults();
        defaults.put("Table.gridColor", new Color(230, 230, 230));
        defaults.put("Table.font", new Font("Arial", Font.PLAIN, 14));
    }

    private void initListeners() {
        buttonFiltrer.addActionListener(e -> {
            String[] options = {"Toutes les tâches", "Tâches en retard", "Tâches à venir"};
            int choix = JOptionPane.showOptionDialog(this, "Choisissez les tâches à afficher", "Filtre", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choix) {
                case 0:
                    populateTableData(this.tacheDAO.listerTaches());
                    break;
                case 1:
                    populateTableData(this.tacheDAO.listerTaches(), true, false);
                    break;
                case 2:
                    populateTableData(this.tacheDAO.listerTaches(), false, true);
                    break;
            }
        });
    }
    private void populateTableData(List<Tache> taches) {
        tableModel.setRowCount(0);
        for (Tache tache : taches) {
            String statut = tache.getDate().before(new Date()) ? "En retard" : "À venir";
            tableModel.addRow(new Object[]{statut, tache.getTitre(), tache.getDate()});
        }
    }

    private void populateTableData(List<Tache> taches, boolean enRetardOnly, boolean aVenirOnly) {
        tableModel.setRowCount(0);
        for (Tache tache : taches) {
            if (enRetardOnly && tache.getDate().before(new Date())) {
                tableModel.addRow(new Object[]{"En retard", tache.getTitre(), tache.getDate()});
            } else if (aVenirOnly && tache.getDate().after(new Date())) {
                tableModel.addRow(new Object[]{"À venir", tache.getTitre(), tache.getDate()});
            }
        }
    }

    private static class NotificationCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 0) {
                if (value.equals("En retard")) {
                    component.setForeground(Color.RED);
                } else {
                    component.setForeground(new Color(0, 153, 0)); // Vert pour "À venir"
                }
            }

            return component;
        }
    }
}