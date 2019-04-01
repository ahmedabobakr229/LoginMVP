package com.example.loginmvp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class StartActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    DrawerLayout drawerLayout ;
    ActionBarDrawerToggle toggle;

    TextView nname ;
    TextView eemail ;
    
    LinearLayout linearLayout , layout ;
    FrameLayout frameLayout ;

    NavigationView navigationView ;

    private static final int REQ_CODE = 9001 ;

    GoogleApiClient googleApiClient ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        
        linearLayout = findViewById(R.id.container_linear);
        frameLayout = findViewById(R.id.frameheader);
        layout = findViewById(R.id.linear);

       // View header = navigationView.getHeaderView(R.layout.header);
//
//        View headerLayout =
//                navigationView.inflateHeaderView(R.layout.navigation_header);

        View headerLayout = navigationView.getHeaderView(0);

        nname =headerLayout.findViewById(R.id.getName);
        eemail =headerLayout.findViewById(R.id.getEmail);

        mToolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_View);

        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        try{
            nname.setText(getIntent().getExtras().getString("name"));
            eemail.setText(getIntent().getExtras().getString("email"));
        }catch (NullPointerException e){

        }

    }

    private void handleResult(GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image = account.getPhotoUrl().toString();

            nname.setText(name);
            eemail.setText(email);


            nname.setText(getIntent().getExtras().getString("name"));
            eemail.setText(getIntent().getExtras().getString("email"));

            Toast.makeText(this, "name is :" + name, Toast.LENGTH_SHORT).show();

        }else {
            //  updateUI(false);
        }

    }
    private void updateUI(boolean isLogin){

        if (isLogin){
//            linearLayout.setVisibility(View.VISIBLE);
//            signIn.setVisibility(View.GONE);
        }else {
//            linearLayout.setVisibility(View.GONE);
//            signIn.setVisibility(View.VISIBLE);
        }
    }

    private void signOut(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Toast.makeText(StartActivity.this, "home", Toast.LENGTH_SHORT).show();

                    return true;
                case R.id.navigation_albums:
                    Toast.makeText(StartActivity.this, "albums", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_fav:
                    Toast.makeText(StartActivity.this, "fav", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_artists:
                    Toast.makeText(StartActivity.this, "artists", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_alums:
                    Toast.makeText(StartActivity.this, "albums", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.button_menu , menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.navigation_artists){
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.settings){
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.shops) {
            Toast.makeText(this, "shops", Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId() == R.id.complain){
            Toast.makeText(this, "complain", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.reserv){
            Toast.makeText(this, "reservations", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
