import java.awt.*;
import java.awt.image.BufferedImage;

class Tile
{
    BufferedImage image;
    boolean collision;
    public static final Tile[] tileset = new Tile[]
    {
            new Tile("tile_bricks",false),
            new Tile("wall_bricks",false)
    };
    public Tile(String sprite, boolean canCollide)
    {
        image = AssetLoader.getSprite(sprite);
        collision = canCollide;
    }
}

public class Tilemap extends GameRenderer {

    public static final int tileSize = 16;
    Vector2 origin = new Vector2(); // in tiles
    int gridWidth;
    int gridHeight;

    Tile[][] grid;

    public Tilemap(int w, int h)
    {
        gridWidth = w;
        gridHeight = h;
        grid = new Tile[w][h];
    }
    public Tilemap(int w, int h, int fill)
    {
        gridWidth = w;
        gridHeight = h;
        grid = new Tile[w][h];
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[x].length; y++)
            {
                grid[x][y] = Tile.tileset[fill];
            }
        }
    }

    public void setTile(int x, int y, int index)
    {
        try
        {
            grid[x][y] = Tile.tileset[index];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("failed to place tile "+ index +" at " + x + ", " + y);
        }
    }

    public void render(int x, int y, Graphics2D g) {
        int tilesX = Game.getWindow().getPixelWidth() / tileSize + 1;
        int tilesY = Game.getWindow().getPixelHeight() / tileSize + 1;
        for(int x0 = 0; x0 < tilesX; x0++)
        {
            for(int y0 = 0; y0 < tilesY; y0++)
            {
                // continuous tile coords
                Vector2 tilePos = new Vector2(
                        x0 + (origin.getX() - x) / tileSize,
                        y0 + (origin.getY() - y) / tileSize
                );
                int ix = (int)Math.floor(tilePos.getX());
                int iy = (int)Math.floor(tilePos.getY());
                if(ix < 0 || ix >= gridWidth || iy < 0 || iy >= gridHeight) continue;
                Tile t = grid[ix][iy];
                if(t == null) continue;
                g.drawImage(t.image,(int)(x0 * tileSize - (tilePos.getX()*tileSize)%tileSize),(int)(y0 * tileSize - (tilePos.getY()*tileSize)%tileSize),tileSize,tileSize,null);
            }
        }
    }
}
