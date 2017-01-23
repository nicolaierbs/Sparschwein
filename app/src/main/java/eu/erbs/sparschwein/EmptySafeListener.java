package eu.erbs.sparschwein;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;

/**
 * Created by erbs on 20/01/2017.
 */

public class EmptySafeListener implements View.OnClickListener {

    protected TextView amountTextView;
    protected Context context;

    protected MediaPlayer mediaPlayer;

    protected EmptySafeListener(Context context, TextView amountTextView){
        this.context = context;
        this.amountTextView = amountTextView;
        mediaPlayer = MediaPlayer.create(context, R.raw.empty);
    }

    @Override
    public void onClick(View v) {
        amountTextView.setText("0  €");

        mediaPlayer.start();
    }
}
