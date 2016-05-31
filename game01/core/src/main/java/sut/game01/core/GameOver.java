package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

public class GameOver extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer mainmenu;

    public GameOver(final ScreenStack ss, final Profile profile) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/gameover.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/button/mainmenu.png");
        this.mainmenu = graphics().createImageLayer(backImage);
        mainmenu.setScale(0.5f,0.5f);
        mainmenu.setTranslation(220,400);

        mainmenu.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                for (int i=0;i < ss.size() ; i++){
                    ss.remove(ss.top());
                }
                ss.push(new HomeScreen(ss,profile));
            }
        });

    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(mainmenu);
    }


}
