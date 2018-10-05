package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class PlayerAction2GameRecDBOConverterTest {

    private static PlayerAction2GameRecDBOConverter converter;

    @Mock
    private Player player;

    @BeforeClass
    public static void beforeClass() {
        converter = new PlayerAction2GameRecDBOConverterImpl();
    }

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertMovePawnAction_success() {
        when(player.getName()).thenReturn("testPlayer");
        PlayerAction playerAction = new PlayerAction(PlayerActionType.MOVE_PAWN,4, 9, null, player);

        GameRecDBO expected = new GameRecDBO();
        expected.setPawn_x(4);
        expected.setPawn_y(9);
        expected.setWall_x(-1);
        expected.setWall_y(-1);
        expected.setPlayer_name("testPlayer");

        GameRecDBO actual = converter.toGameRecDBO(playerAction);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void convertPlaceWallAction_success() {
        when(player.getName()).thenReturn("testPlayer");
        PlayerAction playerAction = new PlayerAction(PlayerActionType.PLACE_WALL,4, 9, 'h', player);

        GameRecDBO expected = new GameRecDBO();
        expected.setPawn_x(-1);
        expected.setPawn_y(-1);
        expected.setWall_x(4);
        expected.setWall_y(9);
        expected.setFence_orien('h');
        expected.setPlayer_name("testPlayer");

        GameRecDBO actual = converter.toGameRecDBO(playerAction);

        Assert.assertEquals(expected, actual);
    }
}
