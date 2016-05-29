package sut.game01.core;


import characters.Profile;
import tripleplay.game.ScreenStack;
import playn.core.util.Clock;
import playn.core.Game;


public class MyGame extends Game.Default {
    public static final int UPDATE_RATE = 50;
    private ScreenStack ss = new ScreenStack();
    protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);

    public MyGame() {
    super(UPDATE_RATE);
  }


  @Override
  public void init() {
      final Profile profile = new Profile(200,1,1,1,1,1);
      ss.push(new HomeScreen(ss,profile));

  }

  @Override
  public void update(int delta) {
      ss.update(delta);

  }

  @Override
  public void paint(float alpha) {
    clock.paint(alpha);
      ss.paint(clock);

  }

}
