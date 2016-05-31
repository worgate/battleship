package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import playn.core.Layer;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

public class Upgrade extends Screen{
    private  final ScreenStack ss;
    private  ImageLayer bg;
    private  ImageLayer backButton;
    private  ImageLayer cost;
    private  ImageLayer costLayer;
    Hp hp ;
    Armor armor;
    Power power;
    Flare flare;
    Skin skin;
    public Upgrade(final ScreenStack ss,final Profile profile) {
        this.ss = ss;

        Image bgImage = assets().getImage("Images/story/upgrade.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image backImage = assets().getImage("Images/back.png");
        this.backButton = graphics().createImageLayer(backImage);
        backButton.setTranslation(0,400);

        Image cost = assets().getImage("Images/char/cost.png");
        this.costLayer = graphics().createImageLayer(cost);
        costLayer.setScale(0.5f,0.5f);
        costLayer.setTranslation(20,100);


        hp = new Hp(170,200,profile);
        armor = new Armor(170,250,profile);
        power = new Power(170,300,profile);
        flare = new Flare(170,350,profile);
        skin = new Skin(450,440,profile);

        backButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.remove(ss.top());

    }
});

        hp.layer().addListener(new Mouse.LayerAdapter(){
@Override
public void onMouseDown(Mouse.ButtonEvent event) {
        hp.hpLevelUp(profile);
        }
        });
        armor.layer().addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                armor.armorLevelUp(profile);
            }
        });
        power.layer().addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                power.powerLevelUp(profile);
            }
        });
        flare.layer().addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                flare.flareLevelUp(profile);
            }
        });
        skin.layer().addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                skin.switchUp(profile);
            }
        });
    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(costLayer);
        this.layer.add(backButton);
        this.layer.add(hp.layer());
        this.layer.add(armor.layer());
        this.layer.add(power.layer());
        this.layer.add(flare.layer());
        this.layer.add(skin.layer());
    }
    @Override
    public void paint(Clock clock) {
        hp.paint(clock);
        armor.paint(clock);
        power.paint(clock);
        flare.paint(clock);
    }

    @Override
    public void update(int delta) {
        hp.update(delta);
        armor.update(delta);
        power.update(delta);
        flare.update(delta);
        try {
            skin.update(delta);
        }catch (IndexOutOfBoundsException e){

        }


    }
}
