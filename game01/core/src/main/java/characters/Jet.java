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

import java.util.HashMap;


public class Jet {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Boolean contacted;
    private int contactCheck;
    public Body body;

    public enum State{
        MAX , MID , MIN,DESTROY
    }

    private State state = State.MAX;
    private  int e = 0;
    private int offset = 8;

    private  float health=100 ;
    int healthMax=100;
    float percent=100;
    float x,y;
    float xChange =200,yChange=200;
    public Jet(final World world,final float x , final float y,final HashMap<Body,String> bodies,int health){
        this.health = health;
        this.healthMax = health;
        percent = (this.health / healthMax) * 100;
        //System.out.println(percent);
        this.x = x;
        this.y = y;
        sprite = SpriteLoader.getSprite("images/jet.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setSize(600/2,200/2);
                sprite.layer().setScale(0.2f,0.2f);
                sprite.layer().setTranslation(x, y );
                body = initPhysicsBody(world,
                        Play1.M_PER_PIXEL * x,
                        Play1.M_PER_PIXEL * y);
                hasLoaded = true;
                bodies.put(body,"Jet");
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
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        //shape.setAsBox( 3 , 5 );
        shape.setAsBox( (sprite.layer().width()) * Play1.M_PER_PIXEL   /9 , (10) * Play1.M_PER_PIXEL );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);
        body.setGravityScale(0f);
       // body.setBullet(false);
        //body.setLinearDamping(0f);
        body.setTransform(new Vec2(x, y), 0f);
        body.applyForce(new Vec2(-40f, 0), body.getPosition());
        return  body;
    }

    public void update(int delta){
        // System.out.println("value = " + (((float) angle / 30f) - 2.8f )) ;
        if(hasLoaded == false) return;

        percent = (health / healthMax) * 100;
        if (percent > 80){
            state = State.MAX;
        }else if (percent > 60){
            state = State.MID;
        }else  if (percent > 20){
            state = State.MIN;
        }else {
            state =State.DESTROY;
        }
        switch (state){
            case MAX:
                sprite.setSprite(0); break;
            case MID:
                sprite.setSprite(1); break;
            case MIN:
               // body.setGravityScale(1f);
                sprite.setSprite(2); break;
            case DESTROY:
                body.setActive(false);
                sprite.layer().setVisible(false);
                sprite.setSprite(3); break;
        }



        if (get__X() <= 0){
           kill();
        }else if(get__Y() <= 0){
            kill();
        }

        /*if ((int)(body.getPosition().x / Play1.M_PER_PIXEL ) == 120){
            System.out.println("drop the bomb");
        }*/



    }
    public int get__X(){
        return ((int)xChange) ;
    }
    public int get__Y(){

        return ((int)yChange) ;
    }

    public int getAttack(float force){
        health = health - force;
        System.out.println("force :" + force + " health : "+ health + "healthMax :" + healthMax);
        //System.out.println(health/healthMax);
        //System.out.println(90%100);
        percent = ((health % healthMax));
        //System.out.println("percent : " + percent + " health : " + health);
        //if (percent <=0 ){ percent = 0;}
        if (percent <= 0){
            return 2;
        }else return  0;

    }

    public void paint(Clock clock) {
        if (!hasLoaded){ return;}
        xChange = ( (body.getPosition().x ) / Play1.M_PER_PIXEL);
        yChange = ( (body.getPosition().y ) / Play1.M_PER_PIXEL);
        sprite.layer().setTranslation(
                (body.getPosition().x +1.2f ) / Play1.M_PER_PIXEL  ,
                (body.getPosition().y + 0.5f)  / Play1.M_PER_PIXEL );
    }
    public void kill(){
        body.setActive(false);
        layer().setVisible(false);
    }




}
