package social.godmode.FillerAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

@Getter
public class GamePlayer {
    public Filler filler;
    public UUID playerUUID;
    public FillerColor color;

    public ArrayList<FillerBlock> ownedBlocks = new ArrayList<>();

    public GamePlayer(Filler filler, UUID playerUUID, FillerColor color, FillerBlock startingBlock) {
        this.filler = filler;
        this.playerUUID = playerUUID;
        this.color = color;
        ownedBlocks.add(startingBlock);
    }

    public void turn(FillerColor color) {
        this.color = color;
        for (FillerBlock block : ownedBlocks) {
            block.setColor(color);
        }

        ListIterator<FillerBlock> iterator = ownedBlocks.listIterator();
//        while (iterator.hasNext()) {
//            FillerBlock block = iterator.next();
//            ArrayList<FillerBlock> surroundings = filler.getSurroundings(block);
//            for (FillerBlock surrounding : surroundings) {
//                if (surrounding.getColor() == color && !ownedBlocks.contains(surrounding)) {
//                    ownedBlocks.add(surrounding);
//                }
//            }
//        }
        GamePlayer otherPlayer = getOtherPlayer();

        int size = ownedBlocks.size();
        for (int i = 0; i < size; i++) {
            FillerBlock block = ownedBlocks.get(i);
            ArrayList<FillerBlock> surroundings = filler.getSurroundings(block);
            for (FillerBlock surrounding : surroundings) {
                if (surrounding.getColor() == color && !ownedBlocks.contains(surrounding) && !otherPlayer.ownedBlocks.contains(surrounding)) {
                    ownedBlocks.add(surrounding);
                }
            }
        }

        if (ownedBlocks.size() + otherPlayer.ownedBlocks.size() >= 64) {
            filler.setGameEnded(true);
        } else {
            filler.setCurrentPlayerIndex(filler.getCurrentPlayerIndex() == 0 ? 1 : 0);
        }
    }

    public GamePlayer getOtherPlayer() {
        return filler.getPlayers()[filler.getCurrentPlayerIndex() == 0 ? 1 : 0];
    }

    public List<FillerColor> getAvailableColors() {
        List<FillerColor> availableColors = new ArrayList<>(List.of(FillerColor.values()));
        availableColors.remove(color);
        availableColors.remove(getOtherPlayer().color);
        return availableColors;
    }

}
