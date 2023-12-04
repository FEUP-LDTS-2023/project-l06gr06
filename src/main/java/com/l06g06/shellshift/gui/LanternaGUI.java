package com.l06g06.shellshift.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;


import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import com.l06g06.shellshift.model.game.elements.Position;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaGUI implements Gui{
    protected final Screen screen;

    // Constructor for tests
    public LanternaGUI(Screen screen){
        this.screen = screen;
    }

    public LanternaGUI(int width, int height) throws IOException, URISyntaxException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = loadFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        this.screen = createScreen(terminal);
    }

    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {

        TerminalSize terminalSize = new TerminalSize(width, height + 1);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        Terminal terminal = terminalFactory.createTerminal();

        return terminal;
    }

    private Screen createScreen(Terminal terminal) throws IOException {
        final Screen screen;
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }

    private void drawElement(int x, int y, char chr, String color){
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.Factory.fromString(color));
        graphics.putString(x,y, String.valueOf(chr));
    }


    @Override
    public PressedKey getNextAction() throws IOException {
        KeyStroke key = this.screen.pollInput();

        if (key == null)  return PressedKey.NONE;

        switch (key.getKeyType()){
            case ArrowUp:
                return PressedKey.UP;
            case ArrowDown:
                return PressedKey.DOWN;
            case ArrowLeft:
                return PressedKey.LEFT;
            case ArrowRight:
                return PressedKey.RIGHT;
            case Enter:
                return PressedKey.SELECT;
            case Character:
                if (key.getCharacter() == ' '){
                    return PressedKey.FIRE;
                } else if (key.getCharacter() == 'q'){
                    return PressedKey.EXIT;
                } else {
                    return PressedKey.NONE;
                }
            default:
                return PressedKey.NONE;
        }
    }

    @Override
    public void drawCoin(Position pos){
        drawElement(pos.getX(), pos.getY(), 'c', "#FFFF00");
    }

    @Override
    public void drawChell(Position pos){
        drawElement(pos.getX(), pos.getY(), 'P', "#FFFF00");
    }

    @Override
    public void drawBullet(Position pos){
        drawElement(pos.getX(), pos.getY(), '*', "#FFFF00");
    }

    @Override
    public void drawPlatform(Position pos){
        drawElement(pos.getX(), pos.getY(), '-', "#FFFF00");
    }

    @Override
    public void drawMonster(Position pos){
        drawElement(pos.getX(), pos.getY(), 'M', "#FFFF00");
    }

    @Override
    public void drawPowerup(Position pos) {
        drawElement(pos.getX(), pos.getY(), '=', "#FFFF00");
    }

    @Override
    public void drawText(Position position, String text, String color) {
        TextGraphics textStr = screen.newTextGraphics();
        textStr.setForegroundColor(TextColor.Factory.fromString(color));
        textStr.setBackgroundColor(TextColor.Factory.fromString(color));
        textStr.putString(position.getX(), position.getY(), text);
    }

    @Override
    public void clear(){
        this.screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        this.screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }

    private AWTTerminalFontConfiguration loadFont() throws URISyntaxException, FontFormatException, IOException {
        URL resource = getClass().getClassLoader().getResource("fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 5);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        return fontConfig;
    }
}
