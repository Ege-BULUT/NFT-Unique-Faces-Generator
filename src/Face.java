import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Face {
    String _hat;
    String _eyes;
    String _mouth;
    String _hatType;
    String _glasses;
    public static List<String> eyes = new LinkedList<String>();
    public static List<String> hats = new LinkedList<String>();
    public static List<String> glasses = new LinkedList<String>();
    public static Color[] hatColors = new Color[]{Color.GRAY, Color.red, Color.blue};
    public static Color[] eyeColors = new Color[]{Color.BLUE, Color.GREEN, Color.BLACK, Color.YELLOW};
    public static Color[] glassesColor = new Color[]{Color.BLACK, Color.BLUE, Color.WHITE, Color.YELLOW, Color.green};



    public Face(String eyes, String mouth){
        this._eyes  = eyes;
        this._mouth = mouth; //8

        Face.eyes.add("Blue");
        Face.eyes.add("Green");
        Face.eyes.add("Black");
        Face.eyes.add("Yellow");

        Face.hats.add("Gray");
        Face.hats.add("Red");
        Face.hats.add("Blue"); // 32

        Face.glasses.add("Black");
        Face.glasses.add("Blue");
        Face.glasses.add("White");
        Face.glasses.add("Yellow");
        Face.glasses.add("Green"); // 192
    }

    public void addGlasses(String glasses){
        this._glasses = glasses;
    }

    public void addHat(String hat, String hatType){
        this._hat = hat;
        this._hatType = hatType;
    }

    public String[] getFace(){
        if(_glasses != null){
            return new String[]{_glasses, _hat,_hatType,_mouth};
        }else{
            return new String[]{_eyes, _hat,_hatType,_mouth};
        }
    }

    public String[] getFaceForText(){
        return new String[]{_eyes, _hat,_hatType,_glasses,_mouth};
    }

    public Color getEyes(){
        return eyeColors[eyes.indexOf(this._eyes)];
    }

    public Color getHat(){
        if(this._hat != null && !this._hat.equals("None")) {
            return hatColors[hats.indexOf(this._hat)];
        }
        return null;
    }

    public String gethatType(){
        return _hatType;
    }

    public Color getGlasses(){
        return glassesColor[glasses.indexOf(this._glasses)];
    }
}
