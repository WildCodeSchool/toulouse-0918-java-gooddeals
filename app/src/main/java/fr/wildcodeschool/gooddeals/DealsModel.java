package fr.wildcodeschool.gooddeals;

public class DealsModel {


    //attibuts
    private String nom;
    private String reduction;
    private String description;
    private int icon;




    //constructeurs
    public DealsModel(String nom, String reduction, String description, int icon) {
        this.nom = nom;
        this.reduction = reduction;
        this.description = description;
        this.icon = icon;


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

    public int getIcon() {
        return this.icon;
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

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
