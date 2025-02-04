import social.godmode.FillerAPI.Filler;
import social.godmode.FillerAPI.FillerBlock;
import social.godmode.FillerAPI.FillerColor;
import social.godmode.FillerAPI.FillerPlayer;

import java.awt.*;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Filler filler = new Filler(UUID.randomUUID(), UUID.randomUUID());

        logBoard(filler);

        while (!filler.isGameEnded()) {
            FillerPlayer currentPlayer = filler.getCurrentPlayer();
            // get random color
            FillerColor color = currentPlayer.getAvailableColors().get((int) (Math.random() * currentPlayer.getAvailableColors().size()));
            currentPlayer.turn(color);
            logBoard(filler);
        }

        FillerPlayer winner = filler.getWinner();
        System.out.println("Winner: " + winner.getPlayerUUID());

//        GamePlayer currentPlayer = filler.getCurrentPlayer();
//        ArrayList<FillerBlock> surroundings = filler.getSurroundings(currentPlayer.getOwnedBlocks().getFirst());
//        currentPlayer.turn(surroundings.getFirst().getColor());

    }

    public static void logBoard(Filler filler) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                FillerBlock block = filler.getBoard()[i][j];
                FillerColor color = block.getColor();
                String ansiColor = getAnsiColor(color.color);
                System.out.print(ansiColor + "||" + "\u001B[0m");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String getAnsiColor(Color color) {
        return String.format("\u001B[38;2;%d;%d;%dm", color.getRed(), color.getGreen(), color.getBlue());
    }
}
