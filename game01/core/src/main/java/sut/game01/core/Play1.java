package sut.game01.core;
import characters.Bullet;
import characters.Ciws;
import characters.Mario;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Timer;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import playn.core.util.TextBlock;
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
    //private  Airplane airplane;


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
    private float x,y;
    private float yk;
    private float mx,my;
    float angle;
    private  float diffY , diffX;
    float ciwsX = 70f,ciwsY = 420f;

    int shoot = 0;
    int ammo = 50, maga= 50;




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
        //Contact
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                System.out.println("A = " + bodies.get(a) + a.getPosition());
                System.out.println("B = " + bodies.get(b) + b.getPosition() );


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


        ciws = new Ciws(world,ciwsX,ciwsY,bodies);
        //Mouse Move
        mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseMove(Mouse.MotionEvent event) {

                mx = event.x() ;
                my = event.y() ;

                diffY = Math.abs(mx - ciwsX) ;
                diffX = Math.abs(my - ciwsY);
                angle = (float) Math.toDegrees(Math.atan(diffY / diffX));
                if (angle < 38f){
                    angle = 38f;
                }else if (angle > 86f){
                    angle = 86f;
                }
               // System.out.println("MX : " + mx + " MY : " + my + "Angle : " + angle);

                ciws.layerAngleUpdate(event.x() , event.y() ,angle );
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                if(angle < 38){ angle = 38;  };
                yk = mx * (float) Math.tan(Math.toRadians(angle));

                if (ammo >0){
                    ammo = ammo - 1;
                    if (mx < 300){
                        shootOut(yk+30f , mx ,world,x,y);
                    }else {
                        shootOut(yk , mx ,world,x,y);
                    }
                }

            }
        });

        keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.K){
                    ammo = maga;
                }
            }
        });





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

            debugDraw.setCamera(0, 0, 1f / Play1.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }




    }
    @Override
    public void update(int delta){
        super.update(delta);
        world.step(0.033f,10,10);
        ciws.update(delta);
        for (int o = 0 ; o < bullets.size() ; o++){
            bullets.get(o).update(delta);
        }


    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        ciws.paint(clock);

        for (int o = 0 ; o < bullets.size() ; o++){
            bullets.get(o).paint(clock);
        }

        if (showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(String.valueOf(ammo) + " / " + String.valueOf(maga),100f,100f);
            if (ammo == 0 ){
                    debugDraw.getCanvas().drawText("Out of ammo",100f,120f);
            }

        }

    }
    public  void shootOut(float yk, float mx, final World world, float x, float y) {
        bullets.add(new Bullet(world, ciwsX, ciwsY, yk, mx,this.bodies));

        for (int o = 0 ; o < bullets.size() ; o++){
                this.layer.add(bullets.get(o).layer());
                bodies.put(bullets.get(o),"Bullet");
        }
        //bullets.add(new Bullet(world, ciwsX, ciwsY, yk, mx,bodies));
       // bodies.put(new Bullet(world, ciwsX, ciwsY, yk, mx),"bullet");



    }



}
