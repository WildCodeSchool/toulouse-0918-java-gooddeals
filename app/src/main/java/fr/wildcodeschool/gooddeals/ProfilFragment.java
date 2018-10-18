package fr.wildcodeschool.gooddeals;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilFragment extends android.support.v4.app.Fragment {
    static final int CAMERA_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;
    private FirebaseAuth mAuth;
    private String selectedImagePath;
    private boolean mIsResolving = false;
    private boolean mShouldResolve = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profil, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageButton btOnline = getView().findViewById(R.id.imageButtonOnline);
        btOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NavbarActivity.class));
            }
        });

        Button btLogOut = getView().findViewById(R.id.log_out_button);
        btLogOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), NavbarActivity.class));
            }
        });

        ((Button) getView().findViewById(R.id.button_photo_gallery))
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,
                                "Select Picture"), SELECT_PICTURE);
                    }
                });

        ((Button) getView().findViewById(R.id.buttonPhoto))
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageView mImageView = getView().findViewById(R.id.imageViewPhoto);

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(imageBitmap);
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}




