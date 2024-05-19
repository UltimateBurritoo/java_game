
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {

    static GameWindow window;
    static Thread mainThread;

    public static void main(String[] args) {
        AssetLoader.loadSprites();
        window = new GameWindow();
        System.out.println("debuug message lol");
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