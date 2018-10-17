package fr.wildcodeschool.gooddeals;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Profil extends AppCompatActivity {
    static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;
    private FirebaseAuth mAuth;
    private String selectedImagePath;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    private Uri mImageUri; //load image
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef; //ne sert pas car pas d'envoie de titleFile
    private ProgressBar mProgressBar;

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

        // FIREBASE pour envoie sur STORAGE
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"); // creation dossier uploads
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        // BUTTON POUR UPLOAD TO FIREBASE STORAGE + BIND A LA METHOD UPLOADFILE()
        Button uploadButton = findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

         mProgressBar = findViewById(R.id.progressBar);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //modif
        ImageView mImageView = findViewById(R.id.imageViewPhoto);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK // si on selectionne image
            && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }


    }

    // METHODE POUR GERER L'EXTENSION DE L'IMAGE (JPEG...)
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    // METHODE UPLOAD POUR LE BUTTON
    // POUR ATTACHER UN TITRE A L'IMAGE : EditText mEditTextFileName = findViewById(R.id.edit_text_file_name);
    public void uploadFile () {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 5000); // 5secondes

                            Toast.makeText(Profil.this,"Upload Successful",Toast.LENGTH_LONG).show();

                            // CODE POUR CREER UNE ID UNIQUE "uploadID" et la rattacher à l'uploadfile= .setValue(upload)
                            /*Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString()); //METHODE GETDOWNLOADURL OBSOLETE
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload); */


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profil.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    // POUR LA PROGRESS BAR
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress); //casté en int sinon pas accépté
                        }
                    });
        } else {
            Toast.makeText(this,"No file selected", Toast.LENGTH_SHORT).show();
        }
    }


    /*

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    */


}




