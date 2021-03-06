package view;

import model.*;

import java.lang.Boolean;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.NoninvertibleTransformException;

import java.awt.LayoutManager;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;


public class CanvasView extends JPanel {
    private model.Shape shape;
    private model.Shape hitshape;
    private model.Shape hs;
    private model.Shape bgshape;
    private DrawingModel amodel;
    private DrawingModel.Stroke stroke;
    private DrawingModel.Stroke hitstroke;
    private DrawingModel.Stroke bgstroke;
    private int ptn;
    private boolean clickflag = false;
    private boolean clickin = false;
    private int flag = 0;
    private int dragflag = 0;

    private double[] matrix = new double[6];

    public CanvasView(DrawingModel amodel) {
        super();
        this.amodel = amodel;
        this.setPreferredSize(new Dimension(800,560));
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        this.amodel.addView(new IView() {
            public void updateView() {
                if (amodel.curstk != null) {
                    amodel.curstk.shape.setscv(amodel.getCurstkScale() / 100);
                    amodel.curstk.shape.setrtv(amodel.getCurstkRotate());
                    amodel.stk.shape.setscv(amodel.getCurstkScale() / 100);
                    amodel.stk.shape.setrtv(amodel.getCurstkRotate());
                    repaint();
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (amodel.strokes != null) {
                    for (int i = 0; i < amodel.strokes.size(); i++) {
                        hs = amodel.strokes.elementAt(i).shape;
                        hs.setColour(Color.BLACK);
                        hs.setStrokeThickness(2.0f);
                        amodel.setClickedfalse();
                        amodel.curstk = null;
                        amodel.stk = null;
                    }

                    for (int i = amodel.strokes.size()-1; i>0; i--) {
                        int x = e.getX();
                        int y = e.getY();
                       // System.out.println("old x y" + x + "..." + y);
                        hs = amodel.strokes.elementAt(i).shape;
                        hitshape = amodel.strokes.elementAt(i).shape;
                        if (hs!= null) {
                            try {
                                Point2D mousepoint = new Point2D.Double((double)e.getX(),(double)e.getY());
                                Point2D mpt = new Point2D.Double();
                               // Point2D mpt =
                                hs.N.inverseTransform(mousepoint, mpt);
                               /* if (hs.N!=null) {
                                    hs.N.getMatrix(matrix);
                                    for (int j = 0; j < matrix.length - 1; j++) {
                                        System.out.println(j + "..." + matrix[j]);
                                    }
                                }*/
                                hitshape.N.inverseTransform(mousepoint, mpt);
                                x = (int) mpt.getX();
                                y = (int) mpt.getY();
                            } catch (NoninvertibleTransformException excep) {
                                System.out.print("non-convertible");
                            }
                        }

                       /* System.out.println("new x y" + x + "..." + y);

                          System.out.println("hittest for old x y");
                          System.out.println(hs.hittest(e.getX (), e.getY()));
                          System.out.println("hittest for new x y");
                          System.out.println(hs.hittest(x,y));
                    if (amodel.curstk != null) {
                        System.out.println("canvas not hit current stroke & stk " + amodel.getCurstkScale());
                    }*/
                       //   if (hs.hittest(e.getX(), e.getY())) {
                        if (hs.hittest((double)x, (double)y)) {
                           // hitshape = new model.Shape();
                            amodel.setClickedtrue();
                           // hitshape = hs ;
                            bgshape = hs;
                            bgshape.setColour(Color.YELLOW);
                            bgshape.setStrokeThickness(6.0f);
                            bgshape.scale = 1.0f;
                            amodel.curstk = amodel.strokes.elementAt(i);
                            amodel.stk = amodel.strokes.elementAt(i-1);
                           // System.out.println("canvas (hit) current stroke & stk " + amodel.getCurstkScale());
                            amodel.setCurstkValues(amodel.getCurstkScale(),amodel.getCurstkRotate());
                            break;
                        }

                    }
                } //repaint();
            }
        });

        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                bgshape = new model.Shape();
                bgshape.setColour(Color.BLACK);
                bgshape.scale = 1.0f;

                shape = new model.Shape();
                shape.setColour(Color.BLACK);
                shape.scale = 1.0f;
            }
        });

        this.addMouseMotionListener(new MouseAdapter(){
            public void mouseDragged(MouseEvent e) {
                dragflag = 1;
                bgshape.addPoint(e.getX(), e.getY());
                shape.addPoint(e.getX(), e.getY());
                ptn = shape.npoints();
                if (ptn == 2) {

                    stroke = amodel.new Stroke(shape);
                    amodel.addStroke(stroke);

                    bgstroke = amodel.new Stroke(bgshape);
                    amodel.addStroke(bgstroke);

                    amodel.incrementCounter();
                }

                repaint();
            }
        });

    }
    // custom graphics drawing
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  // antialiasing look nicer
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (amodel.strokes != null) {
            for (int i= amodel.strokes.size()-1; i>=0; i--) {
                amodel.strokes.elementAt(i).shape.draw(g2);
            }
        }
            return;
    }
}