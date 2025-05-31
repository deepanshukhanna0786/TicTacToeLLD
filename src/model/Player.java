package model;

import common.enums.Mark;
import common.enums.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Player {
    private String name;
    private Mark mark;
    private PlayerType playerType;

    public boolean isComputer() {
        return playerType == PlayerType.Computer;
    }

}
