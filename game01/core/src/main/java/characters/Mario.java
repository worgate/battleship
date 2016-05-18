package characters;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;
import sut.game01.core.Play1;


public class Mario {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    private Boolean contacted;
    private int contactCheck;
    Body body;
    Body other;

    public enum Direction{
        LEFT, RIGHT
    };
    private Direction direction = Direction.RIGHT;

    public enum State{
        IDLE
    }

    private State state = State.IDLE;

    private  int e = 0;
    private int offset = 8;

    public Mario(final World world,final float x , final float y){
       /* PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.SPACE){
                    switch (state){
                        case IDLE: state = State.FIRE; break;
                        case FIRE: state = State.DESTROY; break;
                        case DESTROY: state = State.IDLE; break;
                    }
                }
            }
        });*/



        sprite = SpriteLoader.getSprite("images/mario.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
               // sprite.layer().setSize(312,256);
               // sprite.layer().setScale(0.5f,0.5f);
                sprite.layer().setTranslation(x, y );
                body = initPhysicsBody(world,
                        Play1.M_PER_PIXEL * x,
                        Play1.M_PER_PIXEL * y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });


    }

    public Layer layer(){




        return sprite.layer();
    }
    private Body initPhysicsBody(World world,float x , float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(x,y);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                (sprite.layer().height() +20) * Play1.M_PER_PIXEL   /4 , (55) * Play1.M_PER_PIXEL );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return  body;
    }

    public void update(int delta){

        if(hasLoaded == false) return;

        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key() == Key.A) {
                    direction = Direction.LEFT;
                    body.applyLinearImpulse(new Vec2(-20f,0), body.getPosition());
                }
                if(event.key() == Key.D) {
                    direction = Direction.RIGHT;
                    body.applyLinearImpulse(new Vec2(20f,0), body.getPosition());
                }
                if(event.key() == Key.SPACE) {
                    //direction = Direction.RIGHT;
                    if(direction == Direction.RIGHT)
                        body.applyLinearImpulse(new Vec2(10f,-60f), body.getPosition());
                    else
                        body.applyLinearImpulse(new Vec2(-10f,-60f), body.getPosition());
                }
            }
        });
        //-----


        e = e + delta;
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; break;

            }
            spriteIndex = offset + ((spriteIndex +1) % 4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }

    }

    public void paint(Clock clock) {
        if (!hasLoaded){ return;}

        sprite.layer().setTranslation(
                body.getPosition().x / Play1.M_PER_PIXEL  ,
                body.getPosition().y / Play1.M_PER_PIXEL );
        sprite.layer().setRotation(body.getAngle());
    }
    public Body getBody(){
        return this.body;
    }
}
