package com.example.loginmvp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookButtonBase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    @BindView(R.id.signin_google)
    Button googleLogin ;

    @BindView(R.id.signIn)
    TextView signin;

    @BindView(R.id.signUp)
    TextView signup;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.pass)
    EditText pass ;

    @BindView(R.id.forgetPass)
    TextView forget;

    @BindView(R.id.signinBtn)
    Button signInBtn ;

    @BindView(R.id.facebookbtn_clcik)
    Button faceLogin ;

    @BindView(R.id.signinLine)
    ImageView signLine ;

    @BindView(R.id.signupLine)
    ImageView sinupLine ;

    GoogleApiClient googleApiClient ;
    private static final int REQ_CODE = 9001 ;

    String emal , passw ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        ButterKnife.bind(this);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin();
            }
        });

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.loginmvp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("hash :", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        //SignUp Button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin.setTextColor(getResources().getColor(android.R.color.darker_gray));
                sinupLine.setVisibility(View.VISIBLE);
                Intent i = new Intent(GoogleActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.signin_google :
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void handleResult(GoogleSignInResult googleSignInResult){
        if (googleSignInResult.isSuccess()){

            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String image = account.getPhotoUrl().toString();

            Intent i = new Intent(GoogleActivity.this , StartActivity.class );
            i.putExtra("name" , name);
            i.putExtra("email" , email);
            i.putExtra("image",image).toString();
            startActivity(i);

            Toast.makeText(this, "name" + name +"\n" + "email" + email + "\n" + "image" + image, Toast.LENGTH_LONG).show();

            updateUI(true);
        }else {
            updateUI(false);
        }
    }
    private void signIn(){

        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i , REQ_CODE);


        Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();

    }
    
    private void signOut(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }
    private void UserLogin (){


        emal = email.getText().toString().trim();
        passw = pass.getText().toString().trim();

        if (emal.isEmpty()) {
            email.setError("Enter your email");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emal).matches()) {
            email.setError("Enter avalid Email");
            return;
        }

        if (passw.length() < 6) {
            pass.setError("password length should be more than 5 charachter");
            pass.requestFocus();
            return;
        }
        if (passw.isEmpty()) {
            pass.setError("Enter the  password");
            pass.requestFocus();
            return;
        }

        else {
            Call<ResponseBody> call= RetrofitClient.getInstance()
                    .getApi().setLogin(emal,passw);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response.body().string());
                        Log.i("response",response.body().string());
                        Toast.makeText(GoogleActivity.this, "Login" , Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }
}
