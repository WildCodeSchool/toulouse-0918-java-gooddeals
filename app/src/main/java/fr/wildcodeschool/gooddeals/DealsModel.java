package fr.wildcodeschool.gooddeals;

public class DealsModel {

    //attibuts
    private String name;
    private String reduction;
    private String description;
    private int icon;

    //constructeurs
    public DealsModel(String name, String reduction, String description, int icon) {
        this.name = name;
        this.reduction = reduction;
        this.description = description;
        this.icon = icon;
    }
    //getters
    public String getName() {
        return this.name;
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
    public void setReduction(String reduction) {
        this.reduction = reduction;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
    public void setNom(String nom) {
        this.name = name;
    }
}
