package model;

/**
 * The model holds the data.
 *
 * Model color.
 *
 * Based on Hue, Saturation and Brightness
 *
 * Hue: integer values in the domain range of 0 to 360.
 * Saturation and Brightness: Float values in the doman of 0 to 100%
 *
 * @author: Hayden Kyte (kyte0017)
 * @version: 1.0
 */


import java.util.Observable;


public class HSVModel extends Observable {

    // CLASS VARIABLES
    public static final Integer MAX_Hue = 360;
    public static final Integer MIN_Hue = 0;
    public static final int MAX_HSV = 100;
    public static final float MAX_SATURATION = 1;
    public static final float MAX_BRIGHTNESS = 1;
    public static final float MIN_HSV = 0;

    // INSTANCE VARIABLES
    private Integer hue;
    private float saturation;
    private float brightness;

    // No argument constructor.

    public HSVModel() {
        this(MAX_Hue, MAX_HSV, MAX_HSV);
    }

    // Convenience constructor
    public HSVModel(Integer hue, float saturation, float brightness) {
        super();

        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
    }


    // Setting the HSV values for all my color buttons

    public void asBlack() {
        this.setHSV(MIN_Hue, MIN_HSV, MIN_HSV);
    }

    public void asRed() {
        this.setHSV(MIN_Hue, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asGreen() {
        this.setHSV(120, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asBlue() {
        this.setHSV(240, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asYellow() {
        this.setHSV(60, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asCyan() {
        this.setHSV(180, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    public void asMagenta() {
        this.setHSV(300, MAX_SATURATION, MAX_BRIGHTNESS);
    }

    // GET METHODS
    public Integer getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getBrightness() {
        return brightness;
    }


    // SET METHODS
    public void setHue(Integer hue) {
        this.hue = hue;

        this.updateObservers();
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;

        this.updateObservers();
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;

        this.updateObservers();
    }

    // Convenient setters: Hue, Saturation and Brightness
    public void setHSV(Integer hue, float saturation, float brightness) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;

        this.updateObservers();
    }

    // the model has changed!
    // broadcast the update method to all registered observers
    private void updateObservers() {
        this.setChanged();
        this.notifyObservers();
    }

}