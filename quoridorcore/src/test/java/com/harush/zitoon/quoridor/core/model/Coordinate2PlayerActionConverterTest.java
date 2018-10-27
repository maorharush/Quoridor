package com.harush.zitoon.quoridor.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Coordinate2PlayerActionConverterTest {

    private Coordinate2PlayerActionConverter coordinate2PlayerActionConverter;

    @Before
    public void before() {
        coordinate2PlayerActionConverter = new Coordinate2PlayerActionConverterImpl();
    }

    @Test
    public void toPlayerAction_success() {
        Coordinate coordinate = new Coordinate(4,4);

        PlayerAction expectedPlayerAction = new PlayerAction(4,4, null);
        PlayerAction actualPlayerAction = coordinate2PlayerActionConverter.toPlayerAction(coordinate);

        Assert.assertEquals(expectedPlayerAction, actualPlayerAction);
    }
}