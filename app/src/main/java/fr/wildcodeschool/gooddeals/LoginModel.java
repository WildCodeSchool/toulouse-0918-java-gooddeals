package fr.wildcodeschool.gooddeals;


import android.os.Parcel;
import android.os.Parcelable;

public class LoginModel implements Parcelable {

        private String email;
        private String photo;
        private String pseudo;

        public LoginModel(String email, String photo, String pseudo) {
            this.email = email;
            this.photo = photo;
            this.pseudo = pseudo;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoto() {
            return photo;
        }
        public String getPseudo() {
            return pseudo;
        }



    protected LoginModel(Parcel in) {
        email = in.readString();
        photo = in.readString();
        pseudo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(photo);
        dest.writeString(pseudo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };


}
