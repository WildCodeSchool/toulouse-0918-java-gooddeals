package fr.wildcodeschool.gooddeals;

public class DealsModel {


    //attibuts
    private String nom;
    private String reduction;
    private String description;
    private String image;



    //constructeurs
    public DealsModel (String nom, String reduction, String description, String image) {
        this.nom = nom;
        this.reduction = reduction;
        this.description = description;
        this.image = image;

    }

    //getters
    public String getNom() {
        return this.nom;

    }

    public String getReduction() {
        return this.reduction;

    }

    public String getDescription() {
        return this.description;

    }


    //setters

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;

    }

    public void setDescription(String description) {
        this.description = description;
    }


}
