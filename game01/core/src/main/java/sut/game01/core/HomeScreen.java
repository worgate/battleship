package sut.game01.core;
import static playn.core.PlayN.*;

import characters.Profile;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class HomeScreen extends Screen {
    private ScreenStack ss;


    private ImageLayer bg;
    private Image bgImage;

    private Image start;
    private ImageLayer startButton;
    private Image tutorial;
    private ImageLayer tutorialButton;
    private Image ranking;
    private ImageLayer rankingButton;
    private Image select;
    private ImageLayer selectButton;
    private Image exit;
    private ImageLayer exitButton;
    private  ImageLayer upgrade;
    private  ImageLayer upgradeButton;
    private Image credits;
    private ImageLayer creditsButton;

    public HomeScreen(final ScreenStack ss,final  Profile profile){
        this.ss = ss;

        System.out.println("Home Screen");
        System.out.println( ss.size() );
        bgImage = assets().getImage("Images/bg.png");
        bg = graphics().createImageLayer(bgImage);

        start = assets().getImage("Images/button/start_mode.png");
        startButton = graphics().createImageLayer(start);
        startButton.setTranslation(10,290);
        startButton.setSize(250,40);

        tutorial = assets().getImage("Images/button/tutorial_mode.png");
        tutorialButton = graphics().createImageLayer(tutorial);
        tutorialButton.setTranslation(10,335);
        tutorialButton.setSize(200,30);

        ranking = assets().getImage("Images/button/ranking_mode_1.png");
        rankingButton = graphics().createImageLayer(ranking);
        rankingButton.setTranslation(10,370);
        rankingButton.setSize(200,30);

        select = assets().getImage("Images/button/level_mode.png");
        selectButton = graphics().createImageLayer(select);
        selectButton.setTranslation(10,405);
        selectButton.setSize(200,30);

        exit = assets().getImage("Images/button/exit_mode.png");
        exitButton = graphics().createImageLayer(exit);
        exitButton.setTranslation(10,440);
        exitButton.setSize(200,30);

        credits = assets().getImage("Images/button/credits.png");
        creditsButton = graphics().createImageLayer(credits);
        creditsButton.setScale(0.3f,0.3f);
        creditsButton.setTranslation(500,430);

        //creditsButton.setSize(200,30);



        Image upgrade = assets().getImage("Images/button/upgrade.png");
        this.upgradeButton = graphics().createImageLayer(upgrade);
        upgradeButton.setScale(0.08f,0.08f);
        upgradeButton.setTranslation(30,30);

        startButton.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
                if (profile.level == 1){
                    ss.push(new Story1(ss,profile));
                }else if(profile.level == 2){
                    ss.push(new Story2(ss,profile));
                }else if(profile.level == 3){
                    ss.push(new Story3(ss,profile));
                }
            }
        });

        selectButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(new SelectLevel(ss,profile));
            }
        });

        upgradeButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(new Upgrade(ss,profile));
            }
        });

        creditsButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(new Credits(ss,profile));
            }
        });
        tutorialButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(new Tutorials(ss,profile));
            }
        });
        exitButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                System.exit(0);
            }
        });




    }

    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(startButton);
       this.layer.add(tutorialButton);
        this.layer.add(rankingButton);
        this.layer.add(selectButton);
        this.layer.add(upgradeButton);
        this.layer.add(creditsButton);
        this.layer.add(exitButton);

    }


}

