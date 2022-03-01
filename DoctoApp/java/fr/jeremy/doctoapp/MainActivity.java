package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //liste des icones
        List<Icons> listedesicones = new ArrayList<>();
        listedesicones.add(new Icons("Rendez-Vous","rendez_vous"));
        listedesicones.add(new Icons("Ordonnances","infos_medic"));
        listedesicones.add(new Icons("Pilullier", "pilullier"));
        listedesicones.add(new Icons("Appel urgence","appel_urgence"));
        listedesicones.add(new Icons("Mes tuteurs","tuteurs"));
        listedesicones.add(new Icons("Les gestes","gestes"));
        listedesicones.add(new Icons("Mes notices","notices"));
        listedesicones.add(new Icons("Trouver un médecin","recherche_med"));
        listedesicones.add(new Icons("Contacts", "contacts"));
        listedesicones.add(new Icons("Profil","profil"));
        listedesicones.add(new Icons("Aides","aides"));
        listedesicones.add(new Icons("Paramètres","parametres"));


        //get list view
        GridView iconsGridView = findViewById(R.id.menu_doctoApp);
        iconsGridView.setAdapter(new Icons_menuAdapter(this, listedesicones));

    }





}