package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.Font;
import react.UnitSlot;
import tripleplay.game.*;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.*;
public class HomeScreen extends UIScreen {

    public static final Font TITLE_FONT = graphics().createFont("Helvetica",Font.Style.PLAIN,24);

    private Root root;
    private TestScreen testScreen;
    private ScreenStack ss;

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
        this.testScreen = new TestScreen(ss);

    }

    @Override
    public void wasShown(){
        super.wasShown();
        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), this.layer);
        root.addStyles(Style.BACKGROUND
                .is(Background.bordered(0xFFCCCCCC , 0xFF99CCFF ,5)
                .inset(5,10)));
        root.setSize(width(), height());

        root.add(new Label("Event Driven Programming")  /*center of monitor*/
            .addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));


        root.add(new Button("Start").onClick(new UnitSlot() {
            public void onEmit(){
                ss.push(testScreen);
            }
        }));


    }




}

