package fr.wildcodeschool.gooddeals;

public class UserModel {

    private String pseudo;


    public UserModel(String pseudo) {
        this.pseudo = pseudo;

    }

    public UserModel() {
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
