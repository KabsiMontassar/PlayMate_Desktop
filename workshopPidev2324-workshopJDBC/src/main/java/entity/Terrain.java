package entity;

import java.util.ArrayList;
import java.util.List;

//*******************************************************************************************
public class Terrain {
    private int id;
    private float prix;
    private int duree;
    private String address;
    private String nomTerrain;
    private String gouvernorat;
    private String  image;
    private String video;
    private boolean gradin;
    private boolean vestiaire;
    private boolean status;
    private String videoPath;
    private String imagePath;
    //*******************************************************************************************
    private List<AvisTerrain> avisList; //référence à une liste d'objets de type AvisTerrain (jointure) '*-1'
    public List<AvisTerrain> getAvisList() {
        return avisList;
    }
    public void setAvisList(List<AvisTerrain> avisList) {
        this.avisList = avisList;
    }
    //*******************************************************************************************
    public Terrain(String address, boolean gradin, boolean vestiaire, boolean status, String nomTerrain, float prix, int duree, String gouvernorat, String image, String video) {
        this.address = address;
        this.gradin = gradin;
        this.vestiaire = vestiaire;
        this.status = status;
        this.nomTerrain = nomTerrain;
        this.prix = prix;
        this.duree = duree;
        this.gouvernorat = gouvernorat;
        this.image = image;
        this.video = video;
        this.avisList = new ArrayList<>();
    }
    public Terrain() {}
    //*******************************************************************************************
    @Override
    public String toString() {
        return "Terrain{" +
                ", id='" + id + '\'' +
                ", nomTerrain='" + nomTerrain + '\'' +
                ", address='" + address + '\'' +
                ", gradin='" + gradin + '\'' +
                ", vestiaire='" + vestiaire + '\'' +
                ", status='" + status + '\'' +
                ", prix=" + prix +
                ", duree=" + duree +
                ", gouvernorat=" + gouvernorat +
                ", image=" + image +
                ", video=" + video +
                '}';
    }
    //*******************************************************************************************
    public String getVideoPath() {
        return videoPath;
    }
    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getNomTerrain() {return nomTerrain;}
    public void setNomTerrain(String nomTerrain) {this.nomTerrain = nomTerrain;}
    public float getPrix() {return prix;}
    public void setPrix(float prix) {this.prix = prix;}
    public int getDuree() {return duree;}
    public void setDuree(int duree) {this.duree = duree;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public boolean getGradin() {return gradin;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public void setGradin(boolean gardin) {this.gradin = gardin;}
    public boolean getVestiaire() {return vestiaire;}
    public void setVestiaire(boolean vestiaire) {this.vestiaire = vestiaire;}
    public boolean getStatus() {return status;}
    public void setStatus(boolean status) {this.status = status;}
    public String getGouvernorat() {return gouvernorat;}
    public void setGouvernorat(String gouvernort) {this.gouvernorat = gouvernort;}
    public String getImage() {return image;}
    public void setImage(String image) {this.image = image;}
    public String getVideo() {return video;}
    public void setVideo(String video) {this.video = video;}}