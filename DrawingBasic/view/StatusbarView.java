package view;

import model.*;
import java.lang.String;
import java.lang.Math;
import java.text.DecimalFormat;
//import java.util.Observable;
//import java.util.Observer;

import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import java.awt.LayoutManager;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class StatusbarView extends JPanel {
    // the model that this view is showing
    private DrawingModel model;
    private JLabel label;
    private int counter = 0;

    public StatusbarView(DrawingModel model) {
        super();
        // create UI
        this.layoutView();

        // set the model
        this.model = model;

        this.model.addView(new IView() {
            /** The model changed. Ask the system to repaint */
            public void updateView() {
                String s = Integer.toString(model.getCounterValue());
                String d = Integer.toString(model.getCounterValue());
                s = s + " Strokes";
                if (!model.getClicked()) {
                    label.setText(s);
                } else {
                    if (model.getCounterValue() != 0) {
                        if (model.curstk != null) {
                            DecimalFormat df=new DecimalFormat("0.0");
                            String dd = df.format(model.getCurstkScale()/100);
                            d = d + " Strokes, Selection (" + Integer.toString( model.curstk.shape.npoints()) +
                                    " points, scale: " + dd
                                    + ", rotation " + Integer.toString((int)model.getCurstkRotate()) + ")";
                            label.setText(d);
                        } else {
                            label.setText(s);
                        }
                    }
                }
                repaint();
            }
        });

    }

    private void layoutView () {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("100 Strokes");
        label.setPreferredSize(new Dimension(800, 20));
        label.setHorizontalAlignment( SwingConstants.LEFT );
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        this.add(this.label);
    }

}