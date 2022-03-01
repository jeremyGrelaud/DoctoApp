package fr.jeremy.doctoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode == 100 && (grantResults.length >0) && (grantResults[0]) ==
                PackageManager.PERMISSION_GRANTED){
            CreateFolder("Test");
        }
        else{
            //permission denied
            Toast.makeText(getApplicationContext(), "Permission Denied",Toast.LENGTH_SHORT).show();
        }
    }*/



    /** Called when the user taps the register button */
    public void onLaunchClick(View view) {
        final String FILE_NAME = "loggin.jggd";

        EditText mEditTextName = findViewById(R.id.editTextName);
        EditText mEditTextMail = findViewById(R.id.editTextEmail);
        EditText mEditTextTel = findViewById(R.id.editTextMobile);
        EditText mEditTextPassword = findViewById(R.id.editTextPassword);

        String textName = mEditTextName.getText().toString();
        String textMail = mEditTextMail.getText().toString();
        String textTel = mEditTextTel.getText().toString();
        String textPassword = mEditTextPassword.getText().toString();

        if (textName.length() < 1) {
            mEditTextName.requestFocus();
            mEditTextName.setError("Le champs est vide");
        } else if (!textName.matches("[a-zA-Z ]+")) {
            mEditTextName.requestFocus();
            mEditTextName.setError("Champ invalide");
        } else if (textMail.length() < 6) {
            mEditTextMail.requestFocus();
            mEditTextMail.setError("Champ invalide");
        } else if (textTel.length() < 10) {
            mEditTextTel.requestFocus();
            mEditTextTel.setError("Champ invalide");
        } else if (textPassword.length() < 8) {
            mEditTextPassword.requestFocus();
            mEditTextPassword.setError("Champ invalide");
        } else {

            FileOutputStream loginFile = null;

            try {
                loginFile = openFileOutput(FILE_NAME, MODE_PRIVATE);
                //faut aller à la fin du fichier actuel


                loginFile.write(textName.getBytes());
                loginFile.write("\n".getBytes());
                loginFile.write(textMail.getBytes());
                loginFile.write("\n".getBytes());
                loginFile.write(textTel.getBytes());
                loginFile.write("\n".getBytes());
                loginFile.write(textPassword.getBytes());
                loginFile.write("\n".getBytes());

                mEditTextName.getText().clear();
                mEditTextMail.getText().clear();
                mEditTextTel.getText().clear();
                mEditTextPassword.getText().clear();
                Toast.makeText(this, "Données sauvegardées", Toast.LENGTH_LONG).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (loginFile != null) {
                    try {
                        loginFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            //creation folder de sauvegarde des données
            //check des permissions
            if (ActivityCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //cas ou on a la permission
                //on appel la methode
                CreateFolder(textMail);
            } else {
                //permission not granted
                //request
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }


            //set le nom de l'utilisateur qui vient de se connecter
            LoginActivity.setMailUser(textMail);

            /**sauvegarder les infos de l'utilisateur pour les parametres*/
            String filename = "Parametres.txt";
            String filepath = textMail;
            String filecontent = textName.trim() + "  " + textMail.trim() + "  " + textTel.trim() + "  ";
            //si ce n est pas vide
            if (!filecontent.equals("")) {
                File file = new File(Environment.getExternalStorageDirectory() + "/Doctoapp/" + filepath, filename);
                try {
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //lancer l appli
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }


    public void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        /*overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);*/

    }

    private void CreateFolder(String nom){
        //Initialisation du fichier
        File file = new File(Environment.getExternalStorageDirectory()+"/Doctoapp", nom);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                String Message = "Message : failed to create directory"+
                        "\nPath :"+ Environment.getExternalStorageDirectory()+"/Doctoapp"+
                        "\nmkdirs :" +file.mkdirs();
                builder.setMessage(Message);
                builder.show();
            }
        }
    }

}




