public class PoofEffect extends EffectEntity{
    public PoofEffect(Vector2 position)
    {
        super(position,new SpritesheetRenderer("poof",5,1,new SpriteAnimation[]
                {
                        new SpriteAnimation(0,4,12,false)
                }),0.4166f);
    }
}
