package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.BreakIterator;

public class SaisieInfosProfil extends AppCompatActivity {

    Button btnsave;
    EditText date_naissance, taille, grp_sanguin;
    String filename ="";
    String filepath="";
    String filecontent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_infos_profil);


        btnsave= findViewById(R.id.ajoutbtn_infos_profil);
        date_naissance=findViewById(R.id.editTextDateNaissance);
        taille=findViewById(R.id.editTextTaillePoids);
        grp_sanguin=findViewById(R.id.editTextGrpSang);


        filename="Infos_Principales_Profil.txt";
        filepath=LoginActivity.getMailUser();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filecontent = date_naissance.getText().toString().trim()
                        +"!@!"+ taille.getText().toString().trim()
                        +"!@!"+ grp_sanguin.getText().toString().trim()
                        +"!@!";

                //si ce n est pas vide
                if(!filecontent.equals("")){
                    File file = new File(Environment.getExternalStorageDirectory()+"/Doctoapp/"+filepath, filename);
                    try {
                        FileWriter fw = new FileWriter(file,true);
                        BufferedWriter buffer = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(buffer);

                        if(!file.exists()){
                            file.createNewFile();
                        }
                        pw.println(filecontent);
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //on clear les edit text
                    date_naissance.setText("");
                    taille.setText("");
                    grp_sanguin.setText("");

                    Toast.makeText(SaisieInfosProfil.this, "Infos sauvegardées",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SaisieInfosProfil.this, "Les champs sont vides",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActualiserProfil(View view) {
        startActivity(new Intent(this, Profil.class));
    }


}