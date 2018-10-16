package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;

public class Profil extends AppCompatActivity {
    static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;
    private FirebaseAuth mAuth;
    private String selectedImagePath;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Method for button Google sign in
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

        ((Button) findViewById(R.id.buttonPhoto))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImageView = findViewById(R.id.imageViewPhoto);

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(imageBitmap);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}




