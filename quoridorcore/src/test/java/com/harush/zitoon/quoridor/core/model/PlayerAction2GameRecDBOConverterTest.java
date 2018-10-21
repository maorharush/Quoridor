package com.harush.zitoon.quoridor.core.model;

import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.PlayerAction2GameRecDBOConverter;
import com.harush.zitoon.quoridor.core.dao.dbo.converter.PlayerAction2GameRecDBOConverterImpl;
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

    @Mock
    private Pawn pawn;

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
        when(player.getPlayerID()).thenReturn(22);
        when(pawn.getType()).thenReturn(PawnType.RED);
        when(player.getPawn()).thenReturn(pawn);
        PlayerAction playerAction = new PlayerAction(4, 9, player);
        playerAction.setPlayer(player);

        GameRecDBO expected = new GameRecDBO();
        expected.setGame_id(1);
        expected.setPawn_x(4);
        expected.setPawn_y(9);
        expected.setPawn_type("RED");
        expected.setWall_x(-1);
        expected.setWall_y(-1);
        expected.setIs_first(-1);
        expected.setPlayer_id(22);

        GameRecDBO actual = converter.toGameRecDBO(1, playerAction);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void convertPlaceWallAction_success() {
        when(player.getPlayerID()).thenReturn(22);
        PlayerAction playerAction = new PlayerAction(4, 9, true, true, player);
        playerAction.setPlayer(player);

        GameRecDBO expected = new GameRecDBO();
        expected.setGame_id(1);
        expected.setPawn_x(-1);
        expected.setPawn_y(-1);
        expected.setWall_x(4);
        expected.setWall_y(9);
        expected.setFence_orien('h');
        expected.setIs_first(1);
        expected.setPlayer_id(22);

        GameRecDBO actual = converter.toGameRecDBO(1, playerAction);

        Assert.assertEquals(expected, actual);
    }
}
