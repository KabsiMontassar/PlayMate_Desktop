package entity;
public class AvisTerrain {
    private int idAvis;
    private int terrain_id; // cle étrangère reference à 'id du terrain
    private int note;
    private String commentaire;
    private String date_avis;
    private Terrain terrain; //Terrain associé à l'avis (jointure) //chaque avis lié à un terrain par fk 'terrain_id'.
    //*******************************************************************************************
    public AvisTerrain(int terrain_id, String commentaire, int note, String date_avis) {
        this.terrain_id = terrain_id;
        this.commentaire = commentaire;
        this.note = note;
        this.date_avis = date_avis;
        this.terrain = new Terrain();
    }
    public AvisTerrain(int idAvis, int terrain_id, int note, String commentaire, String date_avis, Terrain terrain) {
        this.idAvis = idAvis;
        this.terrain_id = terrain_id;
        this.note = note;
        this.commentaire = commentaire;
        this.date_avis = date_avis;
        this.terrain = terrain;
    }
    public AvisTerrain() {}
    //*******************************************************************************************
    public Terrain getTerrain() {return terrain;}
    public void setTerrain(Terrain terrain) {this.terrain = terrain;}
    public int getIdAvis() {
        return idAvis;
    }
    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }
    public int getTerrain_id() {return terrain_id;}
    public void setTerrain_id(int id) {
        this.terrain_id = terrain_id;
    }
    public String getCommentaire() {
        return commentaire;
    }
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public int getNote() {return note;}
    public void setNote(int note) {
        this.note = note;
    }
    public String getDate_avis() {
        return date_avis;
    }
    public void setDate_avis(String date_avis) {
        this.date_avis = date_avis;
    }
    //*******************************************************************************************
    @Override
    public String toString() {
        return "AvisTerrain{" +
                "idAvis=" + idAvis +
                ", terrain_id=" + terrain_id +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                ", date_avis='" + date_avis + '\'' + '}';
    }
}