package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class HomeScreen extends Screen {
    private ScreenStack ss;
    /*
    private Play1 play1;
    private Story1 story1;
    private Tutorials tutorials;
    private Ranking rankings;
    private SelectLevel selectLevel;*/

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


    //private ImageLayer tuButton;
    //private Image tu;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;

       /* this.story1 = new Story1(ss);
        this.tutorials = new Tutorials(ss);
        this.rankings = new Ranking(ss);
        this.selectLevel = new SelectLevel(ss);
        this.play1 = new Play1(ss);*/

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

        ranking = assets().getImage("Images/button/ranking_mode.png");
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



        startButton.addListener(new Mouse.LayerAdapter(){
        @Override
        public void onMouseUp(Mouse.ButtonEvent event){
                    ss.push(new Play1(ss));
            }
        });


        /*
        tutorialButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(tutorials);
            }
        });

        rankingButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(rankings);
            }
        });
        selectButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event){
                ss.push(selectLevel);
            }
        });
        exitButton.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                System.exit(0);
            }
        });

        */
    }

    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(startButton);
       this.layer.add(tutorialButton);
        this.layer.add(rankingButton);
        this.layer.add(selectButton);
        this.layer.add(exitButton);

    }


}

