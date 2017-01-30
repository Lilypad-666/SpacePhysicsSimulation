package menu;

import java.util.List;
import processing.core.*;

public class ButtonWithArrows extends Button {

    List<String> textList;
    List<String> infoList;
    int selection;

    ButtonWithArrows(PApplet pApp, String text, String info, double x, double y, double width, double height, List<String> textList, List<String> infoList) {
        super(pApp, text, info, x, y, width, height);

        this.textList = textList;
        this.infoList = infoList;

        selection = 0;
    }

    @Override
    public void draw() {
        pApp.fill(255, 255, 255);
        pApp.stroke(128, 128, 128);
        pApp.rect((float) x, (float) y, (float) width, (float) height);
        pApp.triangle((float) (x + width + width / 10), (float) (y), (float) (x + width + width / 10), (float) (y + height), (float) (x + width + width / 4), (float) (y + height / 2));
        pApp.triangle((float) (x - width / 10), (float) (y), (float) (x - width / 10), (float) (y + height), (float) (x - width / 4), (float) (y + height / 2));

        pApp.fill(0, 0, 0);

        pApp.text(text, (float) (x + width / 2), (float) (y - height / 4));

        pApp.text(textList.get(selection), (float) (x + width / 2), (float) (y + height / 2 + 10));

        pApp.text(infoList.get(selection), (float) (x + width / 2), (float) (y + height * 2));
    }

    public void moveRight() {
        if (selection + 1 < textList.size()) {
            selection += 1;
        } else {
            selection = 0;
        }
    }

    public void moveLeft() {
        if (selection - 1 >= 0) {
            selection -= 1;
        } else {
            selection = textList.size() - 1;
        }
    }

    public boolean pointInsideLeft(double x, double y) {
        return (x >= getX() - width / 4 && x <= getX() && y >= getY() && y <= getY() + height);
    }

    public boolean pointInsideRight(double x, double y) {
        return (x >= getX() + width && x <= getX() + width + width / 4 && y >= getY() && y <= getY() + height);
    }

    public int getSelection() {
        return selection;
    }

    @Override
    public boolean hovering(double x, double y) {
        return (x >= getX() && x <= getX() + getWidth() && y >= getY() - height && y <= getY() + getHeight());
    }
}
