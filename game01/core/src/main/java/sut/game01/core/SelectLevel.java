package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import javafx.scene.control.TextArea;
import playn.core.*;
import pythagoras.f.IRectangle;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class SelectLevel extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    private  ImageLayer lv1;
    private  ImageLayer lv1Button;
    private  ImageLayer lv2;
    private  ImageLayer lv2Button;
    private  ImageLayer lv3;
    private  ImageLayer lv3Button;
    private  ImageLayer upgrade;
    private  ImageLayer upgradeButton;

    public SelectLevel(final ScreenStack ss, final Profile profile) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/select.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(0,400);

        //=================LEVEL=====================//
        Image lv1 = assets().getImage("Images/button/level1.png");
        this.lv1Button = graphics().createImageLayer(lv1);
        lv1Button.setScale(0.4f,0.4f);
        lv1Button.setTranslation(194,150);

        Image lv2 = assets().getImage("Images/button/level2.png");
        this.lv2Button = graphics().createImageLayer(lv2);
        lv2Button.setScale(0.4f,0.4f);
        lv2Button.setTranslation(188,190);

        Image lv3 = assets().getImage("Images/button/level3.png");
        this.lv3Button = graphics().createImageLayer(lv3);
        lv3Button.setScale(0.4f,0.4f);
        lv3Button.setTranslation(180,230);
        //===========================================//
        Image upgrade = assets().getImage("Images/button/upgrade.png");
        this.upgradeButton = graphics().createImageLayer(upgrade);
        upgradeButton.setScale(0.08f,0.08f);
        upgradeButton.setTranslation(30,30);


        upgradeButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(new Upgrade(ss,profile));
            }
        });


        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.remove(ss.top());
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss,profile));
            }
        });



    }









    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(lv1Button);
        this.layer.add(lv2Button);
        this.layer.add(lv3Button);
        this.layer.add(upgradeButton);
        this.layer.add(backButton);


    }


}
