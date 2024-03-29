package com.l06g06.shellshift.controller.menus;


import com.l06g06.shellshift.Game;
import com.l06g06.shellshift.Sound;
import com.l06g06.shellshift.SoundsFx;
import com.l06g06.shellshift.controller.Controller;
import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.map.Map;
import com.l06g06.shellshift.model.mainmenu.MainMenu;
import com.l06g06.shellshift.model.optionsMenu.OptionsMenu;
import com.l06g06.shellshift.model.shop.Shop;
import com.l06g06.shellshift.model.statistics.Statistics;
import com.l06g06.shellshift.model.tutorial.TutorialMap;
import com.l06g06.shellshift.states.*;

import java.io.IOException;
import java.util.List;

public class MainMenuController extends Controller<MainMenu> {
    public MainMenuController(MainMenu mainMenu) {
        super(mainMenu);
    }

    @Override
    public void step(Game game, List<Gui.PressedKey> action, long time) throws IOException {
        for (Gui.PressedKey gpk : action) {
            Game.sleepTimeMS(100);

            switch (gpk) {
                case UP:
                    getModel().prevOption();
                    break;
                case DOWN:
                    getModel().nextOption();
                    break;
                case SELECT:
                    //SomAqui Sound.playSound(SoundsFx.OptionSelect);
                    Sound sound = Sound.getInstance();
                    sound.playSound(SoundsFx.OptionSelect);
                    if (getModel().isSelectedQuit()) game.setState(null);
                    if (getModel().isSelectedStatistics()) game.setState(new StatisticsState(new Statistics()));
                    if (getModel().isSelectedShop()) game.setState(new ShopState(new Shop()));
                    if (getModel().isSelectedTutorial()) game.setState(new TutorialState(new TutorialMap()));
                    if (getModel().isSelectedStart()) game.setState(new GameState(new Map()));
                    if (getModel().isSelectedOptions()) game.setState(new OptionsMenuState(new OptionsMenu()));
                    break;
                default:
            }
        }
    }
}
