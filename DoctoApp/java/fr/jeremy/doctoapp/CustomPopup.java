package fr.jeremy.doctoapp;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;

public class CustomPopup extends Dialog {

    private ImageView img;
    private Button close;

    public CustomPopup(Activity activity) {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.layout_facture);

        this.img = findViewById(R.id.facture1);
        this.close = findViewById(R.id.close_button);

    }

    public Button getClose() { return close; }

    public ImageView getImg() { return img; }

    public void build() {
        show();

    }
}
