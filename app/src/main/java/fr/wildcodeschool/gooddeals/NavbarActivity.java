package fr.wildcodeschool.gooddeals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import static fr.wildcodeschool.gooddeals.BuilderManager.getHamButtonBuilderFilter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NavbarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BoomMenuButton bmb;
    public static final String ATHOME_URL = "https://www.athome-startup.fr/";
    private ArrayList<Deal> deals = new ArrayList<>();

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
        View headerview = navigationView.getHeaderView(0);
        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.ftMain, new ProfilFragment());
                ft.commit();
                drawer.closeDrawers();
            }
        });
        
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ftMain, new MapFragment());
        ft.commit();
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
