package sut.game01.core;
import characters.*;
import characters.Profile;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sprite.Sprite;
import sprite.SpriteLoader;
import sut.game01.core.Play1;

import java.util.HashMap;


public class Hp {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Boolean contacted;
    private int contactCheck;
    public Body body;
    Body other;


    public enum State{
        H1 , H2 , H3,H4,H5
    }

    private State state = State.H1;
    private  int health = 100;
    characters.Profile profile;

    public Hp(final float x , final float y,final characters.Profile profile){
        this.profile = profile;
        sprite = SpriteLoader.getSprite("images/hp.json");

        sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                //sprite.layer().setSize(600/2,200/2);
               // sprite.layer().setScale(0.2f,0.2f);
                sprite.layer().setTranslation(x, y );
                hasLoaded = true;

            }
            @Override
            public void onFailure(Throwable cause) {

            }
        });
    }

    public Layer layer(){
        return sprite.layer();
    }
    public void update(int delta){

        if(hasLoaded == false) return;
        if (profile.hpLevel == 5){
            state = State.H5;
        }else if (profile.hpLevel == 4){
            state = State.H4;
        }else  if (profile.hpLevel == 3){
            state = State.H3;
        }else  if (profile.hpLevel == 2){
            state = State.H2;
        }        else  if (profile.hpLevel == 1){
            state = State.H1;
        }
        switch (state){
            case H5:
                sprite.setSprite(4); break;
            case H4:
                sprite.setSprite(3); break;
            case H3:
                sprite.setSprite(2); break;
            case H2:
                sprite.setSprite(1); break;
            case H1:
                sprite.setSprite(0); break;
        }

    }

    public void paint(Clock clock) {
        if (!hasLoaded){ return;}

    }
    public void hpLevelUp(final Profile profile){

        switch (profile.hpLevel){
            case 1 :
                if (profile.money >= 10){
                    profile.money -= 10;
                    profile.hpLevel = 2;
                } break;
            case 2 :
                if (profile.money >= 20){
                    profile.money -= 20;
                    profile.hpLevel = 3;
                } break;
            case 3 :
                if (profile.money >= 30){
                    profile.money -= 30;
                    profile.hpLevel = 4;
                } break;
            case 4 :
                if (profile.money >= 50){
                    profile.money -= 50;
                    profile.hpLevel = 5;
                } break;

        }

    }


}