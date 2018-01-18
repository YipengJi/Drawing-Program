package model;
/*
*  Shape: See ShapeDemo for an example how to use this class.
*
*/
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import javax.vecmath.*;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.AffineTransform;


// simple shape model class
public class Shape {


    public AffineTransform N;

    public Shape () {};
    // shape points
    ArrayList<Point2d> points;

    public void clearPoints() {
        points = new ArrayList<Point2d>();
        pointsChanged = true;
    }
  
    // add a point to end of shape
    public void addPoint(Point2d p) {
        if (points == null) clearPoints();
        points.add(p);
        pointsChanged = true;
    }    

    public float getscv () { return this.scale;}

    public double getrtv () { return this.rtv; }

    public void setscv(double thescv) {
        this.scale = (float) thescv;
    }

    public void setrtv (double thertv) {
        this.rtv = thertv;
    }

    // add a point to end of shape
    public void addPoint(double x, double y) {
        addPoint(new Point2d(x, y));  
    }

    public int npoints() {
        return points.size();
    }

    // shape is polyline or polygon
    Boolean isClosed = false; 

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }    

    // if polygon is filled or not
    Boolean isFilled = false; 

    public Boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }    

    // drawing attributes
    Color colour = Color.BLACK;
    float strokeThickness = 2.0f;

    public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}

    public float getStrokeThickness() {
		return strokeThickness;
	}

	public void setStrokeThickness(float strokeThickness) {
		this.strokeThickness = strokeThickness;
	}

    // shape's transform

    // quick hack, get and set would be better
    public float scale = 1.0f;

    public double rtv = 0;

    // some optimization to cache points for drawing
    Boolean pointsChanged = false; // dirty bit
    int[] xpoints, ypoints;
    public int npoints = 0;

    void cachePointsArray() {
        xpoints = new int[points.size()];
        ypoints = new int[points.size()];
        for (int i=0; i < points.size(); i++) {
            xpoints[i] = (int)points.get(i).x;
            ypoints[i] = (int)points.get(i).y;
        }
        npoints = points.size();
        pointsChanged = false;
    }

    public int lowerx = 800;
    public int upperx = 0;
    public int lowery = 600;
    public int uppery = 0;
    public int centrex, centrey;

    // find bounding box (centre point) of the shape
	public int findcentreX () {
	    int tempx, tempy;
        for (int i=0; i < points.size(); i++) {
            tempx = xpoints[i];

            lowerx = Math.min(tempx, lowerx);
            upperx = Math.max(tempx, upperx);

        }
        centrex = ((upperx -lowerx) / 2) + lowerx;
        return centrex;
    }
    public int findcentreY () {
        int tempx, tempy;
        for (int i=0; i < points.size(); i++) {
            tempy = ypoints[i];

            lowery = Math.min(tempy, lowery);
            uppery = Math.max(tempy, uppery);
        }
        centrey = ((uppery -lowery) / 2) + lowery;
        return centrey;
    }


    // let the shape draw itself
    // (note this isn't good separation of shape View from shape Model)
    public void draw(Graphics2D g2) {

        // don't draw if points are empty (not shape)
        if (points == null) return;

        // see if we need to update the cache
        if (pointsChanged) cachePointsArray();

        // save the current g2 transform matrix 
       // AffineTransform M = g2.getTransform();

       // System.out.println("scv & rtv   " + scale + rtv );
        AffineTransform H = AffineTransform.getScaleInstance(1,1);

        g2.translate(findcentreX(),findcentreY());
        g2.scale(scale, scale);
        g2.rotate(rtv);
        g2.translate((findcentreX() * -1),(findcentreY() * -1));
        //g2.scale(1.0,1.0);
        N = g2.getTransform();

        // call drawing functions
        g2.setColor(colour);
       // System.out.println(colour);
        if (isFilled) {
            g2.fillPolygon(xpoints, ypoints, npoints);
        } else {
            // can adjust stroke size using scale
        	//g2.setStroke(new BasicStroke(strokeThickness / scale));
            g2.setStroke(new BasicStroke(strokeThickness / scale));
            if (isClosed)
                g2.drawPolygon(xpoints, ypoints, npoints);
            else
                g2.drawPolyline(xpoints, ypoints, npoints);
        }

        // reset the transform to what it was before we drew the shape
        g2.setTransform(H);
    }
    
   
    // let shape handle its own hit testing
    // (x,y) is the point to test against
    // (x,y) needs to be in same coordinate frame as shape, you could add
    // a panel-to-shape transform as an extra parameter to this function
    // (note this isn't good separation of shape Controller from shape Model)    
    public boolean hittest(double x, double y)
    {
        double dis = 0;
       // boolean ifhit = false;
        double xp1, yp1, xp2, yp2;
        Line2D.Double line;
    	if (points != null) {

            for (int i=1; i<points.size(); i++) {
                xp1 = (double) xpoints[i-1];
                yp1 = (double) ypoints[i-1];
                xp2 = (double) xpoints[i];
                yp2 = (double) ypoints[i];
                line = new Line2D.Double(xp1,yp1,xp2,yp2);
                dis = line.ptSegDist (x,y);
                if (dis <= 5) {
                    return true;
                }
            }

    	}
    	
    	return false;
    }

}
