package menu;

import processing.core.*;

public class Button {

    double x;
    double y;
    double width;
    double height;
    boolean enabled;
    vector.Vector color;
    String text;
    String info;
    PApplet pApp;

    public Button(PApplet pApp, String text, String info, double x, double y, double width, double height, boolean enabled) {
        this.pApp = pApp;
        this.text = text;
        this.info = info;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = enabled;
    }

    public Button(PApplet pApp, String text, String info, double x, double y, double width, double height) {
        this.pApp = pApp;
        this.text = text;
        this.info = info;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = false;
    }

    public String getText() {
        return text;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public boolean pointInside(double x, double y) {
        return (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight());
    }

    public void toggle() {
        enabled = !enabled;
    }

    public void draw() {
        pApp.fill(255, 255, 255);
        pApp.stroke(128, 128, 128);
        pApp.rect((float) x, (float) y, (float) width, (float) height);

        if (enabled) {
            pApp.fill(0, 200, 100);
        } else {
            pApp.fill(200, 100, 0);
        }
        pApp.text(text, (float) (x + width / 2), (float) (y + height / 2 + 10));
    }

    public boolean hovering(double x, double y) {
        return (x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight());
    }

    public void showInfo() {
        pApp.fill(0, 0, 0);
        pApp.textAlign(pApp.LEFT);

        if (pApp.mouseX > pApp.width - 400) {
            pApp.text(info, pApp.mouseX - 350, pApp.mouseY + 10, pApp.mouseX - 450, pApp.mouseY + pApp.height);
        } else {
            pApp.text(info, pApp.mouseX + 10, pApp.mouseY + 10, pApp.mouseX + 50, pApp.mouseY + 100);
        }

        pApp.textAlign(pApp.CENTER);
    }
}
