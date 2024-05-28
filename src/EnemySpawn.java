public class EnemySpawn {
    public enum EntityType
    {
        ZOMBIE,
        GHOST
    }
    public static void spawn(EntityType entityType, Vector2 position)
    {
        switch (entityType)
        {
            case ZOMBIE -> GameWindow.queueSpawn(new ZombieEntity(position));
            case GHOST -> GameWindow.queueSpawn(new GhostEntity(position));
        }
        GameWindow.queueSpawn(new PoofEffect(position));
    }
    public static float getSpawnWeight(EntityType entityType, int level)
    {
        switch (entityType)
        {
            case ZOMBIE: return Math.max(0,15-level*1.5f);
            case GHOST: return level * 0.9f + 4f;
        }
        return 0;
    }
    public static float getTotalWeight(int level)
    {
        float t = 0;
        for(EntityType type : EntityType.values())
        {
            t += getSpawnWeight(type,level);
        }
        return t;
    }
    public static EntityType rollForWeight(int level, float totalWeight)
    {
        float s = (float)Math.random() * totalWeight;
        for (EntityType type : EntityType.values())
        {
            totalWeight -= getSpawnWeight(type,level);
            if(s >= totalWeight) return type;
        }
        return EntityType.ZOMBIE;
    }
    public static void spawnGroup(int level, int count, Vector2 center, float distance)
    {
        float totalWeight = getTotalWeight(level);
        for (int i = 0; i < count; i++) {
            spawn(rollForWeight(level,totalWeight),
                    // comically long vector math to decide where to spawn the enemy
                    center.added(new Vector2((float)Math.random()*2-1,(float)Math.random()*2-1).multiplied(distance)));
        }
        if(Math.random() < 0.25f)
        {
            GameWindow.queueSpawn(new ChestEntity(center));
        }
    }
}
