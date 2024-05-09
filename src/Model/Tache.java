package Model;

import java.util.Date;

public class Tache {
    private static int nextId = 1; // Variable statique pour générer les IDs
    private int id;
    private String titre;
    private String description;
    private String priorite;
    private Date date;
    private String etat;

    public Tache() {
        this.id = nextId++;
    }

    public Tache(String titre, String description, String priorite, Date date, String etat) {
        this.id = nextId++;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.date = date;
        this.etat = etat;
    }
    public Tache(String titre, String description, String priorite, Date date, String etat, int id) {
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.date = date;
        this.etat = etat;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}