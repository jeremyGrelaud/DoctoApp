package fr.jeremy.doctoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Rdv extends AppCompatActivity {
    private LinearLayout linearLayout;

    String filename = "";
    String filepath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);

        // On récupère le 'LinearLayout' courant
        linearLayout = (LinearLayout) findViewById(R.id.linear_rdv);

        // On récupère le bouton grâce à l'id
        Button button = (Button) findViewById(R.id.ajouter);

        filename = "Rdv.txt";
        filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());

        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if (mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;

                do {
                    try {
                        line = br.readLine();

                        if (line != null) {
                            String[] tab = line.split("!@!");
                            String content = tab[0] + " " + tab[1] + " " + tab[2] + " " + tab[3];
                            //content est le string a mettre dans le nouveaux textview


                            //-----------Formule pour convertir de px --> dp -------------
                            int padding_in_dp = 10;  // 10 dps
                            int size_in_dp = 18;
                            final float scale = getResources().getDisplayMetrics().density;
                            int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
                            int size_in_px = (int) (size_in_dp * scale + 0.5f);
                            //------------------------------------------------------------


                            TextView textView = new TextView(Rdv.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);

                            layoutParams.setMargins(0, padding_in_px, 0, 0);
                            textView.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);

                            textView.setText(content);
                            textView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                            // Pour je ne sais quel raison 'setBackgroundResource' doit etre apres
                            textView.setBackgroundResource(R.drawable.text_view_background);
                            textView.setTextColor(Color.parseColor("#E2233D"));
                            textView.setTextSize(size_in_dp);

                            // Si on définit pas les layoutParams on peut mettre juste 'textView'
                            linearLayout.addView(textView, layoutParams);

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

    // Méthode rédirigeant vers un nouveau layout
    public void addRdv(View view) {
        startActivity(new Intent(this, SaisieRdv.class));
        finish();
        //ferme l'ancien rdv pour pas avoir de double
    }

    public void modifyRdv(View view) {
        Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
    }
}