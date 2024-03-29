package com.l06g06.shellshift.model.mainmenu;

import com.l06g06.shellshift.Components;

import java.util.Arrays;
import java.util.List;

public class MainMenu {
    private final List<Components> options;
    private int currOption = 0;

    public MainMenu() {
        this.options = Arrays.asList(Components.Start, Components.Shop, Components.Tutorial, Components.Statistics, Components.Options, Components.Quit);
    }

    public List<Components> getOptions() {
        return options;
    }

    public void nextOption() {
        currOption++;
        if (currOption > this.options.size() - 1)
            currOption = this.options.size() - 1;
    }

    public void prevOption() {
        currOption--;
        if (currOption < 0)
            currOption = 0;
    }

    public int getCurrOption() {
        return currOption;
    }

    public boolean isSelected(int i) {
        return i == currOption;
    }

    public int getOptionsSize() {
        return this.options.size();
    }

    public boolean isSelectedShop() {
        return isSelected(1);
    }

    public boolean isSelectedStart() {
        return isSelected(0);
    }

    public boolean isSelectedQuit() {
        return isSelected(getOptionsSize() - 1);
    }

    public boolean isSelectedTutorial() {
        return isSelected(2);
    }

    public boolean isSelectedStatistics() {
        return isSelected(3);
    }

    public boolean isSelectedOptions() {
        return isSelected(4);
    }

}