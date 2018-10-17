package fr.wildcodeschool.gooddeals;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends android.support.v4.app.Fragment {
    static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;
    private Uri mImageUri; //load image
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef; //ne sert pas car pas d'envoie de titleFile
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.activity_profil, container, false);
        Button btLogOut = rootView.findViewById(R.id.log_out_button);
        btLogOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), NavbarActivity.class));
            }
        });

        ((Button) rootView.findViewById(R.id.button_photo_gallery))
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);
                    }
                });

        ((Button) rootView.findViewById(R.id.buttonPhoto))
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
        Button uploadButton = rootView.findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        mProgressBar = rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //modif
        ImageView mImageView = getView().findViewById(R.id.imageViewPhoto);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK // si on selectionne image
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
        }
        Glide.with(getActivity()).load(mImageUri).into(mImageView);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    // METHODE POUR GERER L'EXTENSION DE L'IMAGE (JPEG...)
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    // METHODE UPLOAD POUR LE BUTTON
    // POUR ATTACHER UN TITRE A L'IMAGE : EditText mEditTextFileName = findViewById(R.id.edit_text_file_name);
    public void uploadFile() {
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
                            Toast.makeText(getActivity(), "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}





