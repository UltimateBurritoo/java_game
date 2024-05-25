public class EntityAttributes {
    float maxHP;
    float speed;
    float attack;
    float knockback;

    public EntityAttributes(float maxHP, float speed, float attack) {
        this.maxHP = maxHP;
        this.speed = speed;
        this.attack = attack;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(float maxHP) {
        this.maxHP = maxHP;
    }

    public float getKnockback() {
        return knockback;
    }

    public void setKnockback(float knockback) {
        this.knockback = knockback;
    }
}
