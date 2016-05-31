package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

public class Story2 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;

    private  ImageLayer playLayer;
    private  Image play;

    public Story2(final ScreenStack ss,final Profile profile) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/story2.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image play = assets().getImage("Images/button/start.png");
        this.playLayer = graphics().createImageLayer(play);
        playLayer.setScale(0.4f,0.4f);
        playLayer.setTranslation(470,50f);

        playLayer.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                for (int i=0;i < ss.size() ; i++){
                    ss.remove(ss.top());
                }
                ss.push(new SelectLevel(ss,profile));
            }
        });

    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(playLayer);
    }


}
