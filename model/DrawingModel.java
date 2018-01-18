package model;

import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.*;
import javax.vecmath.*;

import javax.swing.border.Border;
import java.lang.Boolean;


public class DrawingModel {
    public int counter = 0;
    public boolean clicked = false;
    public Color colour = Color.BLACK;
    public float thick = 2.0f;

    /* A list of the model's views. */
    private ArrayList<IView> views = new ArrayList<IView>();

    private double scale = 100;
    private double rotate = 0;

    public Vector <Stroke> strokes = new Vector<Stroke>();
    public Stroke curstk;
    public Stroke stk;

    public class Stroke {
        public model.Shape shape;
        public double scale = 100;
        public double rotate = 0;
        public int pointnum = 0;


        public Stroke (model.Shape sp) {
            this.shape = sp;
        }

    }

    public DrawingModel() {
    }

    public void setRed () {
        this.colour = Color.RED;
        this.updateAllViews();
    }

    public void setGreen() {
        this.colour = Color.GREEN;
        this.updateAllViews();
    }

    public void setBlack () {
        this.colour = Color.BLACK;
        this.updateAllViews();
    }

    public void setColour (Color thecolor) {
        this.colour = thecolor;
        this.updateAllViews();
    }

    public Color getColour () {
        return this.colour;
    }

    public void setThick (float t) {
        this.thick = t;
        this.updateAllViews();
    }

    public float getThick () {
        return this.thick;
    }

    public void addStroke (Stroke s) {
        this.strokes.addElement(s);
        this.updateAllViews();
    }

    public void deleteStroke (Stroke s) {
        int i = this.strokes.indexOf(curstk);
        this.strokes.removeElementAt(i);
        this.updateAllViews();
    }

    public void removeStroke () {
        int i = this.strokes.indexOf(curstk);

        if (i>0) {
            this.strokes.removeElementAt(i);
            this.strokes.removeElementAt(i - 1);
            this.counter--;
            this.updateAllViews();
        }
    }


    /** Set the scale to a new value.*/
    public void setScale(double thescale) {
        this.scale = thescale;
        this.updateAllViews(); // update Views!
    }

    /** Set the rotate to a new value.  */
    public void setRotate(double therotate) {
        this.rotate = therotate;
        this.updateAllViews(); // update Views!
    }

    /** Set both the scale and the rotate to new values. */
    public void setValues(double thescale, double therotate) {
        this.scale = thescale;
        this.rotate = therotate;

        this.updateAllViews(); // update Views!
    }

    public void setCurstkValues(double thescale, double therotate) {
        this.curstk.scale = thescale;
        this.curstk.rotate = therotate;
        this.stk.scale = thescale;
        this.stk.rotate = therotate;

        this.updateAllViews(); // update Views!
    }

    public void setCurstkScale(double thescale) {
        this.curstk.scale = thescale;
        this.stk.scale = thescale;
        this.updateAllViews(); // update Views!
    }

    public void setCurstkRotate(double therotate) {
        this.curstk.rotate = therotate;
        this.stk.rotate = therotate;
        this.updateAllViews(); // update Views!
    }

    public void setCurstkPointnum(int ptn) {
        this.curstk.pointnum = ptn;
        this.updateAllViews(); // update Views!
    }

    public double getCurstkScale() {

        return this.curstk.scale;
    }

    public double getCurstkRotate() {

        return this.curstk.rotate;
    }

    public int getCurstkPointnum() {
        return this.curstk.pointnum;
    }

    public void setCounter(int thecounter) {
        this.counter = thecounter;
        this.updateAllViews(); // update Views!
    }

    public boolean getClicked() {
        return this.clicked;
    }

    public void setClickedtrue() {
        this.clicked = true;
        this.updateAllViews(); // update Views!
    }

    public void setClickedfalse() {
        this.clicked = false;
        this.updateAllViews(); // update Views!
    }

    /** Get the scale */
    public double getScale() {
        return this.scale;
    }

    /** Get the rotate. */
    public double getRotate() {
        return this.rotate;
    }

    public int getCounterValue() {
        return this.counter;
    }

    public void incrementCounter() {
            counter++;
            //System.out.println("Model: increment counter to " + counter);
            updateAllViews() ;
    }

    public void decrementCounter() {
        counter--;
        updateAllViews() ;
    }


    /** Add a new view */
    public void addView(IView view) {
        this.views.add(view);

        view.updateView(); // update Views!
    }

    /** Remove a view*/
    public void removeView(IView view) {

        this.views.remove(view);
        view.updateView();
    }

    /** Update all the views */
    private void updateAllViews() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
}