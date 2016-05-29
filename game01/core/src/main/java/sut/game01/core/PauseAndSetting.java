package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import org.jbox2d.callbacks.DebugDraw;
import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class PauseAndSetting extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    CanvasImage image;

    public PauseAndSetting(final ScreenStack ss, final Profile profile) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/gameover.png");
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

    }


}
