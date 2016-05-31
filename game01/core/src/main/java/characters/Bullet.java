package characters;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;
import sut.game01.core.Play1;

import java.util.ArrayList;
import java.util.HashMap;


public class Bullet {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    Body body;

    public enum State{
        IDLE
    }

    private State state = State.IDLE;

    private  int e = 0;
    private int offset = 8;
    private float yk,mx = 0;
    public Bullet(final World world,final float x , final float y,float yk,float mx,final HashMap<Body,String> bodies){
        this.yk = yk;
        this.mx = mx;
        sprite = SpriteLoader.getSprite("images/bullet.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setScale(0.1f);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                //sprite.layer().setSize(10,10);
                sprite.layer().setTranslation(x, y );
                body = initPhysicsBody(world,
                        Play1.M_PER_PIXEL * x,
                        Play1.M_PER_PIXEL * y);
                hasLoaded = true;
                bodies.put(body,"Bullet");
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

        bodyDef.position = new Vec2( (x), (y));
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.05f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.8f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.applyLinearImpulse( new Vec2(  (yk+30f),mx)  , body.getPosition() );
        return  body;
    }

    public void update(int delta){
        // System.out.println("value = " + (((float) angle / 30f) - 2.8f )) ;
        if(hasLoaded == false) return;

        e = e + delta;
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; break;

            }
            spriteIndex = offset + ((spriteIndex +1) % 1);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
        if (body.isActive() == false){
            sprite.layer().setVisible(false);
        }


    }

    public void paint(Clock clock) {
        if (!hasLoaded){ return;}

        sprite.layer().setTranslation(
                body.getPosition().x / Play1.M_PER_PIXEL  ,
                body.getPosition().y / Play1.M_PER_PIXEL );

    }
    public Body getBody(){
        return this.body;
    }






}
