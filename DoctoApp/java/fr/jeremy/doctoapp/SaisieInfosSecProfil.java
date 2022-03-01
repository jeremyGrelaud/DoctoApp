package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaisieInfosSecProfil extends AppCompatActivity {


    Button btnsave;
    EditText medecins, pathologies, traitements, vaccin;
    String filename ="";
    String filepath="";
    String filecontent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_infos_sec_profil);


        btnsave= findViewById(R.id.ajoutbtn_infos_sec_profil);
        medecins=findViewById(R.id.editTextMedecins);
        pathologies=findViewById(R.id.editTextPathologies);
        traitements=findViewById(R.id.editTextTraitements);
        vaccin=findViewById(R.id.editTextVaccin);


        filename="Infos_Secondaires_Profil.txt";
        filepath=LoginActivity.getMailUser();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filecontent = medecins.getText().toString().trim()
                        +"!@!"+ pathologies.getText().toString().trim()
                        +"!@!"+ traitements.getText().toString().trim()
                        +"!@!"+ vaccin.getText().toString().trim()
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
                    medecins.setText("");
                    pathologies.setText("");
                    traitements.setText("");
                    vaccin.setText("");

                    Toast.makeText(SaisieInfosSecProfil.this, "Infos sauvegardées",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SaisieInfosSecProfil.this, "Les champs sont vides",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActualiserProfil(View view) {
        startActivity(new Intent(this, Profil.class));
        finish();
    }
}