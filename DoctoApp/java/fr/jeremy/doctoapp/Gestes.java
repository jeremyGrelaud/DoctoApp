package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class Gestes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestes);
    }

    public void onEttoufementsClick(View view) {
        String url = "https://www.croix-rouge.fr/Je-me-forme/Particuliers/Les-6-gestes-de-base/L-etouffement";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }
    public void onBruluresClick(View view) {
        String url = "https://www.passeportsante.net/fr/Actualites/Dossiers/DossierComplexe.aspx?doc=premiers-gestes-brulures";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void onCarAccidentClick(View view) {
        String url = "https://www.ornikar.com/code/cours/securite/premiers-secours";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void onPbCardClick(View view) {
        String url = "https://www.croix-rouge.fr/Je-me-forme/Particuliers/Les-6-gestes-de-base/L-arret-cardiaque-les-gestes-de-secours";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }


}