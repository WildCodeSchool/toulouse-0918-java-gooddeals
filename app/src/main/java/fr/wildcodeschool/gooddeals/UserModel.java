package fr.wildcodeschool.gooddeals;

public class UserModel {

    private String mPseudo;


    public UserModel(String pseudo) {
        mPseudo = pseudo;

    }

    public UserModel() {
    }

    public String getPseudo() {
        return mPseudo;
    }

    public void setPseudo(String pseudo) {
        this.mPseudo = pseudo;
    }

}
