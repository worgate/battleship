package sut.game01.core;
import static playn.core.PlayN.*;

import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

public class Play2 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer mainmenu;

    public Play2(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/play3.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.mainmenu = graphics().createImageLayer(backImage);
        mainmenu.setTranslation(0,400);



    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(mainmenu);
    }


}
