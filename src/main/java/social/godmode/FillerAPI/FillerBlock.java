package social.godmode.FillerAPI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class FillerBlock {
    private FillerColor color;
    private final int x;
    private final int y;
}
