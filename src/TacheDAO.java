import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/dbtache";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public static void insererTache(Tache tache) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO listetache (Id, Titre, Description, Priorité, Date, Etat) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tache.getId());
            statement.setString(2, tache.getTitre());
            statement.setString(3, tache.getDescription());
            statement.setString(4, tache.getPriorite());
            statement.setString(5, tache.getDate());
            statement.setString(6, tache.getEtat());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de la tâche : " + ex.getMessage());
        }
    }

    public static List<Tache> recupererTaches() {
        List<Tache> taches = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM listetache";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("Id");
                String titre = resultSet.getString("Titre");
                String description = resultSet.getString("Description");
                String priorite = resultSet.getString("Priorité");
                String date = resultSet.getString("Date");
                String etat = resultSet.getString("Etat");
                Tache tache = new Tache(id, titre, description, priorite, date, etat);
                taches.add(tache);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des tâches : " + ex.getMessage());
        }
        return taches;
    }

    public static void mettreAJourTache(Tache tache) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE listetache SET Titre = ?, Description = ?, Priorité = ?, Date = ?, Etat = ? WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tache.getTitre());
            statement.setString(2, tache.getDescription());
            statement.setString(3, tache.getPriorite());
            statement.setString(4, tache.getDate());
            statement.setString(5, tache.getEtat());
            statement.setString(6, tache.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour de la tâche : " + ex.getMessage());
        }
    }

    public static void supprimerTache(String id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM listetache WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la tâche : " + ex.getMessage());
        }
    }
}