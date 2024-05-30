import java.lang.reflect.Array;
import java.util.ArrayList;

public class DungeonBuilder {
    int roomSize = 20;
    int maxRoomCount = 5;
    int maxDungeonSize = 5;
    int floorTile = 0;
    int wallTile = 1;
    int insideTile = 2;
    int wallThickness = 3;
    int hallLength = 11;
    int hallWidth = 4;
    boolean[][] placedRooms;
    public void setTileTypes(int floor, int wall, int inside)
    {
        floorTile = floor;
        wallTile = wall;
        insideTile = inside;
    }
    public void build(Tilemap t)
    {
        if(GameWindow.currentLevel % 3 == 0)
        {
            floorTile = 0;
            wallTile = 1;
            insideTile = 2;
        } else if (GameWindow.currentLevel % 3 == 1) {
            floorTile = 3;
            wallTile = 4;
            insideTile = 5;
        }
        else
        {
            floorTile = 6;
            wallTile = 7;
            insideTile = 8;
        }
        placedRooms = new boolean[maxDungeonSize][maxDungeonSize];
        recursiveBuild(0,0,t,0,0);
    }
    public void recursiveBuild(int x, int y, Tilemap t,int direction, int index)
    {
        buildRoom(x,y,t,direction,index == 0);
        ArrayList<Integer> directions = new ArrayList<>();
        if(insideDungeon(x,y-1) && !placedRooms[x][y-1]) directions.add(1);
        if(insideDungeon(x,y+1) && !placedRooms[x][y+1]) directions.add(2);
        if(insideDungeon(x-1,y) && !placedRooms[x-1][y]) directions.add(3);
        if(insideDungeon(x+1,y) && !placedRooms[x+1][y]) directions.add(4);
        if(directions.size() > 0)
        {
            int selectedDirection = directions.get((int)(Math.random() * directions.size()));
            cutHallway(x,y,t,selectedDirection);
            detail(x,y,t);
            if (selectedDirection==1)recursiveBuild(x,y-1,t,selectedDirection, index+1);
            else if (selectedDirection==2)recursiveBuild(x,y+1,t,selectedDirection,index+1);
            else if (selectedDirection==3)recursiveBuild(x-1,y,t,selectedDirection,index+1);
            else if (selectedDirection==4)recursiveBuild(x+1,y,t,selectedDirection,index+1);
        }
        else
        {
            // final room
            detail(x,y,t);
            GameWindow.queueSpawn(new ExitEntity(new Vector2((x+0.5f)*roomSize*Tilemap.tileSize,(y+0.5f)*roomSize*Tilemap.tileSize)));
            return;
        }
    }
    boolean insideDungeon(int x, int y)
    {
        return x >= 0 && y >= 0 && x < maxDungeonSize && y < maxDungeonSize;
    }
    public void cutHallway(int x, int y, Tilemap t, int direction)
    {
        if (direction == 1)
        {
            for (int d = 0; d < hallLength; d++)
            {
                for (int w = -hallWidth/2; w < hallWidth/2; w++)
                {
                    t.setTile(x*roomSize + roomSize/2 + w,y*roomSize + d,floorTile);
                }
            }
        } else if (direction == 2) {
            for (int d = 0; d < hallLength; d++)
            {
                for (int w = -hallWidth/2; w < hallWidth/2; w++)
                {
                    t.setTile(x*roomSize + roomSize/2 + w,y*roomSize + roomSize - 1 - d,floorTile);
                }
            }
        }else if (direction == 3) {
            for (int d = 0; d < hallLength; d++) {
                for (int w = -hallWidth / 2; w < hallWidth / 2; w++) {
                    t.setTile(x * roomSize + d, y * roomSize + roomSize / 2 + w, floorTile);
                }
            }
        }else if (direction == 4) {
            for (int d = 0; d < hallLength; d++)
            {
                for (int w = -hallWidth/2; w < hallWidth/2; w++)
                {
                    t.setTile(x*roomSize + roomSize - 1 - d,y*roomSize + roomSize/2 + w,floorTile);
                }
            }
        }

    }
    // 0 = no direction
    // 1 = up
    // 2 = down
    // 3 = left
    // 4 = right
    public void buildRoom(int x, int y, Tilemap t, int startDirection,boolean forceHallway)
    {
        if(!insideDungeon(x,y))return;
        boolean narrowRoom = Math.random() < 0.25 || forceHallway;
        placedRooms[x][y] = true;
        for(int x0 = 0; x0 < roomSize; x0++)
        {
            for (int y0 = 0; y0 < roomSize; y0++) {
                if(x0 >= wallThickness && y0 >= wallThickness && x0 < roomSize-wallThickness && y0 < roomSize-wallThickness && !narrowRoom)
                {
                    t.setTile(x*roomSize+x0,y*roomSize+y0,floorTile);
                }
                else
                {
                    t.setTile(x*roomSize+x0,y*roomSize+y0,insideTile);
                }
            }
        }

        cutHallway(x,y,t,startDirection+(startDirection%2)*2-1);
        if(!narrowRoom)
        {
            int spawnCount = (int)(Math.random()*3+2 + GameWindow.currentLevel);
            GameWindow.queueSpawn(new SpawningEntity(new Vector2((x+0.5f)*roomSize*Tilemap.tileSize,(y+0.5f)*roomSize*Tilemap.tileSize),spawnCount));
        }
    }
    public void detail(int x, int y, Tilemap t)
    {
        for (int x0 = 0; x0 < roomSize; x0++) {
            for (int y0 = 0; y0 < roomSize; y0++) {
                if(t.getTile(x*roomSize+x0,y*roomSize+y0) == Tile.tileset[insideTile] && t.getTile(x*roomSize+x0,y*roomSize+y0+1) == Tile.tileset[floorTile]) t.setTile(x*roomSize+x0,y*roomSize+y0,wallTile);
            }
        }
    }
}
