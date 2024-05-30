
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {

    static GameWindow window;
    static Thread mainThread;

    public static void main(String[] args) {
        // load all the sprites in the assets folder to memory
        AssetLoader.loadSprites();
        // create a new window
        window = new GameWindow();
        System.out.println("debuug message lol");
        // start the main game thread
        mainThread = new Thread(window);
        mainThread.start();
    }

    public static GameWindow getWindow(){
        return window;
    }
    public static Thread getMainThread()
    {
        return mainThread;
    }
}