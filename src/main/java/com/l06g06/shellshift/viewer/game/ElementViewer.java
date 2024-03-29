package com.l06g06.shellshift.viewer.game;


import com.l06g06.shellshift.gui.Gui;
import com.l06g06.shellshift.model.game.elements.Element;

public interface ElementViewer<T extends Element> {
    void draw(T element, Gui gui);
}
