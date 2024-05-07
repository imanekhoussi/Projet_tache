package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TacheDAO {
    private static final String _JDBC_URL_ = "jdbc:mysql://localhost:3306/dbtache";
    private static final String _JDBC_USER_ = "root";
    private static final String _JDBC_PASSWORD_ = "";

    public void insererTache(Tache tache) {
        try (Connection conn = DriverManager.getConnection(_JDBC_URL_, _JDBC_USER_, _JDBC_PASSWORD_)) {
            String sql = "INSERT INTO listetache (Titre, Description, Priorité, Date, Etat) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tache.getTitre());
            statement.setString(2, tache.getDescription());
            statement.setString(3, tache.getPriorite());
            statement.setDate(4, new java.sql.Date(tache.getDate().getTime()));
            statement.setString(5, tache.getEtat());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tache.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'insertion de la tâche : " + ex.getMessage());
        }
    }

    public void modifierTache(Tache tache) {
        try (Connection conn = DriverManager.getConnection(_JDBC_URL_, _JDBC_USER_, _JDBC_PASSWORD_)) {
            String sql = "UPDATE listetache SET Titre = ?, Description = ?, Priorité = ?, Date = ?, Etat = ? WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, tache.getTitre());
            statement.setString(2, tache.getDescription());
            statement.setString(3, tache.getPriorite());
            statement.setDate(4, new java.sql.Date(tache.getDate().getTime()));
            statement.setString(5, tache.getEtat());
            statement.setInt(6, tache.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la modification de la tâche : " + ex.getMessage());
        }
    }

    public void supprimerTache(int id) {
        try (Connection conn = DriverManager.getConnection(_JDBC_URL_, _JDBC_USER_, _JDBC_PASSWORD_)) {
            String sql = "DELETE FROM listetache WHERE Id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de la tâche : " + ex.getMessage());
        }
    }

    public List<Tache> listerTaches() {
        List<Tache> taches = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(_JDBC_URL_, _JDBC_USER_, _JDBC_PASSWORD_)) {
            String sql = "SELECT * FROM listetache";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String titre = resultSet.getString("Titre");
                String description = resultSet.getString("Description");
                String priorite = resultSet.getString("Priorité");
                Date date = resultSet.getDate("Date");
                String etat = resultSet.getString("Etat");
                Tache tache = new Tache(titre, description, priorite, date, etat);
                tache.setId(id);
                taches.add(tache);
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des tâches : " + ex.getMessage());
        }
        return taches;
    }
}