package fr.jeremy.doctoapp;

public class Icons {

    //attributs
    private String name_icon;
    private String mnemonic;

    //constructeur
    public Icons(String name, String mnemonic){
        this.name_icon = name;
        this.mnemonic = mnemonic;
    }

    //methodes
    public String getName_icon(){return  name_icon; }

    public String getMnemonic(){return  mnemonic; }

}
