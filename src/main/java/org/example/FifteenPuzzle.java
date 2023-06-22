package org.example;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class FifteenPuzzle {
    private static final int SIZE = 4;
    private static final int EMPTY_TILE = 0;
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;

    private List<Integer> tiles;
    private JButton[] buttons;
    private JFrame frame;
    private Random random;

    public FifteenPuzzle() {
        tiles = new ArrayList<>();
        buttons = new JButton[SIZE * SIZE];
        frame = new JFrame("Fifteen Puzzle");
        random = new Random();
        initializeTiles();
        initializeButtons();
        createFrame();
    }

    private void initializeTiles() {
        for (int i = 1; i <= SIZE * SIZE - 1; i++) {
            tiles.add(i);
        }
        tiles.add(EMPTY_TILE);
        Collections.shuffle(tiles, random);
    }

    private void initializeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            int tileNumber = tiles.get(i);
            JButton button = new JButton(tileNumber == EMPTY_TILE ? "" : String.valueOf(tileNumber));
            button.addActionListener(new ButtonClickListener(i));
            buttons[i] = button;
        }
    }

    private void createFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLayout(new GridLayout(SIZE, SIZE));

        for (JButton button : buttons) {
            frame.add(button);
        }

        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int buttonIndex;

        public ButtonClickListener(int buttonIndex) {
            this.buttonIndex = buttonIndex;
        }

        public void actionPerformed(ActionEvent e) {
            if (isAdjacentEmpty(buttonIndex)) {
                int emptyIndex = getEmptyIndex();
                swapTiles(buttonIndex, emptyIndex);
                updateButtonLabels();
                if (isGameOver()) {
                    JOptionPane.showMessageDialog(frame, "Congratulations! You solved the puzzle.");
                    resetGame();
                }
            }
        }
    }

    private boolean isAdjacentEmpty(int buttonIndex) {
        int emptyIndex = getEmptyIndex();
        return (buttonIndex == emptyIndex - 1 && emptyIndex % SIZE != 0) ||
                (buttonIndex == emptyIndex + 1 && buttonIndex % SIZE != 0) ||
                buttonIndex == emptyIndex - SIZE ||
                buttonIndex == emptyIndex + SIZE;
    }

    private int getEmptyIndex() {
        return tiles.indexOf(EMPTY_TILE);
    }

    private void swapTiles(int index1, int index2) {
        Collections.swap(tiles, index1, index2);
    }

    private void updateButtonLabels() {
        for (int i = 0; i < buttons.length; i++) {
            int tileNumber = tiles.get(i);
            buttons[i].setText(tileNumber == EMPTY_TILE ? "" : String.valueOf(tileNumber));
        }
    }

    private boolean isGameOver() {
        for (int i = 1; i < tiles.size(); i++) {
            if (tiles.get(i) != i) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        initializeTiles();
        updateButtonLabels();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FifteenPuzzle());
    }
}
