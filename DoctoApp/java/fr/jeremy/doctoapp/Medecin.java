package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Medecin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecin);
    }

    public void onSearchClick(View view) {

        EditText meditTextName = findViewById(R.id.editTextName);
        EditText meditTextCity = findViewById(R.id.editTextCity);
        EditText meditTextProfession = findViewById(R.id.editTextProfession);

        String textName = meditTextName.getText().toString();
        textName.toLowerCase();
        /*On met en minuscule pcq c'est dans ce format qu'il faut mettre l'url*/
        String textCity = meditTextCity.getText().toString();
        textCity.toLowerCase();
        String textProfession = meditTextProfession.getText().toString();
        textProfession.toLowerCase();

        String url_recherche = "https://www.doctolib.fr/doctors/";
        url_recherche = url_recherche + textName + "/" +  textCity;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_recherche));
        startActivity(browserIntent);

    }
}