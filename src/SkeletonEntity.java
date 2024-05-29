public class SkeletonEntity extends LivingEntity {
    EntityBase target;
    Vector2 targetPosition;
    float retargetTimer = 0;
    public SkeletonEntity(Vector2 position)
    {
        super(position,new Vector2(12,12),new SpritesheetRenderer("skeleton",4,1,new SpriteAnimation[]{
                new SpriteAnimation(0,3,12,true)
        }),new EntityAttributes(150,50,25));
        friction = 0;
        System.out.println(position);
        getAttributes().setKnockback(200);
    }

    public void tick(float dt) {
        target = Game.getWindow().getPlayer();
        boolean inRange = target.getPosition().distance(getPosition()) < 300;
        retargetTimer-=dt;
        if (retargetTimer<0)
        {
            retargetTimer = 0.5f;
            if(inRange) targetPosition = target.getPosition().added((float)(Math.random()*40-20),(float)(Math.random()*40-20));
            else targetPosition = getPosition().added((float)(Math.random()*40-20),(float)(Math.random()*40-20));

        }
        Vector2 movement = targetPosition.subtracted(getPosition()).normalized().multiplied(getAttributes().getSpeed());
        if(!inRange) movement.set(new Vector2());
        setVelocity(getVelocity().movedTowards(movement,dt*400));
        spritesheet.setFlipped(movement.getX() > 0);
        checkEnemyMelee(getAttributes().getAttack(),getAttributes().getKnockback());

        super.tick(dt);
    }
}
