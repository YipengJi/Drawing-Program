import javax.swing.JFrame;

import model.DrawingModel;
import view.*;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.BorderLayout;

public class DrawingBasic {

    public static void main(String[] args) {
        JFrame frame = new JFrame("DrawingBasic");

        // create Model and initialize it
        //Model model = new Model();
        model.DrawingModel model = new DrawingModel();
        // create Controller, tell it about model
        //Controller controller = new Controller(model);
        // create View, tell it about model and controller
        //View view = new View(model, controller);
        ToolbarView vTool = new ToolbarView (model);
        StatusbarView vStat = new StatusbarView (model);
        CanvasView vCan = new CanvasView (model);

       // JScrollPane scrollPane = new JScrollPane(frame);
       // scrollPane.setViewportView (vCan);

       /* model.addObserver(vTool);
        model.addObserver(vStat);
        model.addObserver(vCan);
        model.notifyObservers();*/

       /* model.addView(vTool);
        model.addView(vStat);
        model.addView(vCan);
        model.updateAllViews();*/

        // tell Model about View
        //model.addView(view);

        // create second view ...
       // View2 view2 = new View2(model, controller);
       // model.addView(view2);

        // create a layout panel to hold the two views
       // JPanel p = new JPanel(new GridLayout(2,1));
       // frame.getContentPane().add(p);

        // add views (each view is a JPanel)
       // p.add(view);
       // p.add(view2);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(vTool, BorderLayout.PAGE_START);
        frame.getContentPane().add(vStat, BorderLayout.PAGE_END);
       // frame.getContentPane().add(vTool, BorderLayout.NORTH);
        //frame.getContentPane().add(vStat, BorderLayout.SOUTH);
        frame.getContentPane().add(vCan, BorderLayout.CENTER);

        // setup the window
        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

