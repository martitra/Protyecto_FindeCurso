package com.example.soft12.parte_trabajo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.soft12.parte_trabajo.R;
import com.example.soft12.parte_trabajo.activities.SlideScreen.IniciarFragmentActivity;
import com.example.soft12.parte_trabajo.dao.LoginDAO;
import com.example.soft12.parte_trabajo.model.Login;

import java.sql.SQLException;

/**
 * Created by soft12 on 29/06/2015.
 */
public class InicioSesion extends Activity{

    Button btnSignIn;
    LoginDAO loginDAO;
    private EditText txtUsuario;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = (EditText) findViewById(R.id.editTextUserNameToLogin);
        txtPassword = (EditText) findViewById(R.id.editTextPasswordToLogin);

        // create a instance of SQLite Database
        loginDAO = new LoginDAO(this);
        try {
            loginDAO = loginDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Get The Refference Of Buttons
        btnSignIn=(Button)findViewById(R.id.buttonSignIn);

    }

    public void IniciarAplicacion(View V)
    {
        String mail = txtUsuario.getText().toString();
        String password = txtPassword.getText().toString();

        if (!mail.isEmpty() && !password.isEmpty()) {// si no están vacíos
            // fetch the Password form database for respective user name
            Login login = loginDAO.getSinlgeLoginMailEntry(mail);

            // check if the Stored password matches with  Password entered by user
            if (password.equals(login.getPass())) {
                Toast.makeText(InicioSesion.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                Bundle extras = login.getBundle();
                Intent i = new Intent(this, IniciarFragmentActivity.class);
                extras.putBoolean("login", true);
                i.putExtras(extras);
                startActivity(i);
                txtUsuario.setText("");
                txtPassword.setText("");

            } else {
                Toast.makeText(InicioSesion.this, "Ningún usuario con esa contraseña.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(InicioSesion.this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
        }


    }
    public void Salir(View v){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}