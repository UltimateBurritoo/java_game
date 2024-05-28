public class ZombieEntity extends LivingEntity {
    EntityBase target;
    Vector2 targetPosition;
    float retargetTimer = 0;
    float lungeTimer = 2;
    float timerRandomize;
    public ZombieEntity(Vector2 position)
    {
        super(position,new Vector2(12,12),new SpritesheetRenderer("zombie",6,1,new SpriteAnimation[]{
                new SpriteAnimation(0,3,12,true),
                new SpriteAnimation(4,5,12,true)
        }),new EntityAttributes(70,75,10));
        friction = 0;
        timerRandomize = (float)Math.random();
        lungeTimer = timerRandomize * 4;
        System.out.println(position);
        getAttributes().setKnockback(100);
    }

    public void tick(float dt) {
        target = Game.getWindow().getPlayer();
        boolean inRange = target.getPosition().distance(getPosition()) < 300;
        boolean inLungeRange = target.getPosition().distance(getPosition()) < 128;
        retargetTimer-=dt;
        if (retargetTimer<0)
        {
            retargetTimer = 1f;
            if(inRange) targetPosition = target.getPosition().added((float)(Math.random()*40-20),(float)(Math.random()*40-20));
            else targetPosition = getPosition().added((float)(Math.random()*40-20),(float)(Math.random()*40-20));

        }
        lungeTimer -= dt;
        if (inLungeRange)
        {
            if(lungeTimer < 0)
            {
                setVelocity(getVelocity().added(target.getPosition().subtracted(getPosition()).normalized().multiplied(getAttributes().getSpeed()*2.5f)));
                timerRandomize = (float)Math.random();
                lungeTimer = 4 * timerRandomize;
            }
        }
        if (lungeTimer > 4 * timerRandomize - 0.5)
        {
            // lunging
            setVelocity(getVelocity().movedTowards(new Vector2(0,0),dt*300));
            spritesheet.playAnimation(1);
            checkEnemyMelee(getAttributes().getAttack()*1.2f,getAttributes().getKnockback()*2.0f);
        }
        else {
            spritesheet.playAnimation(0);
            Vector2 movement = targetPosition.subtracted(getPosition()).normalized().multiplied(getAttributes().getSpeed());
            if(!inRange) movement.set(Vector2.zero);
            setVelocity(getVelocity().movedTowards(movement,dt*400));
            spritesheet.setFlipped(movement.getX() > 0);
            checkEnemyMelee(getAttributes().getAttack(),getAttributes().getKnockback());
        }

        super.tick(dt);
    }
}
