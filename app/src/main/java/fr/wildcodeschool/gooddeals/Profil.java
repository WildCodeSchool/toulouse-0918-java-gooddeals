package fr.wildcodeschool.gooddeals;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Profil extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Method for button Google sign in
        findViewById(R.id.google_sign_in).setOnClickListener((View.OnClickListener) this);




        //Button for logout
        ImageButton btOnline = findViewById(R.id.imageButtonOnline);
        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profil.this, NavbarActivity.class));

            }
        });

            Button btLogOut = findViewById(R.id.log_out_button);
            btLogOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profil.this, NavbarActivity.class));
            }
        });

        ((Button) findViewById(R.id.button_photo_gallery))
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {

                        // in onCreate or any event where your want the user to
                        // select a file
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);
                    }
                });



        ((Button)findViewById(R.id.buttonPhoto))
                .setOnClickListener(new View.OnClickListener() {
        ImageView imageView = (ImageView)findViewById(R.id.imageViewPhoto);
                    @Override
                    public void onClick(View view) {


                    }
                });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}





