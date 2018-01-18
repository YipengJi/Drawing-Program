package view;

import model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class ToolbarView extends JPanel {
    private DrawingModel model;
    private JButton button = new JButton("Delete");
    private JSlider scale = new JSlider(50, 200, 100);
    private JSlider rotate = new JSlider(-180, 180, 0);
    private JLabel sclb = new JLabel("1.0");
    private JLabel rtlb = new JLabel("0");
    private JLabel sc = new JLabel("Scale");
    private JLabel rt = new JLabel("Rotate");

    private JButton red = new JButton("Red");
    private JButton green = new JButton("Green");
    private JButton black = new JButton("Black");
    private JTextField thick = new JTextField(2);
    private JLabel tklb = new JLabel("Thickness");

    private NumberFormat formatter = NumberFormat.getNumberInstance();
    private NumberFormat formatter2 = DecimalFormat.getNumberInstance();
    private static final int MAX_FRACTION_DIGITS = 1;

    public ToolbarView(DrawingModel aModel) {
        super();
        this.model = aModel;
        this.layoutView();
        this.registerControllers();
        this.model.addView(new IView() {
            public void updateView() {

                if (!model.getClicked()) {
                    scale.setValue(100);
                    rotate.setValue(0);
                    sclb.setText(formatter2.format(1.0));
                    rtlb.setText(formatter.format(0.0));
                    scale.setEnabled(false);
                    sclb.setEnabled(false);
                    rotate.setEnabled(false);
                    rtlb.setEnabled(false);
                    button.setEnabled(false);
                    sc.setEnabled(false);
                    rt.setEnabled(false);

                } else {
                    if (model.getCounterValue() != 0) {
                        if (model.curstk != null) {

                            // current scale and rotate when update toolbar

                            scale.setValue((int) model.getCurstkScale());
                            rotate.setValue((int) model.getCurstkRotate());
                            sclb.setText(formatter2.format(model.getCurstkScale()/100));
                            rtlb.setText(formatter.format(model.getCurstkRotate()));
                        } else {
                         // current scale and rotate when no stroke seleted  "
                            scale.setValue(100);
                            rotate.setValue(0);
                            sclb.setText(formatter2.format(1.0));
                            rtlb.setText(formatter.format(0.0));
                        }
                        scale.setEnabled(true);
                        sclb.setEnabled(true);
                        rotate.setEnabled(true);
                        rtlb.setEnabled(true);
                        button.setEnabled(true);
                        sc.setEnabled(true);
                        rt.setEnabled(true);
                        red.setEnabled(true);
                        green.setEnabled(true);
                        black.setEnabled(true);
                        thick.setEnabled(true);
                        tklb.setEnabled(true);
                    }
                }

            }
        });

        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //deletes the selected stroke
                model.removeStroke();
                model.setClickedfalse();
            }
        });
        red.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                model.setRed();
            }
        });
        green.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                model.setGreen();
            }
        });
        black.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                model.setBlack();
            }
        });

        thick.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Float tk = Float.parseFloat(thick.getText());
                model.setThick(tk);
            }
        });
    }

    private void layoutView() {
        this.setLayout(new GridLayout(2,1));

        this.add(this.button);
        this.add(this.black);
        this.add(this.red);
        this.add(this.green);
        this.add(this.tklb);
        this.add(this.thick);
        this.add(this.sc);
        this.add(this.scale);
        this.add(this.sclb);
        this.add(this.rt);
        this.add(this.rotate);
        this.add(this.rtlb);

        red.setBackground(Color.RED);
        red.setOpaque(true);
        green.setBackground(Color.GREEN);
        green.setOpaque(true);
        black.setBackground(Color.BLACK);
        black.setOpaque(true);

        this.formatter2.setMaximumFractionDigits(MAX_FRACTION_DIGITS);
    }

    private void registerControllers() {
        this.scale.addChangeListener(new scaleController());
        this.rotate.addChangeListener(new rotateController());
    }

    private class scaleController implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            double sc = scale.getValue();
            //System.out.println("scale when changed " + sc);

            if (model.getClicked()) {
                model.curstk.shape.setscv(sc/100);
                model.stk.shape.setscv(sc/100);
                model.setCurstkScale(sc);
            }
            model.setScale(sc);
        }
    }

    private class rotateController implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            double rt = rotate.getValue();
           // System.out.println("rotate when changed " + rt);
            if (model.getClicked()) {
                model.curstk.shape.setrtv(rt);
                model.stk.shape.setrtv(rt);
                model.setCurstkRotate(rt);
            }
            model.setRotate(rt);
        }
    }
}
