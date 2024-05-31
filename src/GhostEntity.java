public class GhostEntity extends LivingEntity{
    EntityBase target;
    float circleTime = 0;
    float circlingSpeed;
    float attackTimer = 0;
    public GhostEntity(Vector2 position)
    {
        super(position,new Vector2(14,14),new SpritesheetRenderer("ghost",6,1,new SpriteAnimation[]{
                new SpriteAnimation(0,3,8, true),
                new SpriteAnimation(4,5, 4, true)
        }), new EntityAttributes(80,150, 14));
        getAttributes().setKnockback(120);
        tilemapCollision = false;
        friction = 0;
        circlingSpeed = (float)Math.random();
        attackRandom = (float)Math.random();
    }
    float attackRandom;

    // Ghost Behavior:
    // - Circle around the player, keeping distance between the player
    // - At random intervals, go into attack mode
    // - During attack mode, move quickly towards the player and deal damage

    public void tick(float dt) {
        target = Game.getWindow().getPlayer();
        spritesheet.setFlipped(target.getPosition().getX() > getPosition().getX());
        boolean inRange = target.getPosition().distance(getPosition()) < 450;
        // attack mode
        if(attackTimer > 5*attackRandom)
        {
            attackTimer += dt;
            spritesheet.playAnimation(1);
            Vector2 targetVelocity = target.getPosition().subtracted(getPosition()).multiplied(attributes.getSpeed()*1.5f);
            setVelocity(getVelocity().movedTowards(targetVelocity,dt*700).cappedMagnitude(attributes.getSpeed()*1.5f));

            checkEnemyMelee(attributes.getAttack(),getAttributes().getKnockback());
            if (attackTimer > 5*attackRandom+2)
            {
                attackRandom = (float)Math.random();
                attackTimer = 0;
            }
        }
        // circle mode
        else if (inRange)
        {
            spritesheet.playAnimation(0);
            circleTime += dt * (Math.random()*0.5f+0.5f) * circlingSpeed;
            attackTimer += dt;
            if(target.getPosition().distance(getPosition()) < 64)
            {
                Vector2 targetVelocity = getPosition().subtracted(target.getPosition()).multiplied(attributes.getSpeed());
                setVelocity(getVelocity().movedTowards(targetVelocity,dt*600).cappedMagnitude(attributes.getSpeed()));
            }
            else
            {
                Vector2 targetPosition = target.getPosition().added((float)Math.cos(circleTime*Math.PI)*160f,(float)Math.sin(circleTime*Math.PI)*160f);
                Vector2 targetVelocity = targetPosition.subtracted(getPosition()).multiplied(attributes.getSpeed());
                setVelocity(getVelocity().movedTowards(targetVelocity,dt*600).cappedMagnitude(attributes.getSpeed()));
            }

        }
        else
        {
            setVelocity(getVelocity().movedTowards(new Vector2(0,0),dt*100));
        }
        super.tick(dt);
    }
}
