public class Tache {
    private String id;
    private String titre;
    private String description;
    private String priorite;
    private String date;
    private String etat;

    // Constructeurs
    public Tache() {}

    public Tache(String id, String titre, String description, String priorite, String date, String etat) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.priorite = priorite;
        this.date = date;
        this.etat = etat;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}