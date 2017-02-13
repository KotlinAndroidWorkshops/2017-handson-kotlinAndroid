package fr.ekito.myweatherapp;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;


public class DialogHelper {

    public static final String HINT = "i.e: Paris, France";

    public static void locationDialog(final View view, final MainActivityWeatherCallback callback) {

        final EditText input = new EditText((Activity) callback);
        input.setHint(HINT);

        final Builder builder = new Builder(view.getContext());

        builder.setMessage(R.string.location_title)
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        callback.getWeatherData(view, input.getText().toString().trim().replace(" ", ""));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                                dialog.dismiss();
                            }
                        }
                );

        // Create the AlertDialog object and return it
        final AlertDialog dialog = builder.create();
        final LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialog.setView(input);
        dialog.show();
    }
}
