import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

public class AssetLoader {
    static Dictionary<String, BufferedImage> sprites;
    static boolean loadedSprites;
    public static void loadSprites()
    {
        sprites = new Hashtable<>();
        File folder = new File("assets/sprites");
        File[] files = folder.listFiles();
        if(files != null){
            for(int i = 0; i < files.length; i++)
            {
                try
                {
                    String name = files[i].getName().substring(0,files[i].getName().indexOf("."));
                    System.out.println("Loaded sprite " + files[i].getPath() + " as " + name);
                    sprites.put(name, ImageIO.read(files[i]));
                }
                catch (IOException c)
                {
                    System.out.println("Failed to load sprite " + files[i].getPath() + ": " + c.getMessage());
                }

            }
        }
        loadedSprites = true;
    }
    public static boolean isLoadedSprites() {
        return loadedSprites;
    }
    public static BufferedImage getSprite(String name)
    {
        return sprites.get(name);
    }
}
