package social.godmode.FillerAPI;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Getter @Setter
public class Filler {
    private final FillerBlock[][] board;
    private FillerPlayer[] players;
    private int currentPlayerIndex;
    private boolean gameEnded;
    private Random random;
    private final long seed;

    public Filler(long seed, UUID... playerUUIDs) {
        random = new Random(seed);
        this.seed = seed;

        this.board = new FillerBlock[8][8];
        fillBoard();
        if (playerUUIDs.length != 2) throw new IllegalArgumentException("Filler game must have exactly 2 players");
        this.players = new FillerPlayer[2];

        // player 1 gets bottom left corner
        players[0] = new FillerPlayer(this, playerUUIDs[0], board[0][0].getColor(), board[0][0]);
        // player 2 gets top right corner
        players[1] = new FillerPlayer(this, playerUUIDs[1], board[7][7].getColor(), board[7][7]);

        while (players[1].color == players[0].color) {
            // pick a random color not in player[1]'s surroundings
            players[1].color = players[1].getAvailableColors().get((int) (random.nextDouble() * players[1].getAvailableColors().size()));
            board[7][7].setColor(players[1].color);
        }

        currentPlayerIndex = 0;
        gameEnded = false;
    }

    public Filler(UUID... playerUUIDs) {
        this(new Random().nextLong(), playerUUIDs);
    }

    public FillerPlayer getWinner() {
        if (!gameEnded) return null;
        return players[0].getOwnedBlocks().size() > players[1].getOwnedBlocks().size() ? players[0] : players[1];
    }

    public FillerPlayer getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    private void fillBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                do {
                    board[i][j] = new FillerBlock(FillerColor.getRandomColor(random), i, j);
                } while (surroundingsHaveSameColor(board[i][j]));
            }
        }
    }

    public ArrayList<FillerBlock> getSurroundings(FillerBlock block) {
        ArrayList<FillerBlock> surroundings = new ArrayList<>();
        int x = block.getX();
        int y = block.getY();
        if (x > 0) surroundings.add(board[x - 1][y]);
        if (x < 7) surroundings.add(board[x + 1][y]);
        if (y > 0) surroundings.add(board[x][y - 1]);
        if (y < 7) surroundings.add(board[x][y + 1]);
        // remove null values
        surroundings.removeIf(Objects::isNull);
        return surroundings;
    }

    private boolean surroundingsHaveSameColor(FillerBlock block) {
        FillerColor color = block.getColor();
        for (FillerBlock surrounding : getSurroundings(block)) {
            if (surrounding.getColor() == color) return true;
        }
        return false;
    }
}
