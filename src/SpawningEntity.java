public class SpawningEntity extends EntityBase{
    int spawnCount;
    float spawnDistance = 160;
    float spawnRadius = 80;
    public SpawningEntity(Vector2 position, int spawnCount)
    {
        super(position, new TestRenderer(0x000000,0,0));
        this.spawnCount = spawnCount;
    }
    public SpawningEntity(Vector2 position, int spawnCount, float spawnDistance)
    {
        super(position, new TestRenderer(0x000000,0,0));
        this.spawnCount = spawnCount;
        this.spawnDistance = spawnDistance;
    }

    public int getSpawnCount() {
        return spawnCount;
    }
    public void tick(float dt)
    {
        float dist = Game.getWindow().getPlayer().getPosition().distance(getPosition());
        if(dist < spawnDistance)
        {
            EnemySpawn.spawnGroup(GameWindow.currentLevel,spawnCount,getPosition(),spawnRadius);
            kill();
        }
    }

}
