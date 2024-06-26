public class EnemySpawn {
    // This class contains methods meant to help with the spawning of enemies using a weighted random system
    public enum EntityType
    {
        ZOMBIE,
        GHOST,
        SKELETON,
        BOMBER
    }
    public static void spawn(EntityType entityType, Vector2 position)
    {
        switch (entityType)
        {
            case ZOMBIE -> GameWindow.queueSpawn(new ZombieEntity(position));
            case GHOST -> GameWindow.queueSpawn(new GhostEntity(position));
            case SKELETON -> GameWindow.queueSpawn(new SkeletonEntity(position));
            case BOMBER -> GameWindow.queueSpawn(new BomberEntity(position));
        }
        GameWindow.queueSpawn(new PoofEffect(position));
    }
    // Equations to get the spawn weight of various enemies per level
    public static float getSpawnWeight(EntityType entityType, int level)
    {
        switch (entityType)
        {
            case ZOMBIE: return Math.max(0,15-level*1.5f);
            case GHOST: return level * 0.9f + 4f;
            case SKELETON: return level * 1.2f + 4f;
            case BOMBER: return  level * 1.4f + 3f;
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
    // Gets an entity type based off of the weights of the current level
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
    // Spawns several enemies around and area
    public static void spawnGroup(int level, int count, Vector2 center, float distance)
    {
        float totalWeight = getTotalWeight(level);
        for (int i = 0; i < count; i++) {
            spawn(rollForWeight(level,totalWeight),
                    // comically long vector math to decide where to spawn the enemy
                    center.added(new Vector2((float)Math.random()*2-1,(float)Math.random()*2-1).multiplied(distance)));
        }
        // 60% chance to spawn a chest
        if(Math.random() < 0.6f)
        {
            GameWindow.queueSpawn(new ChestEntity(center.added(new Vector2((float)Math.random()*2-1,(float)Math.random()*2-1).multiplied(distance))));
        }
    }
}
