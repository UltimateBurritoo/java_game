public class BomberEntity extends LivingEntity {
    EntityBase target;
    Vector2 targetPosition;
    float retargetTimer = 0;
    float explosionTimer = 0;
    public BomberEntity(Vector2 position)
    {
        super(position,new Vector2(12,12),new SpritesheetRenderer("bomber",4,1,new SpriteAnimation[]{
                new SpriteAnimation(0,1,6,true),
                new SpriteAnimation(2,3,12,true)
        }),new EntityAttributes(150,125,25));
        friction = 0;
    }

    public void tick(float dt) {
        target = Game.getWindow().getPlayer();
        boolean inRange = target.getPosition().distance(getPosition()) < 300;
        boolean inExplosionRange = target.getPosition().distance(getPosition()) < 64;
        retargetTimer-=dt;
        if (retargetTimer<0)
        {
            retargetTimer = 0.5f;
            if(inRange) targetPosition = target.getPosition().added((float)(Math.random()*80-40),(float)(Math.random()*80-40));
            else targetPosition = getPosition().added((float)(Math.random()*40-20),(float)(Math.random()*40-20));
        }
        if (inExplosionRange)
        {
            explosionTimer += dt;
            Vector2 movement = targetPosition.subtracted(getPosition()).normalized().multiplied(getAttributes().getSpeed()*2f);
            if(!inRange) movement.set(new Vector2());
            setVelocity(getVelocity().movedTowards(movement,dt*200));
            spritesheet.playAnimation(1);
            if (explosionTimer > 0.5f)
            {
                ExplosionEffect explosion = new ExplosionEffect(getPosition());
                GameWindow.queueSpawn(explosion);
                kill();
            }
        }
        else
        {
            spritesheet.playAnimation(0);
            explosionTimer -= dt*0.5f;
            explosionTimer = Math.max(explosionTimer,0);
            Vector2 movement = targetPosition.subtracted(getPosition()).normalized().multiplied(getAttributes().getSpeed());
            if(!inRange) movement.set(new Vector2());
            setVelocity(getVelocity().movedTowards(movement,dt*400));
            spritesheet.setFlipped(movement.getX() > 0);
        }


        super.tick(dt);
    }
    public void knockback(Vector2 kb)
    {
        setVelocity(getVelocity().added(kb.multiplied(0.25f)));
    }
}
