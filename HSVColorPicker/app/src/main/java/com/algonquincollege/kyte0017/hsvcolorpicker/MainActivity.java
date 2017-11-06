package com.algonquincollege.kyte0017.hsvcolorpicker;

/**
 * This allows a user to see a color based on user input Hue, Saturation and Brightness values.
 *
 * @author Hayden Kyte
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import model.HSVModel;
/**
 * The Controller for HSVModel.
 *
 * As the Controller:
 *   a) event handler for the View
 *   b) observer of the Model (HSVModel)
 *
 * Features the Update / React Strategy.
 *
 * @author kyte0017@algonquinlive.com
 * @version 1.0
 */

public class MainActivity extends Activity implements Observer, SeekBar.OnSeekBarChangeListener {

    // INSTANCE VARIABLES
    private TextView mColorSwatch;
    private HSVModel mModel;
    private SeekBar mHueSB;
    private SeekBar mSaturationSB;
    private SeekBar mBrightnessSB;
    private TextView mHueTV;
    private TextView mSaturationTV;
    private TextView mBrightnessTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a new HSV model

        mModel = new HSVModel();
        mModel.asBlack();

        // The Model is observing this Controller
        mModel.addObserver(this);

        // reference each View
        mColorSwatch = (TextView) findViewById(R.id.color_swatch);
        mHueSB = (SeekBar) findViewById(R.id.hueSB);
        mSaturationSB = (SeekBar) findViewById(R.id.saturationSB);
        mBrightnessSB = (SeekBar) findViewById(R.id.brightnessSB);

        //The title of text of the seekbars
        mHueTV = (TextView) findViewById(R.id.hue);
        mSaturationTV = (TextView) findViewById(R.id.saturation);
        mBrightnessTV = (TextView) findViewById(R.id.brightness);


        // SETTING the max for each seekbar
        mHueSB.setMax(HSVModel.MAX_Hue);
        mSaturationSB.setMax(HSVModel.MAX_HSV);
        mBrightnessSB.setMax(HSVModel.MAX_HSV);


        // register the event handler for each <SeekBar>
        mHueSB.setOnSeekBarChangeListener(this);
        mSaturationSB.setOnSeekBarChangeListener(this);
        mBrightnessSB.setOnSeekBarChangeListener(this);


        //Toast message of HSV values of the current view
        mColorSwatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                Float currentSaturation = mModel.getSaturation();
                Float currentBrightness = mModel.getBrightness();
                Toast.makeText(
                        view.getContext(),

                        "H: " + mModel.getHue() + "\u00B0" + "  S: " + currentSaturation * 100 + "%" + "  V: " + currentBrightness * 100 + "%",
                        Toast.LENGTH_SHORT
                ).show();

                return true;
            }
        });

        // changing the view with the updated model values
        this.updateView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if (res_id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Author:");
            builder.setMessage("Hayden Kyte (kyte0017)");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Event handler each HSV seekbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        // Did the user cause this event?
        // YES > continue
        // NO  > leave this method
        if (!fromUser) {
            return;
        }

        // Determine which <SeekBark> caused the event (switch + case)
        // GET the SeekBar's progress, and SET the model to it's new value
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mModel.setHue(mHueSB.getProgress());
                String hueString = getResources().getString(R.string.hueProgress, progress).toUpperCase() + "\u00B0";
                mHueTV.setText(hueString);
                break;

            case R.id.saturationSB:
                float saturationFloat = mSaturationSB.getProgress();
                saturationFloat = saturationFloat / 100;
                mModel.setSaturation(saturationFloat);
                String saturationString = getResources().getString(R.string.saturationProgress, progress).toUpperCase() + "%";
                mSaturationTV.setText(saturationString);
                break;

            case R.id.brightnessSB:
                float brightnessFloat = mBrightnessSB.getProgress();
                brightnessFloat = brightnessFloat / 100;
                mModel.setBrightness(brightnessFloat);
                String brightnessString = getResources().getString(R.string.brightnessProgress, progress).toUpperCase() + "%";
                mBrightnessTV.setText(brightnessString);
                break;

        }
    }



    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // No-Operation
    }

    //RESETTING the titles of the seekbar once the user has finished scrolling
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.hueSB:
                mHueTV.setText(getResources().getString(R.string.hue));
                break;
            case R.id.saturationSB:
                mSaturationTV.setText(getResources().getString(R.string.saturation));
                break;

            case R.id.brightnessSB:
                mBrightnessTV.setText(getResources().getString(R.string.brightness));
                break;

        }

    }

    // The Model has changed state!
    // Refresh the View to display the current values of the Model.
    @Override
    public void update(Observable observable, Object data) {
        this.updateView();
    }

    private void updateHueSB() {
        //GET the model's Hue value, and SET the <SeekBar>
        mHueSB.setProgress(mModel.getHue());
    }

    private void updateSaturationSB() {
        //GET the model's saturation value, and SET the <SeekBar>
        float saturation = mModel.getSaturation();
        saturation = saturation * 100;
        mSaturationSB.setProgress((int) saturation);
    }

    private void updateBrightnessSB() {
        //GET the model's Brightness value, and SET the  <SeekBar>
        float brightness = mModel.getBrightness();
        brightness = brightness * 100;
        mBrightnessSB.setProgress((int) brightness);
    }

    // HSVToColor requires float values.

    private void updateColorSwatch() {
        //GET the model's HSV values, and SET the background colour of the swatch <TextView>
        float[] HSV2COLOR = {mModel.getHue(), mModel.getSaturation(), mModel.getBrightness()};

        mColorSwatch.setBackgroundColor(Color.HSVToColor(HSV2COLOR));
    }

    // synchronize each View component with the Model
    public void updateView() {
        this.updateColorSwatch();
        this.updateHueSB();
        this.updateSaturationSB();
        this.updateBrightnessSB();
    }

    public void colorChange(View view) {

        switch (view.getId()) {
            case R.id.button_black:
                mModel.asBlack();
                break;

            case R.id.button_red:
                mModel.asRed();
                break;

            case R.id.button_green:
                mModel.asGreen();
                break;

            case R.id.button_blue:
                mModel.asBlue();
                break;

            case R.id.button_yellow:
                mModel.asYellow();
                break;

            case R.id.button_cyan:
                mModel.asCyan();
                break;

            case R.id.button_magenta:
                mModel.asMagenta();
                break;

        }

        Float currentSaturation = mModel.getSaturation();
        Float currentBrightness = mModel.getBrightness();
        Toast.makeText(
                view.getContext(),
                "H: " + mModel.getHue() + "\u00B0" + "  S: " + currentSaturation * 100 + "%" + "  V: " + currentBrightness * 100 + "%",
                Toast.LENGTH_SHORT
        ).show();

    }
} // end of class