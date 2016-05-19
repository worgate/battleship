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
import tripleplay.game.Screen;


import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class Ciws extends Screen {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private  float diffY , diffX;
    private double angle;
    private Boolean contacted;
    private int contactCheck;
    Body body;
    Body other;
    private float x,y;
    private float yk;
    private float mx,my;


    public enum State{
        IDLE
    }

    private State state = State.IDLE;

    private  int e = 0;
    private int offset = 8;


    public Ciws(final World world,final float x , final float y, final HashMap<Body,String> bodies ) {
        this.x = x;
        this.y = y;




        sprite = SpriteLoader.getSprite("images/ciws.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);

                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setSize(500,500);
                sprite.layer().setScale(0.2f,0.2f);
                sprite.layer().setTranslation(x, y );
                body = initPhysicsBody(world,
                        Play1.M_PER_PIXEL * x,
                        Play1.M_PER_PIXEL * y);
                hasLoaded = true;
                bodies.put(body,"CIWS");
            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });


        PlayN.mouse().setListener(new Mouse.Adapter(){


            @Override
            public void onMouseMove(Mouse.MotionEvent event) {
                mx = event.x() ;
                my = event.y() ;
                layerAngleUpdate(event.x() , event.y());
            }

            private void layerAngleUpdate(float x1, float y1) {
                diffY = Math.abs(x1 - x) ;
                diffX = Math.abs(y1 - y);
                angle = Math.toDegrees(Math.atan(diffY / diffX));
                if (angle < 38f){
                    angle = 38f;
                }else if (angle > 86f){
                    angle = 86f;
                }

                //System.out.println("X :" + x1 + "   Y :" + y1 + "Degree " + angle);

            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.DYNAMIC;

                bodyDef.position = new Vec2( (x) / 26.666667f , (y) / 26.666667f);
                Body body1 = world.createBody(bodyDef);

                CircleShape shape = new CircleShape();
                shape.setRadius(0.05f);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.4f;
                fixtureDef.friction = 0.1f;
                fixtureDef.restitution = 0.8f;
                body1.createFixture(fixtureDef);
                body1.setLinearDamping(0.2f);


                bodies.put(body1,"bullet");

                if(angle < 38){
                    angle = 38f;
                }
                yk = (mx) * (float) Math.tan( Math.toRadians(angle)) ;
                if(event.x() < 300){
                    body1.applyLinearImpulse( new Vec2(  yk+30f,mx)  , body.getPosition() );
                    Play1.shootOut();
                }else{
                    body1.applyLinearImpulse( new Vec2(  yk,mx)  , body.getPosition() );
                }


              //  body.applyLinearImpulse( new Vec2(50f , 80f)  , body.getPosition() );

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
        /*shape.setAsBox(
                (sprite.layer().height() +50) * Play1.M_PER_PIXEL   /12 , (45) * Play1.M_PER_PIXEL );*/
        shape.setAsBox(      3 * Play1.M_PER_PIXEL , 3* Play1.M_PER_PIXEL );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;/*
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;*/
        body.createFixture(fixtureDef);

        /*body.setLinearDamping(0.2f);*/
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
            }
            spriteIndex = offset + ((spriteIndex +1) % 2);
            sprite.setSprite(spriteIndex);
            e = 0;
        }



    }



    public void paint(Clock clock) {
        if (!hasLoaded){ return;}

        sprite.layer().setTranslation( body.getPosition().x / Play1.M_PER_PIXEL  ,
                body.getPosition().y / Play1.M_PER_PIXEL );

        sprite.layer().setRotation(((float) angle / 30f)*0.9f - 2.5f);
        //sprite.layer().setRotation(( (float) angle / 30f) - 2.5f);

    }

    public String getInfo(){
        return String.valueOf(layer().rotation());

    }
    public Body getBody(){
        return this.body;
    }

    public float getDiffY(){
        return diffY;
    }



}
