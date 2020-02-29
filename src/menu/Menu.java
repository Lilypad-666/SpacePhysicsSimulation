package menu;

import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class Menu extends PApplet {

    List<Button> buttons;
    planets.PlanetSurface pSurface;
    boolean active = true;

    @Override
    public void settings() {
        size(1000, 800);
    }

    @Override
    public void setup() {
        background(255);
        frameRate(60);

        buttons = new ArrayList<>();

        buttons.add(new Button(this, "Graph", "Plots points as the simulation runs", 100, 100, 150, 50));
        buttons.add(new Button(this, "Start", "Starts the simulation", width / 2 - 75, 650, 150, 50));
        buttons.add(new Button(this, "Lines", "Live updates lines on the graph. **AFFECTS PERFORMANCE A LOT**", width / 2 - 75, 100, 150, 50, false));
        buttons.add(new Button(this, "Dots", "Live updates dots on the graph. **Affects Performance A Bit**", 750, 100, 150, 50, true));
        buttons.add(new Button(this, "Wrap", "Planets/Objects wrap around the screen", 100, 200, 150, 50, false));
        buttons.add(new Button(this, "N-Body", "All planets pull on eachother.", width-250, 200, 150, 50, false));
        
        List<String> modes = new ArrayList<>();

        modes.add("Orbit");
        modes.add("Particle");
        modes.add("Earth");
        modes.add("Cycloid");

        List<String> info = new ArrayList<>();

        //Orbit
        info.add("A simulation of orbiting around earth.");
        
        //Particle
        info.add("Particle simulation test.");
        
        //Earth
        info.add("Dropping ball on earth simulation.");
        
        //Cycloid
        info.add("A simulation of a cycloid (no visualization)");

        buttons.add(new ButtonWithArrows(this, "Mode", "Select which simulation to run.", width / 2 - 75, 350, 150, 50, modes, info));
    }

    @Override
    public void draw() {
        if (active) {
            background(255);
            strokeWeight(5);
            fill(0, 0, 0);
            textSize(30);
            textAlign(CENTER);
            text("Ω's Group", width / 2, 50);

            buttons.forEach((b) -> b.draw());

            for (Button button : buttons) {
                if (button.hovering(mouseX, mouseY)) {
                    button.showInfo();
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        for (Button button : buttons) {
            if (button instanceof ButtonWithArrows) {
                ButtonWithArrows tempButton = (ButtonWithArrows) button;
                if (tempButton.pointInsideLeft(mouseX, mouseY)) {
                    tempButton.moveLeft();
                    button = tempButton;
                } else if (tempButton.pointInsideRight(mouseX, mouseY)) {
                    tempButton.moveRight();
                    button = tempButton;
                }
            } else if (button.pointInside(mouseX, mouseY)) {

                if (button.getText().equals("Start")) {
                    startButton();
                } else {
                    button.toggle();
                }
            }
        }
    }

    public void startButton() {
        boolean lGraph = true;
        boolean lines = false;
        boolean dots = true;
        boolean wrap = false;
        boolean nbody = false;
        int mode = 0;

        for (Button button : buttons) {
            if (button.getText().equals("Graph")) {
                lGraph = button.getEnabled();
            }

            if (button.getText().equals("Lines")) {
                lines = button.getEnabled();
            }

            if (button.getText().equals("Dots")) {
                dots = button.getEnabled();
            }

            if (button.getText().equals("Wrap")) {
                wrap = button.getEnabled();
            }
            
            if (button.getText().equals("N-Body")){
                nbody = button.getEnabled();
            }

            if (button.getText().equals("Mode")) {
                ButtonWithArrows tempButton = (ButtonWithArrows) button;

                mode = ((ButtonWithArrows)button).getSelection();
            }
        }

        String[] surfaceArgs = {"Surface"};

        pSurface = new planets.PlanetSurface();

        pSurface.setGraphCreation(lGraph);

        pSurface.setGraphDots(dots);

        pSurface.setGraphLines(lines);

        pSurface.setMode(mode);

        pSurface.setWrap(wrap);
        
        pSurface.setGravity(nbody);
        
        pSurface.createSquares(4, 5);

        PApplet.runSketch(surfaceArgs, pSurface);

        surface.setVisible(false);

        active = false;
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{menu.Menu.class.getName()});
    }
}
