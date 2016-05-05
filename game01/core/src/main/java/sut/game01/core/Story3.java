package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class Story3 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;

    public Story3(final ScreenStack ss) {
        this.ss = ss;
        System.out.println("Story3");
        Image bgImage = assets().getImage("Images/story/story3.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(0,400);

    /*
        keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.F3 ){
                   ss.remove(ss.top());
                   ss.remove(ss.top());
                   System.out.println( ss.size() );
                   ss.push(new HomeScreen(ss));
                }
            }
        });
        */

    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(backButton);
    }


}
