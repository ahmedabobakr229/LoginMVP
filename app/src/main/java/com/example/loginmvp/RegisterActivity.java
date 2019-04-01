package com.example.loginmvp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    TextView signin ;
    TextView signup ;
    ImageView signinLine , signupLine ;

    EditText username , email , password , mobile , confirmPass , adres , contry , regon ;

    String nam , emal , pass , mob , conf_pass , address , contryy , regonn , Uname ;

    String word= "";

    Button signUpbtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.pass);
        mobile = findViewById(R.id.mobile_number);
        confirmPass = findViewById(R.id.conf_pass);
        adres = findViewById(R.id.adress);
        contry = findViewById(R.id.country);
        regon = findViewById(R.id.region);

        signin = findViewById(R.id.signIn_reg);
        signup = findViewById(R.id.signUp_reg);

        signinLine = findViewById(R.id.signinLine_reg);
        signupLine = findViewById(R.id.signupLine_reg);

        signUpbtn = findViewById(R.id.registerbtn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, GoogleActivity.class);
                startActivity(i);

            }
        });
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usersignup();
            }
        });
    }

    private void Usersignup() {

        nam = username.getText().toString().trim();
        emal = email.getText().toString().trim();
        mob = mobile.getText().toString().trim();
        pass = password.getText().toString().trim();
        conf_pass = confirmPass.getText().toString().trim();
        //address , contryy , regonn

        address = adres.getText().toString().trim();
        contryy = contry.getText().toString().trim();
        regonn = regon.getText().toString().trim();
        Uname = username.getText().toString().trim();

        if (nam.isEmpty()) {
            username.setError("Enter your Name");
            username.requestFocus();
            return;
        }
        if (emal.isEmpty()) {
            email.setError("Enter your email");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emal).matches()) {
            email.setError("Enter avalid Email");
            return;
        }
        if (mob.isEmpty()) {
            mobile.setError("Enter Phone number");
        }
        if (!Patterns.PHONE.matcher(mob).matches()) {
            mobile.setError("Enter avalid phone number");
            return;

        }
        if (pass.length() < 6) {
            password.setError("password length should be more than 5 charachter");
            password.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("Enter the  password");
            password.requestFocus();
            return;
        }

        else {
            Call<ResponseBody>call= RetrofitClient.getInstance()
                    .getApi().setUser(Uname , nam, emal, pass, address,
                           regonn , contryy , mob);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                        Log.i("response",response.body().string());
                        Toast.makeText(RegisterActivity.this, "" + R.string.suc_reg, Toast.LENGTH_SHORT).show();
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

