package fr.jeremy.doctoapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class Icons_menuAdapter extends BaseAdapter{
    //attributs
    private Context context;
    private List<Icons> listedesicones;
    private LayoutInflater inflater;

    //constructeur
    public Icons_menuAdapter(Context context, List<Icons> listedesicones){
        this.context = context;
        this.listedesicones = listedesicones;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listedesicones.size();
    }

    @Override
    public Icons getItem(int position) {
        return listedesicones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_icons_menu, null);

        //get info de l'icone
        Icons currentIcons = getItem(position);
        String name_icon = currentIcons.getName_icon();
        String mnemonic = currentIcons.getMnemonic();


        //get icon view
        ImageView itemIconView = convertView.findViewById(R.id.icon);
        String ressourceName = "item_" + mnemonic;
        int resId = context.getResources().getIdentifier(ressourceName, "drawable", context.getPackageName());
        itemIconView.setImageResource(resId);

        //get icon name
        TextView iconNameView = convertView.findViewById(R.id.name_icon);
        iconNameView.setText(name_icon);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**On differentes activity en fonction de la position de l'icone*/
                if(position==0){
                    Intent intent = new Intent().setClass(context, Rdv.class);
                    startActivity(context,intent,null);
                }
                if(position==1){
                    Intent intent = new Intent().setClass(context, Ordonnances.class);
                    startActivity(context,intent,null);
                }
                if(position==2){
                    Intent intent = new Intent().setClass(context, Pilulier.class);
                    startActivity(context,intent,null);
                }
                if(position==3){

                    Intent intent = new Intent().setClass(context, Appel_urgence.class);
                    startActivity(context,intent,null);
                }

                if(position==4){

                    Intent intent = new Intent().setClass(context, Tuteurs.class);
                    startActivity(context,intent,null);
                }
                if(position==5){

                    Intent intent = new Intent().setClass(context, Gestes.class);
                    startActivity(context,intent,null);
                }

                if(position==6){

                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                    /*
                    Intent intent = new Intent().setClass(context, Mes_notices.class);
                    startActivity(context,intent,null);*/
                }

                if(position==7){

                    Intent intent = new Intent().setClass(context, Medecin.class);
                    startActivity(context,intent,null);
                }

                if(position==8){

                    Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show();
                    /*
                    Intent intent = new Intent().setClass(context, Contacts.class);
                    startActivity(context,intent,null);*/
                }

                if(position==9){

                    Intent intent = new Intent().setClass(context, Profil.class);
                    startActivity(context,intent,null);
                }
                if(position==10){

                    Intent intent = new Intent().setClass(context, Aides.class);
                    startActivity(context,intent,null);
                }

                if(position==11){

                    Intent intent = new Intent().setClass(context, Parametres.class);
                    startActivity(context,intent,null);
                }
            }
        });

        return convertView;
    }
}
