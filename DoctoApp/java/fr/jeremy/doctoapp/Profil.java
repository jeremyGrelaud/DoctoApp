package fr.jeremy.doctoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Profil extends AppCompatActivity {

    String filename = "Infos_Principales_Profil.txt";
    String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());
    String filecontent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        ListView list_tuteurs = (ListView) findViewById(R.id.list_tuteurs);
        TextView nom = findViewById(R.id.Nom_profil);
        nom.setText(LoginActivity.getMailUser());

        TextView date,taille,grp_sang;
        date = findViewById(R.id.text_date_naissance);
        taille = findViewById(R.id.text_taille);
        grp_sang = findViewById(R.id.text_grp_sang);

        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                String one="";
                String two="";
                String three="";

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("!@!");

                            //tab[0] c'est la date
                            //tab[1] c'est la taille et le poids
                            //tab[2] c'est le grp sang
                            one=tab[0];
                            two=tab[1];
                            three=tab[2];

                            //normalement il y a une seule ligne
                            date.setText(one);
                            taille.setText(two);
                            grp_sang.setText(three);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



        /**On va mainteant charger les infos des tuteurs*/
        //chaque ligne du fichier  Tuteurs.txt il y a nom puis prénom
        ListView liste_tuteurs = findViewById(R.id.list_tuteurs);
        ArrayList<String> listItems=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItems);

        //set adapter
        liste_tuteurs.setAdapter(adapter);

        FileReader fr2 = null;
        File mon_fichier2 = new File(filepath, "Tuteurs.txt");
        if(mon_fichier2.exists()) {
            try {
                fr2 = new FileReader(mon_fichier2);
                BufferedReader br = new BufferedReader(fr2);

                String line = null;
                String one="";
                String two="";

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("  ");

                            //tab[0] c'est le nom
                            //tab[1] c'est le prénom
                            one=tab[0];
                            two=tab[1];

                            //On creer un nouveau String
                            //String new_tuteur = two + " " + one;

                            //ajout dynamique
                            listItems.add(two + " " + one);
                            adapter.notifyDataSetChanged();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        /**On va mainteant charger les infos des autres listes à partir du fichier  Infos_Secondaires_Profil.txt*/

        //medecins
        ListView liste_medecins = findViewById(R.id.list_medecins);
        ArrayList<String> listMeds=new ArrayList<String>();
        ArrayAdapter<String> adapter_med=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listMeds);
        //set adapter
        liste_medecins.setAdapter(adapter_med);

        //pathologies
        ListView liste_pathologies = findViewById(R.id.list_pathologies);
        ArrayList<String> listPathos=new ArrayList<String>();
        ArrayAdapter<String> adapter_patho=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listPathos);
        //set adapter
        liste_pathologies.setAdapter(adapter_patho);


        //traitements
        ListView liste_traitements = findViewById(R.id.list_traitements);
        ArrayList<String> listTraits=new ArrayList<String>();
        ArrayAdapter<String> adapter_trait=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listTraits);
        //set adapter
        liste_traitements.setAdapter(adapter_trait);


        //vaccins
        ListView liste_vaccins= findViewById(R.id.list_vaccins);
        ArrayList<String> listVacs=new ArrayList<String>();
        ArrayAdapter<String> adapter_vacs=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listVacs);
        //set adapter
        liste_vaccins.setAdapter(adapter_vacs);

        FileReader fr3 = null;
        File mon_fichier3 = new File(filepath, "Infos_Secondaires_Profil.txt");
        if(mon_fichier2.exists()) {
            try {
                fr3 = new FileReader(mon_fichier3);
                BufferedReader br = new BufferedReader(fr3);

                String line = null;
                String one="";
                String two="";
                String three="";
                String four="";

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("!@!");

                            //tab[0] c'est les medecins
                            //tab[1] c'est les pathologies
                            //tab[2] c'est les traitements
                            //tab[3] c'est les vaccins
                            one=tab[0];
                            two=tab[1];
                            three=tab[2];
                            four=tab[3];

                            //On a nos nouveaux text pour les listes

                            //ajout dynamique des medecins
                            listMeds.add(one);
                            adapter_med.notifyDataSetChanged();

                            //ajout dynamique des pathologies
                            listPathos.add(two);
                            adapter_patho.notifyDataSetChanged();

                            //ajout dynamique des traitements
                            listTraits.add(three);
                            adapter_trait.notifyDataSetChanged();

                            //ajout dynamique des vaccins
                            listVacs.add(four);
                            adapter_vacs.notifyDataSetChanged();

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

    public void onAddInfosProfil(View view) {
        startActivity(new Intent(this, SaisieInfosProfil.class));
        //on ferme l'activite precedente pour pas avoir de multiple fenetre
        finish();
    }

    public void onAddInfosSecProfil(View view) {
        startActivity(new Intent(this, SaisieInfosSecProfil.class));
        //on ferme l'activite precedente pour pas avoir de multiple fenetre
        finish();
    }
}
