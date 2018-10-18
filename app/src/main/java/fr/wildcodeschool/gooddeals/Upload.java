package fr.wildcodeschool.gooddeals;

public class Upload {

    private String mName;
    private String mImageUrl;

    //empty constructor pour Firebase
    public Upload() {}

    // Constructeur
    public Upload (String Name, String ImageUrl){
        mName = Name;
        mImageUrl = ImageUrl;
        if (mName.trim().equals("")) {
            mName = "No name";
        }

    }

    // GETTERS
    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    // SETTERS


    public void setName(String Name) {
        this.mName = Name;
    }

    public void setImageUrl(String ImageUrl) {

        this.mImageUrl = ImageUrl;
    }
}
