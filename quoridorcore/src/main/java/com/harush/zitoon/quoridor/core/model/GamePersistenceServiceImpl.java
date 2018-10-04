package com.harush.zitoon.quoridor.core.model;
import java.util.ArrayList;
import java.util.List;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

/**
 *   represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService{

    private GameRecDAO dao;

    public GamePersistenceServiceImpl(GameRecDAO dao) {
        this.dao = dao;
    }


    @Override
    public void saveTurn(GameSession gameSession){
        GameRecDBO gameRecord = new GameRecDBO();
        Board board = gameSession.getBoard();
        List<Player> players= gameSession.getPlayers();
        List<Wall> walls = board.getAllWalls();
        List<GameRecDBO> recordedList = new ArrayList<>();
        int sizeOfplayersList = players.size();
        int sizeOfWallsList = walls.size();

     while(sizeOfplayersList>=0 || sizeOfWallsList>=0){
         //TODO find a solution to manage game_id
         gameRecord.setGame_id(gameSession.getGame_id());
         if (sizeOfplayersList>=0){
             gameRecord.setPlayer_name(players.get(sizeOfplayersList).getName());
             gameRecord.setPawn_x(players.get(sizeOfplayersList).pawn.getX());
             gameRecord.setPawn_y(players.get(sizeOfplayersList).pawn.getY());
         }
         if(sizeOfWallsList>=0){
             gameRecord.setWall_x(walls.get(sizeOfWallsList).getX());
             gameRecord.setWall_y(walls.get(sizeOfWallsList).getY());
             gameRecord.setFence_orien(walls.get(sizeOfWallsList).getOrientation());
         }
         recordedList.add(gameRecord);
         sizeOfplayersList--;
         sizeOfWallsList--;
     }
        dao.insert(recordedList);
    }

    @Override
    public GameSession loadGame() {


        return null;
    }


}
