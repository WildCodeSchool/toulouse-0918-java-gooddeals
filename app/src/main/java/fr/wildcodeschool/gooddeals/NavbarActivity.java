package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;

import java.util.ArrayList;

import static fr.wildcodeschool.gooddeals.BuilderManager.getHamButtonBuilderFilter;

public class NavbarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ATHOME_URL = "https://www.athome-startup.fr/";
    private BoomMenuButton bmb;
    private ArrayList<Deal> deals = new ArrayList<>();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dealRef = database.getReference("deal");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bmb = findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {

            if (i == 0) {
                HamButton.Builder builder = getHamButtonBuilderFilter("Pour Manger");
                bmb.addBuilder(builder);
            } else if (i == 1) {
                HamButton.Builder builder = getHamButtonBuilderFilter("Apéro");
                bmb.addBuilder(builder);
            } else if (i == 2) {
                HamButton.Builder builder = getHamButtonBuilderFilter("Friandises");
                bmb.addBuilder(builder);
            } else if (i == 3) {
                HamButton.Builder builder = getHamButtonBuilderFilter("Bien-être");
                bmb.addBuilder(builder);
            } else if (i == 4) {
                HamButton.Builder builder = getHamButtonBuilderFilter("Loisirs");
                bmb.addBuilder(builder);
            }
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (getIntent().getIntExtra("fragmentNumber", 0) == 1) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.ftMain, new ProfilFragment());
            fragmentTransaction.commit();
        } else {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ftMain, new MapFragment());
            ft.commit();
        }
        View headerview = navigationView.getHeaderView(0);
        ImageView imageUser = headerview.findViewById(R.id.imageDeal);
        TextView pseudoTv = headerview.findViewById(R.id.pseudo_header);
        TextView headerEmailUser = headerview.findViewById(R.id.emailUser_text_view);
        Menu navigationViewMenu = navigationView.getMenu();
        Singleton singleton = Singleton.getInstance();
        boolean hasPhoto = false;
        if (singleton.getLogModel() != null) {
            headerEmailUser.setVisibility(View.VISIBLE);
            pseudoTv.setVisibility(View.VISIBLE);
            navigationViewMenu.findItem(R.id.nav_login).setVisible(false);
            navigationViewMenu.findItem(R.id.nav_logout).setVisible(true);
            headerEmailUser.setText(singleton.getLogModel().getEmail());
            pseudoTv.setText(singleton.getLogModel().getPseudo());
            if (singleton.getLogModel().getPhoto() != null) {
                hasPhoto = true;
                Glide.with(getApplicationContext())
                        .load(singleton.getLogModel().getPhoto())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageUser);
            }


            headerview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.ftMain, new ProfilFragment());
                    ft.commit();
                    drawer.closeDrawers();
                }
            });

        }
        if (!hasPhoto) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.licorne)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(new Intent(NavbarActivity.this, Login.class));
        } else if (id == R.id.nav_map) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ftMain, new MapFragment());
            ft.commit();
        } else if (id == R.id.nav_liste) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ftMain, new ListFragment());
            ft.commit();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Singleton.getInstance().singleClear();
            Toast.makeText(this, R.string.disconnected, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NavbarActivity.this, NavbarActivity.class));
        } else if (id == R.id.atHome_web) {
            Uri uri = Uri.parse(ATHOME_URL);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
