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


public class Airplane {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private  float diffY , diffX;
    private double angle;
    private Boolean contacted;
    private int contactCheck;
    Body body;
    Body other;


    public enum State{
        IDLE , FIRE , DESTROY
    }

    private State state = State.IDLE;

    private  int e = 0;
    private int offset = 8;

    public Airplane(final World world,final float x , final float y){
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



        sprite = SpriteLoader.getSprite("images/airplane.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);

                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setSize(312,256);
                sprite.layer().setScale(0.5f,0.5f);
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


        PlayN.mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseMove(Mouse.MotionEvent event) {
                layerAngleUpdate(event.x() , event.y());
            }

            private void layerAngleUpdate(float x1, float y1) {
                diffY = Math.abs(x1 - x) ;
                diffX = Math.abs(y1 - y);
                angle = Math.toDegrees(Math.atan(diffY / diffX));
                if (angle < 54f){
                    angle = 54f;
                }else if (angle > 84f){
                    angle = 84f;
                }
               // System.out.println("X :" + x1 + "   Y :" + y1 + "Degree " + angle);

               // System.out.println(angle);
            }
        });



    }

    public Layer layer(){


        return sprite.layer();
    }
    private Body initPhysicsBody(World world,float x , float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                (sprite.layer().height() +50) * Play1.M_PER_PIXEL   /4 , (45) * Play1.M_PER_PIXEL );
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
       // System.out.println("value = " + (((float) angle / 30f) - 2.8f )) ;
        if(hasLoaded == false) return;

        e = e + delta;
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; break;
                case FIRE: offset = 4; break;
                case DESTROY: offset = 8; break;
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

        sprite.layer().setRotation(( (float) angle / 30f) - 2.9f);

    }

    public String getInfo(){
        return String.valueOf(layer().rotation());

    }




}
