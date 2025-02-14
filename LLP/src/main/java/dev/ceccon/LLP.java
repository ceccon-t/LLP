package dev.ceccon;

import dev.ceccon.gui.MainView;

import javax.swing.*;

public class LLP {
    public static void main( String[] args ) {
        System.out.println( "Launching LLP - Local Language Practice application." );

        SwingUtilities.invokeLater(() -> {
            new MainView();
        });
    }
}
