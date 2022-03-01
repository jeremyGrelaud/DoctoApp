package fr.jeremy.doctoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Test_DataBase extends AppCompatActivity {

    private TextView InfosView;
    private  DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__data_base);

        InfosView = (TextView) findViewById(R.id.InfosView);
        databaseManager = new DatabaseManager(this);

        databaseManager.insertInfos("Pieck", 0700000001,"Pieck@gmail.com","123");
        databaseManager.insertInfos("Jean", 0700000002,"Jean@gmail.com","666");
        databaseManager.insertInfos("Connie", 0700000003,"Connie@gmail.com","0");
        databaseManager.insertInfos("Gaby", 0766666666,"Gaby@gmail.com","EldianShouldDie");

        List<PersonData> infos = databaseManager.readTop10();
        for(PersonData info : infos){
            InfosView.append(info.toString()+"\n\n");
        }
        databaseManager.close();
    }
}