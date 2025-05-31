package common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayerType {
    Human("Human"),
    Computer("Computer");

    private final String playerType;
}
