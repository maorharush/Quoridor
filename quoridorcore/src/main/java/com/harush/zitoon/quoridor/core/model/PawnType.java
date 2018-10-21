package com.harush.zitoon.quoridor.core.model;

import java.util.Arrays;
import java.util.Optional;

public enum PawnType {

    RED("#b31a1a"),
    GREEN("#336633"),
    WHITE("#f2f2f2"),
    BLUE("#1a3399");

    private String hexColor;

    PawnType(String hexColor) {
        this.hexColor = hexColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    public static PawnType getByHexColor(String hexColor) {
        PawnType[] values = PawnType.values();
        Optional<PawnType> optionalPawnType = Arrays.stream(values).filter(pawnType -> pawnType.getHexColor().equals(hexColor)).findFirst();
        return optionalPawnType.orElse(null);
    }


}

