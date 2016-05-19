package sut.game01.core;
import characters.Bullet;
import characters.Ciws;
import characters.Mario;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import characters.Airplane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static playn.core.PlayN.*;

public class Play1 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    private  Airplane airplane;


    public static float M_PER_PIXEL = 1 / 26.6666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;
    private int count = -1;
    private int z;

    BodyDef bodyDef;
    //Airplane air;

    private static HashMap bodies = new HashMap<Body,String>();
    private int i = 0;
    private String debugString = "Hello";
    private  int score = 0 ;

    private  Mario mario;
    private Ciws ciws;

    Bullet bullet;

    Image playbg;
    ImageLayer bglayer;

    Image ship;
    ImageLayer shipL;

    Image sky;
    ImageLayer skyL;
    private  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public Play1(final ScreenStack ss) {
        this.ss = ss;
        this.layer.clear();


        ///==============================WORLD SETUP ========================///
        final Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);


        playbg = assets().getImage("/images/sea_bg.png");
        bglayer = graphics().createImageLayer(playbg);

        sky = assets().getImage("/images/sky.png");
        skyL = graphics().createImageLayer(sky);



        ship = assets().getImage("/images/char/ship.png");
        shipL = graphics().createImageLayer(ship);
        shipL.setScale(0.9f);
        shipL.setTranslation(-250f,350f);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                System.out.println("A = " + bodies.get(a) + a.getPosition());
                 System.out.println("B = " + b.toString() + b.getPosition() );


                /*else if (bodies.get(b) == "bullet"){
                    System.out.println("Hit!!!");
                    b.setActive(false);
                    airplane.layer().setVisible(false);

                }*/






            }

            @Override
            public void endContact(Contact contact) {         }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });


        airplane = new Airplane(world,400f,20f);
        this.layer.add(airplane.layer());


        Image backImage = assets().getImage("Images/back.png");
        backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(20,50);

        





        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.remove(ss.top());
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss));



            }
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();
        ciws = new Ciws(world,70f,420f,bodies,bullets);
        this.layer.add(skyL);
        this.layer.add(ciws.layer());
        this.layer.add(backButton);
        this.layer.add(shipL);
        this.layer.add(bglayer);



        if (showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / Play1.M_PER_PIXEL),
                    (int) (height / Play1.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / Play1.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }




    }
    @Override
    public void update(int delta){
        super.update(delta);
        world.step(0.033f,10,10);
        airplane.update(delta);
       //air.update(delta);
        ciws.update(delta);
        for (int i = 0 ; i < bullets.size() ; i++){
            this.layer.add(bullets.get(i).layer());
            bullets.get(i).update(delta);
        }

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        airplane.paint(clock);
        ciws.paint(clock);
        for (int i = 0 ; i < bullets.size() ; i++){
            bullets.get(i).paint(clock);
        }

        if (showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().drawText( debugString,100f,100f);
        }

    }
    public static void shootOut(float yk, float mx, final World world, float x, float y,final ArrayList bullets){


        bullets.add(new Bullet(world, x, y, yk, mx, bullets));


       // bullets.add(new Bullet(world,x,y,yk,mx));



        /*

        //System.out.println("yto");
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
        body1.applyLinearImpulse( new Vec2(  yk+30f,mx)  , body1.getPosition() );
        */



        //bodies.put(body1,"bullet");



    }



}
