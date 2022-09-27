package com.example.sertifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sertifikasi.database.AppDatabase;
import com.example.sertifikasi.database.Entity.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Activity_Login extends AppCompatActivity {

    private MaterialButton btnLogin, btnRegister;
    private TextInputEditText usernameInput , passwordInput;
    private AppDatabase database;
    private TextInputLayout layoutInputUsername , layoutInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.loginBtn);
        btnRegister =findViewById(R.id.registerBtn);

        usernameInput = findViewById(R.id.inputUsernameLogin);
        passwordInput = findViewById(R.id.inputPasswordLogin);

        layoutInputUsername = findViewById(R.id.usernameLayout);
        layoutInputPassword = findViewById(R.id.passwordLayout);

        database = AppDatabase.getInstance(getApplicationContext());


        ValidUser();
        ValidPassword();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean validationUsername = usernameInput.getText().length() != 0 ;
                boolean validationPassword = passwordInput.getText().length() != 0;

                if (validationUsername && validationPassword ) {
                    submitLogin();
                }else {
                    errorMessage();
                }

            }
        });

        usernameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b ) {
                    layoutInputUsername.setError(ValidUser());
                }
            }
        });

        passwordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutInputPassword.setError(ValidPassword());
            }
        });

        //handle back button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Login.this , MainActivity.class));
            }
        });

    }

    private void submitLogin() {
        String getUsername = usernameInput.getText().toString();
        String getPassword = passwordInput.getText().toString();


        if ( getUsername.length() != 0 && getPassword.length() != 0 ) {

            //check data jika ada
            boolean checkUser = database.userDao().check(getUsername);
            //jika data tidak null
            if(checkUser ) {
                User users = database.userDao().dataUser(getUsername);
                if (getUsername.equals(users.username) && getPassword.equals(users.password)){
                    succesLogin(users.username, users.id);
                }else {
                    errorLogin();
                }
            }else {
                if (getUsername.equals("admin") && getPassword.equals("admin")) {
                    startActivity(new Intent(Activity_Login.this, Activity_Hasil.class));
                }else {
                    errorLogin();
                }
            }

        } else {
            validUsernameLogin();
            validPasswordLogin();
        }



    }

    private CharSequence validPasswordLogin() {
        if (passwordInput.getText().length() == 0 ) {
            return "Input Password";
        }
        return null;
    }

    private CharSequence validUsernameLogin() {

        if (usernameInput.getText().length() == 0 ) {
            return "Input Username";
        }

        return null;
    }
    private void errorLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        builder.setTitle("Login Failed")
                .setMessage("Please Check Username or password !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        usernameInput.getText().clear();
                        passwordInput.getText().clear();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void succesLogin(String username , int id ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        builder.setTitle("Login Success")
                .setMessage("Welcome Back " + username + "!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Activity_Login.this , Activity_Client.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void errorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Login.this);
        builder.setTitle("Error")
                .setMessage("Please fill all input ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private CharSequence ValidUser() {
        if (usernameInput.getText().length() == 0 ) {
            return "Input Username";
        }

        return null;
    }

    private CharSequence ValidPassword() {
        if (usernameInput.getText().length() == 0 ) {
            return "Input Username";
        }

        return null;
    }
}