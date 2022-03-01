package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class SaisieRdv extends AppCompatActivity {

    Button btnsave;
    EditText date, type_rdv;
    Spinner day, month;
    TextView load;
    String filename ="";
    String filepath="";
    String filecontent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_rdv);


        //-----------creation du spinner des jours-------------
        Spinner spinner = (Spinner) findViewById(R.id.day_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //-----------fin creation du spinner-------------



        //-----------creation du spinner des horaires-------------
        Spinner spinner2 = (Spinner) findViewById(R.id.month_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        //-----------fin creation du spinner-------------

        btnsave= findViewById(R.id.ajoutBtnRdv);
        date=(EditText) findViewById(R.id.editTextDateRdv);
        type_rdv=(EditText) findViewById(R.id.editTextTypeRdv);
        day=(Spinner) findViewById(R.id.day_spinner);
        month=(Spinner) findViewById(R.id.month_spinner);
        load=findViewById(R.id.loadrdv);

        filename="Rdv.txt";
        filepath=LoginActivity.getMailUser();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.setText("");
                filecontent = day.getSelectedItem().toString().trim()
                        +"!@!"+ date.getText().toString().trim()
                        +"!@!"+ month.getSelectedItem().toString().trim()
                        +"!@!"+ type_rdv.getText().toString().trim()
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
                    date.setText("");
                    type_rdv.setText("");

                    Toast.makeText(SaisieRdv.this, "Infos sauvegardées",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SaisieRdv.this, "Les champs sont vides",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onActualiserRdv(View view) {
        //Rdv new_rdv = new Rdv();
        //new_rdv.updateRdv(view);
        startActivity(new Intent(this, Rdv.class));
    }

}