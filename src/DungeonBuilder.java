public class DungeonBuilder {
    int roomSize = 20;
    int maxRoomCount = 5;
    int maxDungeonSize = 4;
    int floorTile = 0;
    int wallTile = 1;
    int insideTile = 2;

    public void setTileTypes(int floor, int wall, int inside)
    {
        floorTile = floor;
        wallTile = wall;
        insideTile = inside;
    }
    public void build(Tilemap t)
    {
        buildPropagation(0,0,t,(byte)0);
    }
    public void buildPropagation(int x, int y, Tilemap t, byte direction)
    {
        byte neighbors = buildRoom(x,y,t,direction);
        if((neighbors & 1) > 0) buildPropagation(x,y-1,t,(byte)2);
        if((neighbors & 2) > 0) buildPropagation(x,y+1,t,(byte)1);
        if((neighbors & 4) > 0) buildPropagation(x-1,y,t,(byte)8);
        if((neighbors & 8) > 0) buildPropagation(x+1,y,t,(byte)4);
    }
    public byte buildRoom(int x, int y, Tilemap t, byte direction)
    {
        byte placeableRooms = 0;
        int totalRooms = 0;
        if(t.getTile(x*roomSize,y*roomSize-1) == null && t.isInside(x*roomSize,y*roomSize-1) && (Math.random() < 0.5 && totalRooms < 3)) placeableRooms += 1; // top
        if(t.getTile(x*roomSize,y*roomSize+roomSize+1) == null && t.isInside(x*roomSize,y*roomSize+roomSize) && (Math.random() < 0.5 && totalRooms < 3)) placeableRooms += 2; // bottom
        if(t.getTile(x*roomSize-1,y*roomSize) == null && t.isInside(x*roomSize-1,y*roomSize) && (Math.random() < 0.5 && totalRooms < 3)) placeableRooms += 4; // left
        if(t.getTile(x*roomSize+roomSize+1,y*roomSize) == null && t.isInside(x*roomSize+roomSize,y*roomSize) && (Math.random() < 0.5 && totalRooms < 3)) placeableRooms += 8; // right
        placeableRooms |= direction;
        for(int x0 = x*roomSize; x0 <= x*roomSize+roomSize; x0++)
        {
            for (int y0 = y *roomSize; y0 <= y*roomSize+roomSize; y0++) {
                if(x0 == x*roomSize || x0 == x*roomSize+roomSize || y0 == y*roomSize || y0 == y*roomSize+roomSize)
                {
                    t.setTile(x0,y0,insideTile);
                }
                else if(x0 == x*roomSize || x0 == x*roomSize+roomSize || (y0-1) == y*roomSize || (y0-1) == y*roomSize+roomSize)
                {
                    t.setTile(x0,y0,wallTile);
                }
                else
                {
                    t.setTile(x0,y0,floorTile);
                }
            }
        }
        if((placeableRooms & 1) > 0)
        {
            for(int x1 = roomSize/2-2; x1 < roomSize/2+2; x1++)
            {
                t.setTile(x*roomSize+x1,y*roomSize,floorTile);
                t.setTile(x*roomSize+x1,y*roomSize+1,floorTile);
            }
        }
        if((placeableRooms & 2) > 0)
        {
            for(int x1 = roomSize/2-2; x1 < roomSize/2+2; x1++)
            {
                t.setTile(x*roomSize+x1,y*roomSize+roomSize-1,floorTile);
            }
        }
        if((placeableRooms & 4) > 0)
        {
            for(int y1 = roomSize/2-2; y1 < roomSize/2+2; y1++)
            {
                t.setTile(x*roomSize,y*roomSize+y1,floorTile);
            }
        }
        if((placeableRooms & 8) > 0)
        {
            for(int y1 = roomSize/2-2; y1 < roomSize/2+2; y1++)
            {
                t.setTile(x*roomSize+roomSize-1,y*roomSize+y1,floorTile);
            }
        }
        return (byte)(placeableRooms & ~direction);
    }
}
