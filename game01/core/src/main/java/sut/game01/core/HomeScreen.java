package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class HomeScreen extends Screen {
    private TestScreen testScreen;
    private Story1 story1;
    private ScreenStack ss;
    private ImageLayer bg;
    private Image bgImage;
    private ImageLayer startButton;
    private Image start;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
        this.story1 = new Story1(ss);
        bgImage = assets().getImage("Images/mainbg.png");
        bg = graphics().createImageLayer(bgImage);

        start = assets().getImage("Images/button/start.png");
        startButton = graphics().createImageLayer(start);
        startButton.setTranslation(230,300);
        
        startButton.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
                    ss.push(story1);
            }
        });

    }

    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(startButton);

    }




}

