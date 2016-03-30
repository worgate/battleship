package sut.game01.core;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import characters.Airplane;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Play1 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    private  Airplane airplane;
    public Play1(final ScreenStack ss) {
        this.ss = ss;

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
        this.layer.add(bg);
        this.layer.add(backButton);

        airplane = new Airplane(560f ,400f);
        this.layer.add(airplane.layer());
    }
    @Override
    public void update(int delta){
        super.update(delta);
        airplane.update(delta);
    }





}
