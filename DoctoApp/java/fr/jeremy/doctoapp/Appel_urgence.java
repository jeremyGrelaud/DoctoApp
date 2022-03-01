package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Appel_urgence extends AppCompatActivity {

    String filename = "Tuteurs.txt";
    String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());
    String filecontent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appel_urg);


        /**On va chercher les 2 tuteurs d importance 1 dans el fichier tuteur et display leurs infos*/
        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        TextView nom_1, nom_2;
        nom_1 = findViewById(R.id.nom_contact1);
        nom_2 = findViewById(R.id.nom_contact2);

        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                String nom="";
                String prenom="";
                String tel="";
                String ordre="";
                int index=1;

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("  ");

                            //tab[0] c'est le nom
                            //tab[1] c'est le prenom
                            //tab[2] c'est le tel
                            //tab[3] c'est le ordre
                            nom=tab[0];
                            prenom=tab[1];
                            tel=tab[2];
                            ordre=tab[3];

                            //premier tuteur à ajouter
                            if(index==1 && ordre.equals("1")){

                                LinearLayout layout1 = findViewById(R.id.contact_med_legal);
                                //on doit ajouter une textview qui contient le num de tel
                                TextView tel1 = new TextView(this);
                                tel1.setText(tel);
                                tel1.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                ((LinearLayout) layout1).addView(tel1);

                                tel1.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                tel1.setTextSize(getResources().getDimension(R.dimen.text_appel_size));
                                tel1.setTypeface(tel1.getTypeface(), Typeface.BOLD);

                                String nom_contact1 = nom + " " + prenom;
                                nom_1.setText(nom_contact1);
                                index++;
                            }
                            else if(index==2 && ordre.equals("1")){
                                //deuxième tuteur à ajouter

                                LinearLayout layout2 = findViewById(R.id.contact_tuteur);
                                //on doit ajouter une textview qui contient le num de tel
                                TextView tel2 = new TextView(this);
                                tel2.setText(tel);
                                tel2.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                ((LinearLayout) layout2).addView(tel2);

                                tel2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                tel2.setTypeface(tel2.getTypeface(), Typeface.BOLD);
                                tel2.setTextSize(getResources().getDimension(R.dimen.text_appel_size));

                                String nom_contact2 = nom + " " + prenom;
                                nom_2.setText(nom_contact2);
                                index++;
                            }
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