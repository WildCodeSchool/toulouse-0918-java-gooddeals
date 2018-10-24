package fr.wildcodeschool.gooddeals;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;

public class ProfilFragment extends android.support.v4.app.Fragment {
    static final int CAMERA_REQUEST = 3245;
    private static final int SELECT_PICTURE = 1000;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    public FirebaseAuth mAuth;
    private Uri mImageUri; //Uri object used to tell a ContentProvider(Glide) what we want to access by reference.
    private Bitmap bmp;
    private StorageReference mStorageRef;
    private StorageReference photoStorageRef;
    private DatabaseReference mDatabaseRef; //ne sert pas car pas d'envoie de titleFile
    private ProgressBar mProgressBar;
    private UploadTask uploadTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // FIREBASE pour envoie sur STORAGE
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads"); // creation dossier uploads
        photoStorageRef = FirebaseStorage.getInstance().getReference("upload photos"); // ref CAMERA to FB
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        final View rootView = inflater.inflate(R.layout.activity_profil, container, false);

        // BUTTON POUR UPLOAD TO FIREBASE STORAGE + BIND A LA METHOD UPLOADFILE()
        Button uploadButton = rootView.findViewById(R.id.uploadButton1);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });

        // Button delete
        Button delete = rootView.findViewById(R.id.profile_activity_button_delete);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage(R.string.suppression_compte)
                        .setCancelable(false)
                        .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Deleting user info from database
                                mDatabase.getReference().child(user.getUid()).removeValue();
                                //Deleting user.
                                user.delete();
                                //Signing out and back to login.
                                FirebaseAuth.getInstance().signOut();
                                Singleton.getInstance().singleClear();
                                startActivity(new Intent(getActivity(), NavbarActivity.class));

                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();

            }
        });

       /* ((Button) rootView.findViewById(R.id.button_photo_gallery))
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);
                    }
                });*/

        /*((Button) rootView.findViewById(R.id.buttonPhoto))
                .setOnClickListener(new View.OnClickListener() {*/
        ImageView imgFavorite = rootView.findViewById(R.id.imageViewPhoto);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                selectImage();
            }
        });


        mProgressBar = rootView.findViewById(R.id.progressBar);
        /*Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleProfil = toolbar.findViewById(R.id.toolbar_title);
        titleProfil.setText("MON PROFIL");*/

        return rootView;

    }

    private void selectImage() {


            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Add Photo!");

            builder.setItems(options, new DialogInterface.OnClickListener() {

                @Override

                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo"))

                    {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                        startActivityForResult(intent, 3245);

                    } else if (options[item].equals("Choose from Gallery"))

                    {

                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(intent, 1000);


                    } else if (options[item].equals("Cancel")) {

                        dialog.dismiss();

                    }

                }

            });

            builder.show();

        }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // GALLERY
        ImageView mImageView = getView().findViewById(R.id.imageViewPhoto);
        /*if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK // si on selectionne image
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(getActivity()).load(mImageUri).into(mImageView);
        }
        // CAMERA
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // convert byte array to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                    byteArray.length);
            mImageView.setImageBitmap(bitmap);

        }*/
        if (resultCode == RESULT_OK) {

            if (requestCode == 3245) {

                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();



                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);



                    mImageView.setImageBitmap(bitmap);



                    String path = android.os.Environment

                            .getExternalStorageDirectory()

                            + File.separator

                            + "Phoenix" + File.separator + "default";

                    f.delete();

                    OutputStream outFile = null;

                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        outFile = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        outFile.flush();

                        outFile.close();

                    } catch (FileNotFoundException e) {

                        e.printStackTrace();

                    } catch (IOException e) {

                        e.printStackTrace();

                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 1000) {



                Uri selectedImage = data.getData();

                String[] filePath = { MediaStore.Images.Media.DATA };

                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("image from gallery", picturePath+"");

                mImageView.setImageBitmap(thumbnail);


            }

        }

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

    // METHODE UPLOAD GALLERY

    public void uploadFile() {
        if (mImageUri != null) {
            //TROUVER LE CHEMIN DANS LE PHONE
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            //NEW CODE REMPLACEMENT getDownloadUrl
            final String fileconext = fileReference.toString();
            final StorageReference ref = mStorageRef.child("uploads");
            uploadTask = ref.putFile(mImageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
                // DOWNLOAD URI CHEMIN DE LA PHOTO VERS GOOGLE
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Toast.makeText(getActivity(), downloadUri.toString(), Toast.LENGTH_LONG).show();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }

    }
}









