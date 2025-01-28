package social.godmode.FillerAPI;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Getter @Setter
public class Filler {
    private final FillerBlock[][] board;
    private GamePlayer[] players;
    private int currentPlayerIndex;
    private boolean gameEnded;

    public Filler(UUID... playerUUIDs) {
        this.board = new FillerBlock[8][8];
        fillBoard();
        if (playerUUIDs.length != 2) throw new IllegalArgumentException("Filler game must have exactly 2 players");
        this.players = new GamePlayer[2];

        // player 1 gets bottom left corner
        players[0] = new GamePlayer(this, playerUUIDs[0], board[0][0].getColor(), board[0][0]);
        // player 2 gets top right corner
        players[1] = new GamePlayer(this, playerUUIDs[1], board[7][7].getColor(), board[7][7]);

        currentPlayerIndex = 0;
        gameEnded = false;
    }

    public GamePlayer getWinner() {
        if (!gameEnded) return null;
        return players[0].getOwnedBlocks().size() > players[1].getOwnedBlocks().size() ? players[0] : players[1];
    }

    public GamePlayer getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    private void fillBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                do {
                    board[i][j] = new FillerBlock(FillerColor.getRandomColor(), i, j);
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
