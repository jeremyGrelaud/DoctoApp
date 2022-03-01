package fr.jeremy.doctoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class Ordonnances extends AppCompatActivity {
    private Ordonnances activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordonnances);

        this.activity = this;
        ImageButton button1 = (ImageButton)findViewById(R.id.ordonnance_button_img1);
        ImageButton button2 = (ImageButton)findViewById(R.id.ordonnance_button_img2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                MyCustomAlertDialog();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCustomAlertDialog();
            }
        });
    }

    public void MyCustomAlertDialog() {

        CustomPopup customPopup = new CustomPopup(activity);
        customPopup.getClose().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopup.dismiss();
            }
        });
        customPopup.build();

        /*
        //-------VERSION FONCTIONNEL-----------
        final Dialog popup_img = new Dialog(Ordonnances.this);
        View facture = (View)findViewById(R.id.ordonnance_img1);


        popup_img.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popup_img.setContentView(R.layout.activity_ordonnances);
        popup_img.show();
        //---------------------------------------
        */

        /*
        ImageButton button = (ImageButton)popup_img.findViewById(R.id.ordonnance_button_img1);
        button.setEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Hello, voici ma popup", Toast.LENGTH_LONG).show();
            }
        });

        //Button close = (Button)popup_img.findViewById(R.)


        @Override
        public void onClick(View v) {
            popup_img.cancel();
        }
        */


    }

}
