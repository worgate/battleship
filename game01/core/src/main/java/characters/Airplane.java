package characters;
import playn.core.*;
import playn.core.util.Callback;
import sprite.Sprite;
import sprite.SpriteLoader;


public class Airplane {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State{
        IDLE , FIRE , DESTROY
    }

    private State state = State.IDLE;

    private  int e = 0;
    private int offset = 8;

    public Airplane(final float x ,final float y){
        PlayN.keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.SPACE){
                    switch (state){
                        case IDLE: state = State.FIRE; break;
                        case FIRE: state = State.DESTROY; break;
                        case DESTROY: state = State.IDLE; break;
                    }
                }
            }
        });



        sprite = SpriteLoader.getSprite("images/airplane.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setSize(200,120);
                sprite.layer().setTranslation(x, y + 13f);

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

        e = e + delta;
        if(e > 150){
            switch (state){
                case IDLE: offset = 0; break;
                case FIRE: offset = 4; break;
                case DESTROY: offset = 8; break;
            }


            spriteIndex = offset + ((spriteIndex +1) % 4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
}
}
