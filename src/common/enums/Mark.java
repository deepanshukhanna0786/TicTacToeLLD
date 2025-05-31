package common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mark {
    X("X"),
    O("O"),
    EMPTY(" ");

    private final String symbol;
}
