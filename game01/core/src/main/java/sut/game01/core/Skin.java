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


public class Skin {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private Boolean contacted;
    private int contactCheck;
    public Body body;
    Body other;

    public void switchUp(final Profile profile) {
        if (profile.skin == 1){
              profile.skin = 2;
            state = State.H2;
        }else{
            profile.skin = 1;
            state = State.H1;
        }

    }

    public enum State{
        H1 , H2
    }

    private State state = State.H1;
    private  int health = 100;
     Profile profile;
    public Skin(final float x , final float y,final characters.Profile profile){
        this.profile = profile;
        sprite = SpriteLoader.getSprite("images/skin.json");

        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setScale(0.2f,0.2f);
                sprite.layer().setTranslation(x, y );
                hasLoaded = true;
            }
            @Override
            public void onFailure(Throwable cause) {

            }
        });
        if (profile.skin == 1){
            state = state.H1;
        }else {
            state = state.H2;
        }

    }

    public Layer layer(){
        return sprite.layer();
    }
    public void update(int delta){
            if (state == State.H1){
                sprite.setSprite(0);
            }else {
                sprite.setSprite(1);
            }


    }

    public void paint(Clock clock) {
        if (!hasLoaded){ return;}

    }



}
