package fr.jeremy.doctoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Tuteurs extends AppCompatActivity {
    private Button add;
    private Button modify;

    private static String Tel_tuteur_princ;

    EditText name, firstname, tel;
    Spinner order;
    String filename = "Tuteurs.txt";
    String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());
    String filecontent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuteurs);

        //-----------creation du spinner-------------
        Spinner spinner = (Spinner) findViewById(R.id.ordre_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.ordre_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //-----------creation du spinner-------------


        // On récupère le 'TableLayout' courant
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table_tuteurs);


        //on charge tous les tuteurs deja existant

        LayoutInflater inflater = getLayoutInflater();
        TableLayout table = (TableLayout) findViewById(R.id.table_tuteurs);

        // construction dynamique du tableau
        //parcours toutes les lignes du fichier Tuteurs.txt


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                String nom="";
                String prenom="";
                String tel="";
                String ordre="";

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

                            if(ordre.equals("1")){
                                Tel_tuteur_princ = tel;
                            }

                            //a la fin on creer une ligne du tableau et on l ajoute

                            TableRow tr = (TableRow) inflater.inflate(R.layout.tuteur_tablerow, null);

                            ((TextView) tr.findViewById(R.id.nom_tuteur)).setText(nom);
                            ((TextView) tr.findViewById(R.id.prenom_tuteur)).setText(prenom);
                            ((TextView) tr.findViewById(R.id.tel_tuteur)).setText(tel);
                            ((TextView) tr.findViewById(R.id.notifs_tuteur)).setText(ordre);
                            table.addView(tr);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // On récupère le bouton grâce à l'id
        Button button = (Button) findViewById(R.id.ajouter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    /**SAUVEGARDE DES DONNEES*/

                    EditText edit = (EditText)findViewById(R.id.edittutor_name);
                    String nom = edit.getText().toString();

                    EditText edit2 = (EditText)findViewById(R.id.edittutor_firstname);
                    String prenom = edit2.getText().toString();

                    EditText edit3 = (EditText)findViewById(R.id.edittutor_phone);
                    String tel = edit3.getText().toString();

                    String ordre = ((Spinner) findViewById(R.id.ordre_spinner)).getSelectedItem().toString();

                    filename="Tuteurs.txt";
                    filepath=LoginActivity.getMailUser();

                    filecontent = nom.trim()
                            +"  "+ prenom.trim()
                            +"  "+ tel.trim()
                            +"  "+ ordre.trim()
                            +"  ";

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
                        edit.setText("");
                        edit2.setText("");
                        edit3.setText("");

                        Toast.makeText(Tuteurs.this, "Infos sauvegardées",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Tuteurs.this, "Les champs sont vides",Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    /**Methode pour refresg la page et donc actualiser le tableau*/
    public void refreshTab(View view) {
        startActivity(new Intent(this, Tuteurs.class));
        //on ferme l'activite precedente pour pas avoir de multiple fenetre
        finish();
    }

    public static String getTel_tuteur_princ(){
        return Tel_tuteur_princ;
    }

}