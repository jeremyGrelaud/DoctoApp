package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class SaisieTraitement extends AppCompatActivity {

    Button btnsave;
    EditText name_medic, dose;
    Spinner day, hour;
    TextView load;
    String filename ="";
    String filepath="";
    String filecontent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_traitement);


        //-----------creation du spinner des jours-------------
        Spinner spinner = (Spinner) findViewById(R.id.day_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //-----------creation du spinner-------------



        //-----------creation du spinner des horaires-------------
        Spinner spinner2 = (Spinner) findViewById(R.id.hour_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.hours_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);
        //-----------creation du spinner-------------

        btnsave= findViewById(R.id.ajoutbtn);
        name_medic=findViewById(R.id.editTextNomTraitement);
        dose=findViewById(R.id.editTextDose);
        day=findViewById(R.id.day_spinner);
        hour=findViewById(R.id.hour_spinner);
        load=findViewById(R.id.loadtest);

        filename="Traitements.txt";
        filepath=LoginActivity.getMailUser();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.setText("");
                filecontent = day.getSelectedItem().toString().trim()
                +"  "+ hour.getSelectedItem().toString().trim()
                +"  "+ name_medic.getText().toString().trim()
                +"  "+ dose.getText().toString().trim()
                +"  ";

                //si ce n est pas vide
                if(!filecontent.equals("")){
                    File file = new File(Environment.getExternalStorageDirectory()+"/Doctoapp/"+filepath, filename);
                    /*
                    if(!file.exists()){
                        FileOutputStream fos = null;
                        try {
                            fos=new FileOutputStream(file);
                            fos.write(filecontent.getBytes());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }*/

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

                        /*
                        PrintWriter out = null;
                        BufferedWriter bufWriter;
                        try{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bufWriter =
                                        Files.newBufferedWriter(
                                                Paths.get("log.txt"),
                                                Charset.forName("UTF8"),
                                                StandardOpenOption.WRITE,
                                                StandardOpenOption.APPEND,
                                                StandardOpenOption.CREATE);
                                out = new PrintWriter(bufWriter, true);
                            }
                        }catch(IOException e){
                            //Oh, no! Failed to create PrintWriter
                        }
                        //After successful creation of PrintWriter
                        out.println(filecontent);
                        //After done writing, remember to close!
                        out.close();*/

                    //on clear les edit text
                    name_medic.setText("");
                    dose.setText("");

                    Toast.makeText(SaisieTraitement.this, "Infos sauvegardées",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SaisieTraitement.this, "Les champs sont vides",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onAddTraitementClick(View view) {
        Toast.makeText(this, "Salut", Toast.LENGTH_SHORT).show();
    }

    public void onActualiserPilulier(View view) {

        startActivity(new Intent(this, Pilulier.class));
    }

    public void onModifTraitements(View view) {
        startActivity(new Intent(this, ModifTraitement.class));
    }
}