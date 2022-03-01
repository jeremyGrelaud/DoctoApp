package fr.jeremy.doctoapp;

import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parametres extends AppCompatActivity {

    EditText nom,mail,tel;
    String filename = "Parametres.txt";
    String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        nom = findViewById(R.id.profil_name);
        mail = findViewById(R.id.edittutor_firstname);
        tel = findViewById(R.id.profil_nphone);

        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);
                String line = null;

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("  ");
                            nom.setHint(tab[0] + "  (Nom)");
                            mail.setHint(tab[1] + "  (Mail)");
                            tel.setHint(tab[2] + "  (N° Tel)");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
