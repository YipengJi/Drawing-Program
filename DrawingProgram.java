/*
1. Strokes can be set to black, red or yellow before being drawn by clicking on the buttons in toolbar.
Default colour is black when no colour was set.
When a stroke got selected, clicking colour buttons can change the colour of the slected stroke and the colour of the next stroke.
When selecting a colour before drawing next stroke, next stroke will be changed to the slected colour.

2. Stockes' thickness can be changed by typing in the desired thickness in the testfield of toolbar and press return.
Default thickness is 2.0f before drawing.
Thickness can be changed when clicking on the stroke. Background yellow highlight thickness will be changed with the stroke.
When changing thickness before drawing next stroke, next stroke will be changed to the xet thickness.
Other strokes' thickness can be changed easily to current set stroke thickness by clicking on them.
 */


import javax.swing.JFrame;

import model.DrawingModel;
import view.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.BorderLayout;

public class DrawingProgram {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Program");

        // create Model and initialize it
        model.DrawingModel model = new DrawingModel();

        // create View, tell it about model and controller
        ToolbarView vTool = new ToolbarView (model);
        StatusbarView vStat = new StatusbarView (model);
        CanvasView vCan = new CanvasView (model);

       // JScrollPane scrollPane = new JScrollPane(frame);
       // scrollPane.setViewportView (vCan);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(vTool, BorderLayout.NORTH);
        frame.getContentPane().add(vStat, BorderLayout.SOUTH);
        frame.getContentPane().add(vCan, BorderLayout.CENTER);

        // setup the window
        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

