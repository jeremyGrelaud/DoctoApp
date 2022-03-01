package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ModifTraitement extends AppCompatActivity {

    Button btnLoad;
    String filename ="";
    String filepath="";
    TextView load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_traitement);


        btnLoad =findViewById(R.id.affbtn);
        filename="Traitements.txt";
        filepath=LoginActivity.getMailUser();
        load=findViewById(R.id.aff_traitement);


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.setText("");
                FileReader fr = null;
                File mon_fichier = new File(Environment.getExternalStorageDirectory()+"/Doctoapp/"+filepath, filename);
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    fr = new FileReader(mon_fichier);
                    BufferedReader br = new BufferedReader(fr);
                    String line = br.readLine();
                    while(line !=null){
                        stringBuilder.append(line).append('\n');
                        line = br.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    String filecontent = "Liste des traitements :\n" + stringBuilder.toString();
                    load.setText(filecontent);
                }
            }
        });

    }
}