package fr.jeremy.doctoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.core.content.ContextCompat.startActivity;

public class Pilulier extends AppCompatActivity {

    private int notificationID =1;

    //on créer 5 variables boolean pour les check
    private boolean checked8h30 = false;
    private boolean checked12h00 = false;
    private boolean checked15h00 = false;
    private boolean checked16h30 = false;
    private boolean checked22h00 = false;

    public void setChecked8h30(boolean checked8h30) {this.checked8h30 = checked8h30;}
    public void setChecked12h00(boolean checked12h00) {this.checked12h00 = checked12h00;}
    public void setChecked15h00(boolean checked15h00) {this.checked15h00 = checked15h00;}
    public void setChecked16h30(boolean checked16h30) {this.checked16h30 = checked16h30;}
    public void setChecked22h00(boolean checked22h00) {this.checked22h00 = checked22h00;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilulier);


        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        // Nom du jour de la semaine actuel
        String jour = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());

        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jour);


        TextView first = findViewById(R.id.Block_8h30);
        TextView second = findViewById(R.id.Block_12h00);
        TextView third = findViewById(R.id.Block_15h00);
        TextView fourth = findViewById(R.id.Block_16h30);
        TextView fifth = findViewById(R.id.Block_22h00);

        String filename = "Traitements.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());

        //on va devoir lire toutes les infos du dossier Traitements.txt
        //et afficher celles du jour dans les bons txt
        //il faut lire ligne par ligne le .txt


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                String traitement1 = "";
                String traitement2 = "";
                String traitement3 = "";
                String traitement4 = "";
                String traitement5 = "";

                do {
                    try {
                        line = br.readLine();

                        if(line!=null) {
                            //envoi dans un tab de caracteres les mots separe par un double espace

                            String[] tab = line.split("  ");

                            //on ne regarde que les traitements du jour
                            //tab[0] c'est le jour


                            if ((tab[0].toLowerCase()).equals(jour)) {

                                if (tab[1].equals("8h30")) {
                                    String traitement = tab[2] + " " + tab[3];
                                    if(traitement1.equals("")){
                                        traitement1 = "- " + traitement;
                                    }
                                    else{
                                        traitement1 = traitement1 +"\n- "+traitement;
                                    }

                                }
                                if (tab[1].equals("12h00")) {
                                    String traitement = tab[2] + " " + tab[3];
                                    if(traitement2.equals("")){
                                        traitement2 = "- " + traitement;
                                    }
                                    else{
                                        traitement2 = traitement2 +"\n- "+traitement;
                                    }
                                }
                                if (tab[1].equals("15h00")) {
                                    String traitement = tab[2] + " " + tab[3];
                                    if(traitement3.equals("")){
                                        traitement3 = "- " + traitement;
                                    }
                                    else{
                                        traitement3 = traitement3 +"\n- "+traitement;
                                    }
                                }
                                if (tab[1].equals("16h30")) {
                                    String traitement = tab[2] + " " + tab[3];
                                    if(traitement4.equals("")){
                                        traitement4 = "- " + traitement;
                                    }
                                    else{
                                        traitement4 = traitement4 +"\n- "+traitement;
                                    }
                                }
                                if (tab[1].equals("22h00")) {
                                    String traitement = tab[2] + " " + tab[3];
                                    if(traitement5.equals("")){
                                        traitement5 = "- " + traitement;
                                    }
                                    else{
                                        traitement5 = traitement5 +"\n- "+traitement;
                                    }
                                }
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
                //a la fin on set tout

                first.setText(traitement1);
                first.setTextSize(getResources().getDimension(R.dimen.text_pilulier_size));
                second.setText(traitement2);
                second.setTextSize(getResources().getDimension(R.dimen.text_pilulier_size));
                third.setText(traitement3);
                third.setTextSize(getResources().getDimension(R.dimen.text_pilulier_size));
                fourth.setText(traitement4);
                fourth.setTextSize(getResources().getDimension(R.dimen.text_pilulier_size));
                fifth.setText(traitement5);
                fifth.setTextSize(getResources().getDimension(R.dimen.text_pilulier_size));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        Calendar maintenant = Calendar.getInstance();
        int heureactuelle = maintenant.get(Calendar.HOUR_OF_DAY);
        int minuteactuelle = maintenant.get(Calendar.MINUTE);

        //gestion alarme ou notif
        //on parcours chaque block pour les notifs si le texte est pas vide on set une notif

        //si 8h30 non vide set notif
        if(!first.getText().toString().equals("")){
            if(heureactuelle==8 && minuteactuelle==30){
                this.createDynamicNotif(first.getText().toString(),8,30);
            }

        }
        //si 12h00 non vide set notif
        if(!second.getText().toString().equals("")){
            if(heureactuelle==12 && minuteactuelle==00) {
                this.createDynamicNotif(second.getText().toString(), 12, 00);
            }
        }
        //si 15h00 non vide set notif
        if(!third.getText().toString().equals("")){
            if(heureactuelle==15 && minuteactuelle==00) {
                this.createDynamicNotif(third.getText().toString(), 15, 00);
            }
        }
        //si 16h30 non vide set notif
        if(!fourth.getText().toString().equals("")){
            if(heureactuelle==16 && minuteactuelle==30) {
                this.createDynamicNotif(fourth.getText().toString(), 16, 30);
            }
        }
        //si 22h00 non vide set notif
        if(!fifth.getText().toString().equals("")){
            if(heureactuelle==22 && minuteactuelle==00) {
                this.createDynamicNotif(fifth.getText().toString(), 22, 00);
            }
        }


        /**Gestion du sms au proche 1 h plus tard si traitement pas validé**/


        //il faut trouver le tuteur principal normalement celui d'ordre 1
        String phone_number_tuteur = "0761664091";
        //String phone_number_tuteur = Tuteurs.getTel_tuteur_princ();
        String heure = "";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                //si on a la permission on pourra envoyer des sms

                //on doit regarder tous les creneaux

                //on regarde l'heure actuelle du tel
                Calendar rightNow = Calendar.getInstance();
                int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY); // return the hour in 24 hrs format (ranging from 0-23)
                int currentMinuteIn59Format = rightNow.get(Calendar.MINUTE); // return the minute in 59 mins format (ranging from 0-59)

                //on recup l etat des radios buttons
                RadioButton un = findViewById(R.id.RadioButton8h30);
                RadioButton deux = findViewById(R.id.RadioButton12h00);
                RadioButton trois = findViewById(R.id.RadioButton15h00);
                RadioButton quatre = findViewById(R.id.RadioButton16h30);
                RadioButton cinq = findViewById(R.id.RadioButton22h00);


                //on enregistre dans un fichier l etat de chaque bouton quand on clique dessus
                //on lit le fichier si le jour du fichier correspond c bon sinon il faut tout remettre à false

                // Nom du jour de la semaine actuel
                Calendar calendarsms = Calendar.getInstance();
                Date datesms = calendarsms.getTime();
                String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(datesms.getTime());
                ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

                String filenamesms = "Sms.txt";
                String filepathsms = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());
                FileReader fr8 = null;
                File mon_fichier8 = new File(filepathsms, filenamesms);
                if(mon_fichier8.exists()) {
                    try {
                        fr8 = new FileReader(mon_fichier8);
                        BufferedReader br = new BufferedReader(fr8);
                        String line = null;
                        do {
                            try {
                                line = br.readLine();
                                if(line!=null) {
                                    //envoi dans un tab de caracteres les mots separe par un double espace

                                    String[] tab = line.split("!@!");

                                    //tab[0] c'est le jour
                                    //tab[1] c'est le boolean de 8h30
                                    //tab[2] c'est le boolean de 12h00
                                    //tab[3] c'est le boolean de 15h00
                                    //tab[4] c'est le boolean de 16h30
                                    //tab[5] c'est le boolean de 22h00
                                    if(!tab[0].equals(jouractuel)){
                                        //si on a changé de jour
                                        //alors il faut tout reset le fichier
                                        mon_fichier8.delete();
                                        mon_fichier8.createNewFile();
                                        FileWriter fw = new FileWriter(mon_fichier8, true);
                                        BufferedWriter buffer = new BufferedWriter(fw);
                                        PrintWriter pw = new PrintWriter(buffer);
                                        pw.println(jouractuel.trim()+"!@!false!@!false!@!false!@!false!@!false!@!");
                                    }

                                    this.setChecked8h30(Boolean.parseBoolean(tab[1]));
                                    this.setChecked12h00(Boolean.parseBoolean(tab[2]));
                                    this.setChecked15h00(Boolean.parseBoolean(tab[3]));
                                    this.setChecked16h30(Boolean.parseBoolean(tab[4]));
                                    this.setChecked22h00(Boolean.parseBoolean(tab[5]));

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } while (line != null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    //on creer
                    try {
                        FileWriter fw = new FileWriter(mon_fichier8, true);
                        BufferedWriter buffer = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(buffer);
                        mon_fichier8.createNewFile();
                        pw.println(jouractuel.trim()+"!@!false!@!false!@!false!@!false!@!false!@!");
                        pw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //pour le debug
                //Toast.makeText(this, checked8h30+" "+checked12h00+" "+checked15h00+" "+checked16h30+" "+checked22h00, Toast.LENGTH_SHORT).show();

                /**Envoi des sms si necessaire*/
                //si 8h30 non vide set notif
                if(!first.getText().toString().equals("")){
                    //si on depasse 9h30 il faut envoyer un sms
                    if((currentHourIn24Format==10 && currentMinuteIn59Format>30)||(currentHourIn24Format>10)){
                        if(!checked8h30){
                            heure = "8h30";
                            sendSMSTuteur(phone_number_tuteur,heure);
                        }
                    }
                }
                //si 12h00 non vide set notif
                if(!second.getText().toString().equals("")){
                    //si on depasse 13h00 il faut envoyer un sms
                    if((currentHourIn24Format==13 && currentMinuteIn59Format>0)||(currentHourIn24Format>13)){
                        if(!checked12h00){
                            heure = "12h00";
                            sendSMSTuteur(phone_number_tuteur,heure);
                        }
                    }
                }
                //si 15h00 non vide set notif
                if(!third.getText().toString().equals("")){
                    //si on depasse 16h00 il faut envoyer un sms
                    if((currentHourIn24Format==16 && currentMinuteIn59Format>0)||(currentHourIn24Format>16)){
                        if(!checked15h00){
                            heure = "15h00";
                            sendSMSTuteur(phone_number_tuteur,heure);
                        }
                    }
                }
                //si 16h30 non vide set notif
                if(!fourth.getText().toString().equals("")){
                    //si on depasse 17h30 il faut envoyer un sms
                    if((currentHourIn24Format==17 && currentMinuteIn59Format>30)||(currentHourIn24Format>17)){
                        if(!checked16h30){
                            heure = "16h30";
                            sendSMSTuteur(phone_number_tuteur,heure);
                        }
                    }
                }
                //si 22h00 non vide set notif
                if(!fifth.getText().toString().equals("")){
                    //si on depasse 23h00 il faut envoyer un sms
                    if((currentHourIn24Format==23 && currentMinuteIn59Format>0)){
                        if(!checked22h00){
                            heure = "22h00";
                            sendSMSTuteur(phone_number_tuteur,heure);
                        }
                    }
                }
            }
            else{
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
            }
        }
    }

    //fonction d'envoi d'un sms au tuteur
    private void sendSMSTuteur(String phoneNo, String hour) {
        String SMS = "Votre pupille n'a pas pris son traitement de " + hour;

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, SMS, null, null);
            Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
        }
    }


    /**Methode redirigeant sur une autre page pour saisir de nouveaux traitement*/
    public void addTraitement(View view) {
        startActivity(new Intent(this, SaisieTraitement.class));
        finish();
    }


    public void createDynamicNotif(String message, int hour, int minute){

        //set notifID and message
        Intent intent = new Intent(Pilulier.this, AlarmReceiver.class);
        intent.putExtra("notificationID", notificationID);
        intent.putExtra("message",  message );

        //Pending intent
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                Pilulier.this, 0, intent,PendingIntent.FLAG_CANCEL_CURRENT
        );
        //cancel s'il existe deja un pending intent

        //Alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //set alarm with hour and minute in params
        //create time
        Calendar startTime =  Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, minute);
        startTime.set(Calendar.SECOND, 0);
        long alarmStartTime = startTime.getTimeInMillis();

        //set Alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);

        //Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();

    }

    public void validate8h30(View view) {
        /**on doit mettre à jour les infos*/
        //on commence par lire le fichier
        String boolean8h30 = null;
        String boolean12h00 = null;
        String boolean15h00 = null;
        String boolean16h30 = null;
        String boolean22h00 = null;

        // Nom du jour de la semaine actuel
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());
        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

        String filename = "Sms.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                do {
                    try {
                        line = br.readLine();
                        if (line != null) {
                            //envoi dans un tab de caracteres les mots separe par !@!
                            String[] tab = line.split("!@!");
                            //tab[0] c'est le jour
                            boolean8h30 = tab[1];//tab[1] c'est le boolean de 8h30
                            boolean12h00 = tab[2];//tab[2] c'est le boolean de 12h00
                            boolean15h00 = tab[3];//tab[3] c'est le boolean de 15h00
                            boolean16h30 = tab[4];//tab[4] c'est le boolean de 16h30
                            boolean22h00 = tab[5];//tab[5] c'est le boolean de 22h00
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**Maintenant on ecrit*/
            String filecontent = jouractuel.trim()
                    + "!@!" + "true".trim()
                    + "!@!" + boolean12h00.trim()
                    + "!@!" + boolean15h00.trim()
                    + "!@!" + boolean16h30.trim()
                    + "!@!" + boolean22h00.trim()
                    + "!@!";

            //debug
            //Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();

            //si ce n est pas vide
            if (!filecontent.equals("")) {
                try {
                    mon_fichier.delete();
                    // on delete puis recreer pcq on souhaite avoir une seule ligne
                    mon_fichier.createNewFile();
                    FileWriter fw = new FileWriter(mon_fichier, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void validate12h00(View view) {
        /**on doit mettre à jour les infos*/
        //on commence par lire le fichier
        String boolean8h30 = null;
        String boolean12h00 = null;
        String boolean15h00 = null;
        String boolean16h30 = null;
        String boolean22h00 = null;

        // Nom du jour de la semaine actuel
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());
        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

        String filename = "Sms.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                do {
                    try {
                        line = br.readLine();
                        if (line != null) {
                            //envoi dans un tab de caracteres les mots separe par !@!
                            String[] tab = line.split("!@!");
                            //tab[0] c'est le jour
                            boolean8h30 = tab[1];//tab[1] c'est le boolean de 8h30
                            boolean12h00 = tab[2];//tab[2] c'est le boolean de 12h00
                            boolean15h00 = tab[3];//tab[3] c'est le boolean de 15h00
                            boolean16h30 = tab[4];//tab[4] c'est le boolean de 16h30
                            boolean22h00 = tab[5];//tab[5] c'est le boolean de 22h00
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**Maintenant on ecrit*/
            String filecontent = jouractuel.trim()
                    + "!@!" + boolean8h30.trim()
                    + "!@!" + "true".trim()
                    + "!@!" + boolean15h00.trim()
                    + "!@!" + boolean16h30.trim()
                    + "!@!" + boolean22h00.trim()
                    + "!@!";

            //debug
            //Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();

            //si ce n est pas vide
            if (!filecontent.equals("")) {
                try {
                    mon_fichier.delete();
                    // on delete puis recreer pcq on souhaite avoir une seule ligne
                    mon_fichier.createNewFile();
                    FileWriter fw = new FileWriter(mon_fichier, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void validate15h00(View view) {
        /**on doit mettre à jour les infos*/
        //on commence par lire le fichier
        String boolean8h30 = null;
        String boolean12h00 = null;
        String boolean15h00 = null;
        String boolean16h30 = null;
        String boolean22h00 = null;

        // Nom du jour de la semaine actuel
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());
        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

        String filename = "Sms.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                do {
                    try {
                        line = br.readLine();
                        if (line != null) {
                            //envoi dans un tab de caracteres les mots separe par !@!
                            String[] tab = line.split("!@!");
                            //tab[0] c'est le jour
                            boolean8h30 = tab[1];//tab[1] c'est le boolean de 8h30
                            boolean12h00 = tab[2];//tab[2] c'est le boolean de 12h00
                            boolean15h00 = tab[3];//tab[3] c'est le boolean de 15h00
                            boolean16h30 = tab[4];//tab[4] c'est le boolean de 16h30
                            boolean22h00 = tab[5];//tab[5] c'est le boolean de 22h00
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**Maintenant on ecrit*/
            String filecontent = jouractuel.trim()
                    + "!@!" + boolean8h30.trim()
                    + "!@!" + boolean12h00.trim()
                    + "!@!" + "true".trim()
                    + "!@!" + boolean16h30.trim()
                    + "!@!" + boolean22h00.trim()
                    + "!@!";

            //debug
            //Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();

            //si ce n est pas vide
            if (!filecontent.equals("")) {
                try {
                    mon_fichier.delete();
                    // on delete puis recreer pcq on souhaite avoir une seule ligne
                    mon_fichier.createNewFile();
                    FileWriter fw = new FileWriter(mon_fichier, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void validate16h30(View view) {
        /**on doit mettre à jour les infos*/
        //on commence par lire le fichier
        String boolean8h30 = null;
        String boolean12h00 = null;
        String boolean15h00 = null;
        String boolean16h30 = null;
        String boolean22h00 = null;

        // Nom du jour de la semaine actuel
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());
        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

        String filename = "Sms.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                do {
                    try {
                        line = br.readLine();
                        if (line != null) {
                            //envoi dans un tab de caracteres les mots separe par !@!
                            String[] tab = line.split("!@!");
                            //tab[0] c'est le jour
                            boolean8h30 = tab[1];//tab[1] c'est le boolean de 8h30
                            boolean12h00 = tab[2];//tab[2] c'est le boolean de 12h00
                            boolean15h00 = tab[3];//tab[3] c'est le boolean de 15h00
                            boolean16h30 = tab[4];//tab[4] c'est le boolean de 16h30
                            boolean22h00 = tab[5];//tab[5] c'est le boolean de 22h00
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**Maintenant on ecrit*/
            String filecontent = jouractuel.trim()
                    + "!@!" + boolean8h30.trim()
                    + "!@!" + boolean12h00.trim()
                    + "!@!" + boolean15h00.trim()
                    + "!@!" + "true".trim()
                    + "!@!" + boolean22h00.trim()
                    + "!@!";

            //debug
            //Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();

            //si ce n est pas vide
            if (!filecontent.equals("")) {
                try {
                    mon_fichier.delete();
                    // on delete puis recreer pcq on souhaite avoir une seule ligne
                    mon_fichier.createNewFile();
                    FileWriter fw = new FileWriter(mon_fichier, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void validate22h00(View view) {
        /**on doit mettre à jour les infos*/
        //on commence par lire le fichier
        String boolean8h30 = null;
        String boolean12h00 = null;
        String boolean15h00 = null;
        String boolean16h30 = null;
        String boolean22h00 = null;

        // Nom du jour de la semaine actuel
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String jouractuel = new SimpleDateFormat("EEEE", Locale.FRANCE).format(date.getTime());
        ((TextView) findViewById(R.id.jour_semaine_pilulier)).setText(jouractuel);

        String filename = "Sms.txt";
        String filepath = Environment.getExternalStorageDirectory() + "/Doctoapp/" + (LoginActivity.getMailUser());


        FileReader fr = null;
        File mon_fichier = new File(filepath, filename);
        if(mon_fichier.exists()) {
            try {
                fr = new FileReader(mon_fichier);
                BufferedReader br = new BufferedReader(fr);

                String line = null;
                do {
                    try {
                        line = br.readLine();
                        if (line != null) {
                            //envoi dans un tab de caracteres les mots separe par !@!
                            String[] tab = line.split("!@!");
                            //tab[0] c'est le jour
                            boolean8h30 = tab[1];//tab[1] c'est le boolean de 8h30
                            boolean12h00 = tab[2];//tab[2] c'est le boolean de 12h00
                            boolean15h00 = tab[3];//tab[3] c'est le boolean de 15h00
                            boolean16h30 = tab[4];//tab[4] c'est le boolean de 16h30
                            boolean22h00 = tab[5];//tab[5] c'est le boolean de 22h00
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } while (line != null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            /**Maintenant on ecrit*/
            String filecontent = jouractuel.trim()
                    + "!@!" + boolean8h30.trim()
                    + "!@!" + boolean12h00.trim()
                    + "!@!" + boolean15h00.trim()
                    + "!@!" + boolean16h30.trim()
                    + "!@!" + "true".trim()
                    + "!@!";

            //debug
            //Toast.makeText(this, filecontent, Toast.LENGTH_SHORT).show();

            //si ce n est pas vide
            if (!filecontent.equals("")) {
                try {
                    mon_fichier.delete();
                    // on delete puis recreer pcq on souhaite avoir une seule ligne
                    mon_fichier.createNewFile();
                    FileWriter fw = new FileWriter(mon_fichier, true);
                    BufferedWriter buffer = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(buffer);
                    pw.println(filecontent);
                    pw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}