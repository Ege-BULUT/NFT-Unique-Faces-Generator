import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;

public class FaceGenerator extends Canvas implements Runnable {


    private Thread thread;
    private boolean running = false;
    public static final int WIDTH = 640, HEIGHT = WIDTH/4 * 3;
    private static final long serialVersionUID = 1550691097823471818L;

    public FaceGenerator(){
        new Window(WIDTH, HEIGHT, "Game Title Here UwU", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double  delta = 0;
        int     frames = 0;
        double  amountOfTicks = 60.0;
        long    lastTime = System.nanoTime();
        int     deltaFrames = 0, tempFPS = 0;
        double  ns = 1000000000 / amountOfTicks;
        long    timer = System.currentTimeMillis();

        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                drawFaces();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                tempFPS = frames - deltaFrames;
                deltaFrames = frames;
                timer += 1000;
                System.out.println("FPS : " + tempFPS);
            }
        }
        stop();
    }

    private void tick(){

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics b = bs.getDrawGraphics();
        g.setColor(Color.PINK);
        b.setColor(Color.WHITE);
        b.fillRect(0, 0, 20, 20);
        g.fillOval(0, 0, 20, 20);
        bs.show();
    }

    public static String[] hatTypes = new String[]{"Cap", "Fedora"};
    public static String[] mouths = new String[]{"Happy", "Sad"};
    public static String[] hats = new String[]{"None", "Gray","Red","Blue"};
    public static String[] eyes = new String[]{"Blue", "Green","Black","Yellow"};
    public static String[] glasses = new String[]{"Black","Blue","White","Yellow","Green"};
    public static List<Face> faces = new ArrayList<Face>();

    public int scale = 1;
    public int x = 15, y = 5, index = 1;
    private void drawFaces(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics face = bs.getDrawGraphics();
        Graphics eyes = bs.getDrawGraphics();
        Graphics mouth = bs.getDrawGraphics();
        Graphics background = bs.getDrawGraphics();
        background.setColor(Color.WHITE);
        background.fillRect(0,0,getWidth(),getHeight());
        mouth.setColor(Color.white);
        face.setColor(new Color(201, 157, 131));

        for(int i = 0; i<faces.size(); i++){
            int _mouth = -180;
            if(faces.get(i)._mouth.equals("Sad")){ _mouth = 180;}
            eyes.setColor(faces.get(i).getEyes());
            face.fillOval(x, y, (20*scale), (30*scale));
            eyes.fillOval(x+(5*scale), y+(10*scale), (3*scale), (3*scale));
            eyes.fillOval(x+(10*scale), y+(10*scale), (3*scale), (3*scale));
            mouth.fillArc(x+(5*scale),y+(15*scale),(10*scale),(10*scale),0,_mouth);
            if(faces.get(i)._glasses != null) {
                Graphics glasses = bs.getDrawGraphics();
                glasses.setColor(faces.get(i).getGlasses());
                glasses.fillRoundRect(x + (3 * scale), y + (8 * scale), (5 * scale), (5 * scale), (5 * scale), (5 * scale));
                glasses.fillRoundRect(x + (8 * scale), y + (8 * scale), (5 * scale), (5 * scale), (5 * scale), (5 * scale));
                glasses.fillPolygon(new int[]{x + (13 * scale), x + (19 * scale), x + (17 * scale), x + (13 * scale)}, new int[]{y + (10 * scale), y + (9 * scale), y + (10 * scale), y + (11 * scale)}, 4);
            }
            if(faces.get(i).getHat() != null && !faces.get(i).getHat().equals("None")){
                Graphics hat = bs.getDrawGraphics();
                hat.setColor(faces.get(i).getHat());
                hat.fillPolygon(new int[]{x-(2*scale),x+(22*scale),x+(22*scale),x+(30*scale),x+(30*scale),x-(2*scale)},new int[]{y-(2*scale),y-(2*scale),y+(3*scale),y+(3*scale),y+(9*scale),y+(9*scale)},6);
                if(faces.get(i).gethatType().equals(hatTypes[1])){
                    hat.fillRect(x-(9*scale),y+(3*scale),(7*scale),(6*scale));
                }
            }

            if(index < faces.size()){
                index++;
                newFaceCoordinate();
            }else{
                index = 1;
                x=(15*scale); y=(5*scale);
            }
        }
        bs.show();
    }

    public void newFaceCoordinate(){
        if(x<(450*scale)){
            x += (50*scale);
        }
        else{
            y+=(50*scale);
            x = (15*scale);
        }
    }

    static boolean sameFace = false;
    public static void generateNewFace(){
        String _eyes, _hat, _hatType = null, _mouth, _glasses = null;
        int eyeIndex=0, hatIndex=0, hatTypeIndex, mouthIndex, glassesIndex;
        Face newFace;
        eyeIndex = (int) (Math.random() * (eyes.length));
        hatIndex = (int) (Math.random() * (hats.length));
        mouthIndex = (int) (Math.random() * (mouths.length));
        _eyes = eyes[eyeIndex];
        _hat = hats[hatIndex];
        _mouth = mouths[mouthIndex];
        newFace = new Face(_eyes, _mouth);
        if(!_hat.equals("None")){
            hatTypeIndex = (int) (Math.random() * (hatTypes.length));
            _hatType = hatTypes[hatTypeIndex];
            newFace.addHat(_hat,_hatType);

        }
        if((int) (Math.random() * 10) == 4){
            glassesIndex = (int) (Math.random() * (glasses.length));
            _glasses = glasses[glassesIndex];
            newFace.addGlasses(_glasses);
        }
        for(int i = 0; i<faces.size(); i++){
            if(Arrays.equals(faces.get(i).getFace(), newFace.getFace())){
                generateNewFace();
                sameFace = true;
            }
        }
        if(!sameFace) {
            faces.add(newFace);

            System.out.println("Eye : " + _eyes + "\nHat : " + _hat + "\nHat type : " + _hatType + "\nGlasses : " + _glasses + "\n" + _mouth + "\n");
        }
        sameFace = false;
    }

    public static void main(String[] args) {
        for(int i = 0; i<70; i++){
            generateNewFace();
        }
        String text;
        String[] category = new String[] {"Eyes","Hat","Hat Type","Glasses","Expression"};
        for(int n=0; n<5; n++) {
            System.out.print("\n"+category[n]);
            for(int i = 0; i<(15-category[n].length()); i++){
                System.out.print(" ");
            }
            System.out.print(": ");
            for (int i = faces.size() - 1; i >= 0; i--) {
                if (faces.get(i).getFaceForText()[n] == null){
                    text = "null";
                }
                else{
                    text = faces.get(i).getFaceForText()[n];
                }
                System.out.print(text);
                for(int s = 0; s<(10-text.length()); s++){
                    System.out.print(" ");
                }
            }
        }

        for(int i = 0; i<faces.size(); i++){
            for(int j = 0; j<faces.size(); j++){
                if(i != j && faces.get(i) == faces.get(j)){
                    System.out.println("\n"+(i+1)+". face and "+(j+1)+". face are exactly same.\nthey are both");
                    for(int k = 0; k<5; k++){
                        System.out.println(faces.get(j).getFaceForText()[k]);
                    }
                }
            }
        }
        new FaceGenerator();
    }
}
