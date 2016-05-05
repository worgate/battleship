package sut.game01.core;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import characters.Airplane;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.mouse;

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
    BodyDef bodyDef;


    public Play1(final ScreenStack ss) {
        this.ss = ss;

        final Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //gravity.set(10f,0.0f);
                //world.setGravity(gravity);

                bodyDef = new BodyDef();
                bodyDef.type = BodyType.DYNAMIC;
                bodyDef.position = new Vec2(event.x() * M_PER_PIXEL,event.y()*M_PER_PIXEL);
                Body body = world.createBody(bodyDef);

                PolygonShape shape = new PolygonShape();
                shape.setAsBox(1,1);
                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.4f;
                fixtureDef.friction = 0.2f;
                fixtureDef.restitution = 0.4f;
                body.createFixture(fixtureDef);
                body.setLinearDamping(0.2f);
            }
        });

        Image bgImage = assets().getImage("Images/story/play1.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(0,400);

        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.remove(ss.top());


            }
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();
        //this.layer.add(bg);
        this.layer.add(backButton);

        airplane = new Airplane(world,560f ,400f);
        this.layer.add(airplane.layer());

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
        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0, height), new Vec2(width, height));
        ground.createFixture(groundShape, 0.0f);

        Body ground2 = world.createBody(new BodyDef());
        EdgeShape groundShape2 = new EdgeShape();
        groundShape2.set(new Vec2(0, 0), new Vec2(width, 0));
        ground.createFixture(groundShape2, 0.0f);

        Body ground3 = world.createBody(new BodyDef());
        EdgeShape groundShape3 = new EdgeShape();
        groundShape2.set(new Vec2(0, 0), new Vec2(0, height));
        ground.createFixture(groundShape2, 0.0f);

        Body ground4 = world.createBody(new BodyDef());
        EdgeShape groundShape4 = new EdgeShape();
        groundShape2.set(new Vec2(width, 0), new Vec2(width, height));
        ground.createFixture(groundShape2, 0.0f);



    }
    @Override
    public void update(int delta){
        super.update(delta);
        world.step(0.033f,10,10);
        airplane.update(delta);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        airplane.paint(clock);
        if (showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }

    }
}
