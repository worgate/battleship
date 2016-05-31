package sut.game01.core;
import characters.*;
import characters.Profile;
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
import playn.core.Sound;
import java.util.*;

import static playn.core.PlayN.*;

public class Play2 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;

    public static float M_PER_PIXEL = 1 / 26.6666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;
    private int count = -1;
    private int z;

    BodyDef bodyDef;


    private static HashMap bodies = new HashMap<Body,String>();
    private int i = 0;
    private String debugString = "Hello";
    private  int score = 0 ;

    private Ciws ciws;

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
    private int ammo = 30,maga = 30 ;
    int point = 0;
    Jet jet ;
    int mega;
    private  ArrayList<Jet> jetPack = new ArrayList<Jet>();
    private  ArrayList<Bomb> bombPack = new ArrayList<Bomb>();

    int k;
    int latestJet = 0;

    int hp,armor,flare,hpMax,armorMax,flareMax;
    Profile profile ;
    long timeCounter;
    //int countEnemy = 0;
    int MaxEnemy = 5;
    int[]  timeline = {50,200,300,100,400,450,500,650,700,750};
    int[] positionY = {250,220,180,260,300,240,170,320,220,250};
    private  ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    int jetHp = 70;


    public Play2(final ScreenStack ss, final Profile profile) {
        this.profile = profile;
        hp = profile.hpLevel * 100;
        MaxEnemy = timeline.length + 1;

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
        if (profile.skin == 1){
            ship = assets().getImage("/images/char/ship.png");
        }else{
            ship = assets().getImage("/images/char/ship2.png");
        }

        shipL = graphics().createImageLayer(ship);
        shipL.setScale(0.9f);
        shipL.setTranslation(-250f,350f);
        //Contact
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                //System.out.println("A = " + bodies.get(a) + a.getPosition());
                //System.out.println("B = " + bodies.get(b) + b.getPosition() );
                if ( (bodies.get(a) == "Jet" && bodies.get(b) == "Bullet")  ){
                    //System.out.println("Hit");
                    for (int o = 0 ; o < jetPack.size() ; o++){
                        if (jetPack.get(o).body == contact.getFixtureA().getBody()){
                            //System.out.println(profile.powerLevel*50);
                            point = jetPack.get(o).getAttack( (float)(profile.powerLevel*10));
                            profile.money += point;
                            contact.getFixtureB().getBody().setActive(false);
                        }
                        //contact.getFixtureB().getBody().setActive(false);
                    }

                }else if(bodies.get(a) == "CIWS" && bodies.get(b) == "Bomb"){
                    if (armor > 0){
                        armor -= 40;
                        if (armor < 0){ armor = 0;}
                    }else {
                        hp -= 40;
                    }

                    contact.getFixtureB().getBody().setActive(false);
                }
            }
            @Override
            public void endContact(Contact contact) {         }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
        this.hp = hpMax = profile.hpLevel*100;
        this.armor = armorMax  = profile.armorLevel * 100;
        this.flare =  flareMax = profile.flareLevel;

        ciws = new Ciws(world,ciwsX,ciwsY,bodies);
        //Mouse Move
        jetPack.add(new Jet(world,640,200,bodies,jetHp));

        mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseMove(Mouse.MotionEvent event) {

                mx = event.x() ;
                my = event.y() ;

                diffY = Math.abs(mx - ciwsX) ;
                diffX = Math.abs(my - ciwsY);
                angle = (float) Math.toDegrees(Math.atan(diffY / diffX));
                if (angle < 35f){
                    angle = 35f;
                }else if (angle > 86f){
                    angle = 86f;
                }
                // System.out.println("MX : " + mx + " MY : " + my + "Angle : " + angle);

                ciws.layerAngleUpdate(event.x() , event.y() ,angle );
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                if(angle < 35){ angle = 35;  };
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
                if (event.key() == Key.R){
                    if (profile.money >= 10){
                        profile.money -= 10;
                        ammo = maga;
                    }


                } else if (event.key() == Key.F) {
                    if (flare >= 1){
                        flare -= 1;
                        for (int o = 0 ; o < bombPack.size() ; o++){
                            bombPack.get(o).body.setActive(false);
                            bombPack.get(o).sprite.layer().setVisible(false);
                        }
                    }


                }
            }
        });

        Image backImage = assets().getImage("Images/button/stop.png");
        backButton = graphics().createImageLayer(backImage);
        backButton.setScale(0.2f,0.2f);
        backButton.setTranslation(20,50);

        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.remove(ss.top());
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss,profile));
            }
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(skyL);
        this.layer.add(ciws.layer());

        this.layer.add(jetPack.get(0).layer());
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
            //   debugDraw.setFlags( DebugDraw.e_shapeBit|DebugDraw.e_jointBit |DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 1f / Play1.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

    }
    @Override
    public void update(int delta){
        super.update(delta);
        try {
            world.step(0.045f,10,10);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("error");
        }
        ciws.update(delta);
        timeCounter += 1;
        for (i= 0 ; i < timeline.length ; i++){
            if (timeCounter == timeline[i]){
                generateEnemy(positionY[i]);
            }
        }
       // System.out.println("Pack :" +jetPack.size());
       // System.out.println("Max :" +MaxEnemy);
        if (jetPack.size() == MaxEnemy){
            int k=0;

            for (int o = 0 ; o < jetPack.size() ; o++){
                if (jetPack.get(o).layer().visible() == false){
                    k++;
                }
            }
            if (k == MaxEnemy){
                profile.level =3;
                ss.push(new Story3(ss,profile));
            }
         //   System.out.println(k);
        }

        for (int o = 0 ; o < bullets.size() ; o++){
            bullets.get(o).update(delta);
        }

        for (int o = 0 ; o < bombPack.size() ; o++){
            bombPack.get(o).update(delta);
        }
        for (int o = 0 ; o < jetPack.size() ; o++){
            jetPack.get(o).update(delta);

            if (jetPack.get(o).get__X() == 160 || jetPack.get(o).get__X() == 130 || jetPack.get(o).get__X() == 100 ||
                    jetPack.get(o).get__X() == 161 || jetPack.get(o).get__X() == 131 || jetPack.get(o).get__X() == 101){
              //  System.out.println("DROP!!");
                dropTheBomb(jetPack.get(o).get__X(),jetPack.get(o).get__Y());
            }
//            System.out.println(jetPack.get(o).body.getPosition().x);
            // if jetX == 120 drop the bomb
           /* if(jetPack.get(o).body.getPosition().x == 120){
                //dropTheBomb(jetPack.get(o).getX0(),jetPack.get(o).getY0());
                System.out.println("drop the bomb");
            }*/
        }
        if (hp <= 0){
            gameOver();
        }

    }

    private void gameOver() {
        ss.remove(ss.top());
        ss.remove(ss.top());
        ss.push(new GameOver(ss,profile));
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        ciws.paint(clock);

        for (int o = 0 ; o < bullets.size() ; o++){
            bullets.get(o).paint(clock);
        }
        for (int o = 0 ; o < jetPack.size() ; o++){
            jetPack.get(o).paint(clock);
        }
        for (int o = 0 ; o < bombPack.size() ; o++){
            bombPack.get(o).paint(clock);
        }

        if (showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(
                    "HP : " + String.valueOf(hp) + "/" + String.valueOf(hpMax)+
                            "    Armor : " + String.valueOf(armor) + "/" + String.valueOf(armorMax)+
                            "    flare : " + String.valueOf(flare) + "/" + String.valueOf(flareMax),100f,40f);
            debugDraw.getCanvas().drawText("Money : " +String.valueOf(profile.money) +
                    "      Ammo : " +  String.valueOf(ammo) +"/" +  String.valueOf(maga),100f,50f);

            if (ammo == 0 ){
                debugDraw.getCanvas().drawText("Out of ammo! Press R to buy (cost 10)",240f,120f);
            }
            /*
            switch (k){
                case 1:
                    generateEnemy();
                    break;
            }*/

        }

    }

    public  void shootOut(float yk, float mx, final World world, float x, float y) {
        float bk = yk;
        bullets.add(new Bullet(world, ciwsX, ciwsY, yk ,  mx ,this.bodies));
        for (int o = 0 ; o < bullets.size() ; o++){
            this.layer.add(bullets.get(o).layer());
            bodies.put(bullets.get(o),"Bullet");
        }

    }

    public void dropTheBomb(int x,int y){
        bombPack.add(new Bomb(world,x,y,bodies));

        for (int o = 0 ; o < bombPack.size() ; o++){
            this.layer.add(bombPack.get(o).layer());
            bodies.put(bombPack.get(o),"Bomb");
        }


    }

    public void generateEnemy(int y){
        latestJet = jetPack.size();
       // System.out.println("Jet No:" + jetPack.size());
        jetPack.add(new Jet(world,680,y,bodies,jetHp));
        this.layer.add(jetPack.get(latestJet).layer());
    }






}
