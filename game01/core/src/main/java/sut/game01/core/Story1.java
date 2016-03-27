package sut.game01.core;
import static playn.core.PlayN.*;

import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

public class Story1 extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    private  ImageLayer battleship;
    private  Image battleshippic;

    private  ImageLayer jet;
    private  Image jetpic;

    private  ImageLayer enemyship;
    private  Image enemyshippic;


    public Story1(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/story1.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(0,0);

        battleshippic = assets().getImage("Images/char/ship.png");
        this.battleship = graphics().createImageLayer(battleshippic);
        battleship.setTranslation(-60,380);
        battleship.setSize(300,90);

        jetpic = assets().getImage("Images/char/jet2.png");
        this.jet = graphics().createImageLayer(jetpic);
        jet.setTranslation(460,50);
        jet.setSize(70,20);


        enemyshippic = assets().getImage("Images/char/enemy_ship.png");
        this.enemyship = graphics().createImageLayer(enemyshippic);
        enemyship.setTranslation(380,400);
        enemyship.setSize(200,60);


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
        this.layer.add(battleship);
        this.layer.add(jet);
        this.layer.add(enemyship);
    }


}
