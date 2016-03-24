package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.*;
import react.UnitSlot;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.*;
public class HomeScreen extends Screen {
    private TestScreen testScreen;
    private ScreenStack ss;
    private ImageLayer bg;
    private Image bgImage;
    private ImageLayer startButton;
    private Image start;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;
        this.testScreen = new TestScreen(ss);
        bgImage = assets().getImage("Images/mainbg.png");
        bg = graphics().createImageLayer(bgImage);

        start = assets().getImage("Images/start.png");
        startButton = graphics().createImageLayer(start);
        startButton.setTranslation(220,205);
        
        startButton.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
                    ss.push(testScreen);
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

