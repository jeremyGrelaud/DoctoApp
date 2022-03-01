package fr.jeremy.doctoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    private static String MailUser;

    public static String getMailUser() {
        return MailUser;
    }

    public static void setMailUser(String mailUser) {
        MailUser = mailUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon color
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                CreateFolderDoctoapp();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    /*La methode permet de basculer de l'acitvite login a register et override les transitions en cours*/
    public void onLoginClick(View view){
        final String FILE_NAME = "loggin.jggd";

        EditText mEditTextMail = findViewById(R.id.editTextEmail);
        EditText mEditTextPassword = findViewById(R.id.editTextPassword);

        String textMail = mEditTextMail.getText().toString();
        String textPassword = mEditTextPassword.getText().toString();

        if(textMail.length() < 6) {
            mEditTextMail.requestFocus();
            mEditTextMail.setError("Champ invalide");
        }
        else if(textPassword.length() < 8) {
            mEditTextPassword.requestFocus();
            mEditTextPassword.setError("Champ invalide");
        }
        else {

            FileInputStream loginFile = null;
            try {
                loginFile = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(loginFile);
                BufferedReader buffReader = new BufferedReader(isr);

                int continuer = 1;
                String text;
                do{
                    text = buffReader.readLine();
                    if(text == null) {
                        continuer = 2;
                    }
                    else if(text.equals(textMail)) {
                        buffReader.readLine();
                        text = buffReader.readLine();
                        if(text.equals(textPassword)){
                            continuer = 0;
                        }
                    }

                }while(continuer == 1);

                if(continuer == 0) {
                    //nom du compte qui vient de se connecter
                    MailUser=textMail;
                    startActivity(new Intent(this, MainActivity.class));
                    /*overridePendingTransition(R.anim.slide_in_right, R.anim.stay);*/
                }
                else if (continuer == 2){
                    Toast.makeText(this, "Compte inexistant", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(loginFile != null) {
                    try {
                        loginFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void onRegisterClick(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        /*overridePendingTransition(R.anim.slide_in_right, R.anim.stay);*/
    }

    private void CreateFolderDoctoapp(){
        //Initialisation du fichier
        File file = new File(Environment.getExternalStorageDirectory(), "Doctoapp");
        //check
        if(file.exists()){
            //cas ou il existe deja
            //Toast.makeText(getApplicationContext(), "Existe deja",Toast.LENGTH_SHORT).show();
        }
        else{
            //cas ou il faut le créer
            file.mkdirs();
            //check
            if(file.isDirectory()){
                //quand le directory est crée
                //Toast.makeText(getApplicationContext(), "Directory created", Toast.LENGTH_SHORT).show();

            }
            else{
                //alert
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                String Message = "Message : failed to create directory"+
                        "\nPath :"+ Environment.getExternalStorageDirectory()+
                        "\nmkdirs :" +file.mkdirs();
                builder.setMessage(Message);
                builder.show();
            }
        }
    }
}