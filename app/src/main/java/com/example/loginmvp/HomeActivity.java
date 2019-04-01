package com.example.loginmvp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookButtonBase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {


    TextView Name;
    TextView Email;
    CircleImageView imageView ;
    Button out ;

    Uri uri ;
    private static final int REQ_CODE = 9001 ;

    GoogleApiClient googleApiClient ;

    GoogleSignInResult googleSignInResult ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        Name = findViewById(R.id.name_g);
        Email = findViewById(R.id.email_g);
        imageView = findViewById(R.id.image);
        out = findViewById(R.id.logout_g);

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(HomeActivity.this,GoogleActivity.class);
               startActivity(i);
            }
        });

        Name.setText(getIntent().getExtras().getString("name"));
        Email.setText(getIntent().getExtras().getString("email"));

    }

    private void handleResult(GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image = account.getPhotoUrl().toString();

            Name.setText(name);
            Email.setText(email);


            Name.setText(getIntent().getExtras().getString("name"));
            Email.setText(getIntent().getExtras().getString("email"));

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
}
