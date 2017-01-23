package eu.erbs.sparschwein;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by erbs on 20/01/2017.
 */
public class InsertCoinListener extends EmptySafeListener {

    private float value;


    public InsertCoinListener(Context context, TextView amountTextView, float value){
        super(context,amountTextView);
        this.value = value;
        mediaPlayer = MediaPlayer.create(context, R.raw.singlecoin);
    }

    @Override
    public void onClick(View v) {
        float oldValue = Float.valueOf(amountTextView.getText().toString().replaceAll(" €", ""));
        float newValue = Math.round((value + oldValue)*100)/100f;
        amountTextView.setText(String.valueOf(newValue) + "  €");

        mediaPlayer.start();
    }


}
