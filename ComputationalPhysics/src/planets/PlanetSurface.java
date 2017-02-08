package planets;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import processing.core.*;

public class PlanetSurface extends PApplet {

    List<GravityObj> objects;
    double gravity;
    boolean running;
    boolean drawing;
    boolean info;
    boolean nbody;
    
    int rows;
    int columns;
    
    NBodySquare[][] squares;
    
    public static double t = 0;
    public static double dt = 1.6667e-2;
    double dtMod = 1;

    boolean graphDots = false;
    boolean graphLines = false;
    boolean createGraph = false;
    graph.Graph graph = new graph.Graph();

    Mode mode;
    boolean wrap;

    enum Mode {
        cycloid,
        particle,
        orbit,
        earth;
    }

    @Override
    public void settings() {
        size(1800, 1012);
    }
    
    public void setGravity(boolean nbody){
        this.nbody = nbody;
    }

    public void addToObjects(GravityObj object) {
        objects.add(object);
        objects.forEach((o) -> {
            o.update(objects);
            o.setWrap(wrap);
        });
    }
    
    public void updateSquares() {
        for (int rowNdx = 0; rowNdx < squares.length; rowNdx++){
            for (int columnNdx = 0; columnNdx < squares[0].length; columnNdx++){
                squares[rowNdx][columnNdx].setSquares(squares);
            }
        }
    }
    
    public void removeFromObjects(GravityObj object){
        if (objects.contains(object)){
            objects.remove(object);
            objects.forEach((o) -> {
                o.update(objects);
            });
        }
    }

    @Override
    public void setup() {
        background(0);
        stroke(255, 255, 0);
        frameRate(100);
        running = false;
        drawing = true;

        graph = new graph.Graph();

        objects = new ArrayList<>();

        if (mode == Mode.orbit){
            double scale = 1;
            setTimeScale(1);
            
            PlanetObj test = new PlanetObj(this, "test", width/2, height/2+50, 50, 5e10, objects, scale);
            
            addToObjects(test);
            
            test.addYVel(-1e-10);
            
            PlanetObj test2 = new PlanetObj(this, "test2", width/2, height/2-50, 50, 5e10, objects, scale);
            
            addToObjects(test2);
            
            test2.addYVel(1e-10);
        }
        
        if (mode == Mode.orbit) {

            double scale = 1e10;
            
            setTimeScale(5e6);

            PlanetObj sun = new PlanetObj(this, "sun", width/2, height/2, 5, 1.989e30, objects, scale);

            addToObjects(sun);
            
            PlanetObj mercury = new PlanetObj(this, "mercury", width/2+(5.791*(Math.abs(1e10/scale))), height/2, 5, 3.285e23, objects, scale);

            addToObjects(mercury);
            
            mercury.addYVel(4.78);

            PlanetObj venus = new PlanetObj(this, "venus", width/2+(10.82*(Math.abs(1e10/scale))), height/2, 5, 4.867e24, objects, scale);
            
            addToObjects(venus);
            
            venus.addYVel(3.5);
            
            PlanetObj earth = new PlanetObj(this, "earth", width/2+(14.96*(Math.abs(1e10/scale))), height/2, 5, 5.972e24, objects, scale);

            addToObjects(earth);
            
            earth.addYVel(2.975);
            
            PlanetObj mars = new PlanetObj(this, "mars", width/2+(22.78*(Math.abs(1e10/scale))), height/2, 5, 6.39e23, objects, scale);
            
            addToObjects(mars);
            
            mars.addYVel(2.4145);
            
            PlanetObj jupiter = new PlanetObj(this, "jupiter", width/2+(77.85*(Math.abs(1e10/scale))), height/2, 5, 1.898e27, objects, scale);
            
            addToObjects(jupiter);
            
            jupiter.addYVel(1.3025);
            
            PlanetObj saturn = new PlanetObj(this, "saturn", width/2+(142.9*(Math.abs(1e10/scale))), height/2, 5, 5.683e26, objects, scale);
            
            addToObjects(saturn);
            
            saturn.addYVel(0.963);
            
            PlanetObj uranus = new PlanetObj(this, "uranus", width/2+(287.1*(Math.abs(1e10/scale))), height/2, 5, 8.681e25, objects, scale);
            
            addToObjects(uranus);
            
            uranus.addYVel(0.6839);
            
            PlanetObj neptune = new PlanetObj(this, "neptune", width/2+(449.8*(Math.abs(1e10/scale))), height/2, 5, 1.024e26, objects, scale);
            
            addToObjects(neptune);
            
            neptune.addYVel(0.543);

            //graph.planetIn(earth, scale);
        }

        if (mode == Mode.particle){
            double scale = 5e10;
            setTimeScale(1e8);
            
            //ParticleObj sun = new ParticleObj(this, "sun", width/2, height/2, 30, 1e27, objects, scale);
            //addToObjects(sun);
            //sun.setGrow(false);
            
            int totalObjects = 1000;
            
            for (int ndx = 0; ndx < totalObjects; ndx++){
                //1e22
                ParticleObj temp = new ParticleObj(this, "" + ndx, Math.random()*width, Math.random()*height, 5, (1e28+(Math.random()*1e28)/totalObjects), objects, scale);
                addToObjects(temp);
                
                for (int rowNdx = 0; rowNdx < rows; rowNdx++){
                    for (int columnNdx = 0; columnNdx < columns; columnNdx++){
                        squares[rowNdx][columnNdx].setScale(scale);
                    }
                }
                
                double tempScale = 1;
                
                //temp.addXVel(tempScale/2-Math.random()*tempScale);
                //temp.addYVel(tempScale/2-Math.random()*tempScale);
                
                
                /*/Makes Spiral
                if (temp.position.i > width/2 & temp.position.j < height/2){
                    //Top right
                    temp.addYVel(Math.random()*tempScale);
                } else if (temp.position.i > width/2 && temp.position.j > height/2){
                    //Bottom right
                    temp.addXVel(Math.random()*-tempScale);
                } else if (temp.position.i < width/2 && temp.position.j > height/2){
                    //Bottom left
                    temp.addYVel(Math.random()*-tempScale);
                } else if (temp.position.i < width/2 && temp.position.j < height/2){
                    //Top left
                    temp.addXVel(Math.random()*tempScale);
                }*/
            }
        }
        
        if (mode == Mode.cycloid){
            graph.setCycloid(true);
        }

        if (mode == Mode.earth){
            double scale = 1;
            
            setTimeScale(1);
            
            PlanetObj earth = new PlanetObj(this, "earth", width/2, height, 6.371e6 * 2, true, 5.972e24, objects, scale);

            addToObjects(earth);
            
            earth.putAtBottom();
            
            PlanetObj ball = new PlanetObj(this, "ball", width/2, 25, 10, 0.0585, objects, scale);
            
            addToObjects(ball);
            
            graph.planetIn(ball, scale);
        }
        
        if (createGraph) {
            String[] largs = {"GraphAddOn"};

            PApplet.runSketch(largs, graph);

            graph.setDots(graphDots);
            graph.setLines(graphLines);
        }
    }

    public void createSquares(int rows, int columns){
        squares = new NBodySquare[rows][columns];
        
        this.rows = rows;
        this.columns = columns;
        
        //REMINDER THAT WIDTH AND HEIGHT ARENT WORKING SO IM MANUALLY
        //PUTTING THEM IN
        double oneHeight = (1012+100)/squares.length;
        double oneWidth = (1800+100)/squares[0].length;
        //Make Squares
        for (int rowNdx = 0; rowNdx < rows; rowNdx++){
            for (int columnNdx = 0; columnNdx < columns; columnNdx++){
                squares[rowNdx][columnNdx] = new NBodySquare(this, rows, columns, columnNdx*oneWidth + (oneWidth/2)-50, rowNdx*oneHeight + (oneHeight/2)-50);
            }
        }
    }
    
    public void doSquareGravity(){
        for (int rowNdx = 0; rowNdx < squares.length; rowNdx++){
            for (int columnNdx = 0; columnNdx < squares[0].length; columnNdx++){
                squares[rowNdx][columnNdx].doGravity();
            }
        }
        
        
    }
    
    public void clearSquares(){
        for (int rowNdx = 0; rowNdx < squares.length; rowNdx++){
            for (int columnNdx = 0; columnNdx < squares[0].length; columnNdx++){
                squares[rowNdx][columnNdx].clear();
            }
        }
    }
    
    @Override
    public void draw() {
        
        background(0);
        
        dt = dtMod/frameRate;
        
        int objectCount = 0;
        
        double oneHeight = (1012+100)/squares.length;
        double oneWidth = (1800+100)/squares[0].length;
        for (int rowNdx = 0; rowNdx < rows; rowNdx++){
            for (int columnNdx = 0; columnNdx < columns; columnNdx++){
                line((float)(columnNdx*oneWidth)-50, 0, (float)(columnNdx*oneWidth)-50, 1012);
            }
            line(0, (float)(rowNdx*oneHeight)-50, 1800, (float)(rowNdx*oneHeight)-50);
        }
        
        for (GravityObj o : objects){
            if (!o.dead){
                if (!(o.position.i > -50 && o.position.i < width+50 && o.position.j > -50 && o.position.j < height+50)){
                    if (mode != Mode.earth){
                        o.kill();
                        o.mass = 0;
                    }
                }
                
                objectCount++;

                if (running){
                    if (nbody)
                        o.step();
                    else{
                        oneHeight = (height+100)/squares.length;
                        oneWidth = (width+100)/squares[0].length;
                        
                        double tempX = o.getX() + 50;
                        double tempY = o.getY() + 50;
                        
                        int orow = (int)Math.floor(tempY/oneHeight);
                        int ocolumn = (int)Math.floor(tempX/oneWidth);
                        
                        if (orow < rows && orow >= 0 && ocolumn < columns && ocolumn >= 0)
                            squares[orow][ocolumn].addObj(o);
                    }
                    t += dt;
                }

                if (drawing)
                    o.draw();
            }
        }
        
        updateSquares();
        doSquareGravity();
        
        clearSquares();
        
        if (info){
            textSize(16);
            textAlign(LEFT);
            
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);
            text(df.format(frameRate), 15, 31);
            text("fps", 65, 31);
            text(objectCount + " objects", 15, 52);
            
            double total = dtMod*(frameRate/60);
            int timeY = (int)total/31_557_600;
            total %= 31_557_600;
            int timeD = (int)total/86_400;
            total %= 86_400;
            int timeH = (int)total/3_600;
            total %= 3_600;
            int timeM = (int)total/60;
            total %= 60;
            int timeS = (int)total;
            total -= (int)total;
            int timeMs = (int)(total*100);
            
            if (timeY >= 1){
                text("About " + timeY + " year(s) per real second.", 15, 114);
            } else if (timeD >= 1){
                text("About " + timeD + " day(s) per real second.", 15, 114);
            } else if (timeH >= 1){
                text("About " + timeH + " hour(s) per real second.", 15, 114);
            } else if (timeM >= 1){
                text("About " + timeM + " minute(s) per real second.", 15, 114);
            } else if (timeS >= 1){
                text("About " + timeS + " second(s) per real second.", 15, 114);
            } else {
                text("About " + timeMs + " millisecond(s) per real second.", 15, 114);
            }
            
        }
    }
    
    public void setTimeScale(double modifier){
        dtMod = modifier;
        dt = dtMod/frameRate;
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            running = !running;
            graph.pause(running);
        }
        
        if (key == 'm'){
            drawing = !drawing;
        }
        
        double dtMod = dt*frameRate;
        
        if (key == 'v' || key == '.'){
            setTimeScale(dtMod + (dtMod*0.1));
        }
        
        if (key == 'w' || key == ','){
            setTimeScale(dtMod - (dtMod*0.1));
        }
        
        if (key == 'b' || key == 'n'){
            info = !info;
        }
    }

    public void setGraphCreation(boolean c) {
        createGraph = c;
    }

    public void setGraphDots(boolean dots) {
        this.graphDots = dots;
    }

    public void setGraphLines(boolean lines) {
        this.graphLines = lines;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void pause(boolean running) {
        this.running = running;
    }

    public void setMode(int mode) {
        switch (mode) {
            case 0:
                this.mode = Mode.orbit;
                break;
            case 1:
                this.mode = Mode.particle;
                break;
            case 2:
                this.mode = Mode.earth;
                break;
            case 3:
                this.mode = Mode.cycloid;
                break;
        }
    }
}
