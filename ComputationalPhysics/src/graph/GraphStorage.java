package graph;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class GraphStorage {

    PApplet pApp;
    List<GraphObj> graphs;
    double largestX;
    double largestY;
    double smallestX;
    double smallestY;
    double rangeX;
    double rangeY;
    boolean changedLargeSmall;
    double tempLargestX;
    double tempSmallestX;

    public GraphStorage(PApplet pApp) {
        graphs = new ArrayList<>();
        this.pApp = pApp;
        changedLargeSmall = false;
    }

    public void createGraph(String name) {
        graphs.add(new GraphObj(pApp, name));
    }

    public void createGraph(String name, double r, double g, double b) {
        graphs.add(new GraphObj(pApp, name, r, g, b));
    }

    public void draw(boolean running) {
        graphs.forEach((g) -> g.draw(rangeX, rangeY, smallestX, largestY, running));
    }

    public void drawLines() {
        graphs.forEach((g) -> g.drawLines(rangeX, rangeY, smallestX, largestY));
    }

    public void setPoint(String name, double x, double y) {
        double reverseY = -y;
        if (!changedLargeSmall) {
            largestX = x;
            largestY = reverseY;
            smallestX = x;
            smallestY = reverseY;
            changedLargeSmall = true;
        } else {
            if (x > largestX) {
                largestX = x;
            }

            if (reverseY < largestY) {
                largestY = reverseY;
            }

            if (x < smallestX) {
                smallestX = x;
            }

            if (reverseY > smallestY) {
                smallestY = reverseY;
            }
        }

        //Set Scales
        rangeX = Math.abs(largestX - smallestX);
        rangeY = Math.abs(largestY - smallestY);

        for (GraphObj g : graphs) {
            if (g.getName().equals(name)) {
                g.setPoint(x, reverseY);
            }
        }
    }

    public GraphObj getGraph(String name) {
        for (GraphObj g : graphs) {
            if (g.getName().equals(name)) {
                return g;
            }
        }

        return null;
    }

    public int numberOfGraphs() {
        return graphs.size();
    }

    public GraphObj getGraph(int index) {
        return graphs.get(index);
    }

    public double getMinX() {
        return smallestX;
    }

    public double getRangeX() {
        return rangeX;
    }

    public List<Double> getPoints() {
        List<Double> points = new ArrayList<>();
        for (GraphObj g : graphs) {
            List<Double> tempPoints = g.getPoints();

            for (double d : tempPoints) {
                points.add(d);
            }
        }
        return points;
    }

    public void setLargeSmallX(double largestX, double smallestX) {
        if (tempLargestX == 0 && tempSmallestX == 0) {
            this.tempLargestX = this.largestX;
            this.tempSmallestX = this.smallestX;
        }

        this.largestX = largestX;
        this.smallestX = smallestX;

        rangeX = Math.abs(largestX - smallestX);

        graphs.forEach((g) -> g.cropPoints(this.smallestX, rangeX));
    }

    public void resetZoom() {
        largestX = tempLargestX;
        smallestX = tempSmallestX;

        rangeX = Math.abs(largestX - smallestX);
        graphs.forEach((g) -> g.resetZoom());

        tempLargestX = 0;
        tempSmallestX = 0;
    }
}
