package com.harush.zitoon.quoridor.core.model;
import java.util.ArrayList;
import java.util.List;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

public class GamePersistenceServiceImpl implements GamePersistenceService{

    private GameRecDAO dao;

    public GamePersistenceServiceImpl(GameRecDAO dao) {
        this.dao = dao;
    }


    @Override
    public void saveTurn(GameSession gameSession){
        GameRecDBO recorder = new GameRecDBO();
        Board board = gameSession.getBoard();
        List<Player> players= gameSession.getPlayers();
        List<Wall> wall = board.getAllWalls();
        List<GameRecDBO> recordedList = new ArrayList<>();
        int sizeOfplayersList = players.size();
        int sizeOfWallsList = wall.size();

     while(sizeOfplayersList>=0 || sizeOfWallsList>=0){
         recorder.setGame_id(1);//maybe getMaxGameID()?
         recorder.setPlayer_name(sizeOfplayersList);
         recorder.setPawn_x(players.get(sizeOfplayersList).pawn.getX());
         recorder.setPawn_y(players.get(sizeOfplayersList).pawn.getY());
         recorder.setWall_x(wall.get(sizeOfWallsList).getX());
         recorder.setWall_y(wall.get(sizeOfWallsList).getY());
         recorder.setFence_orien(wall.get(sizeOfWallsList).getOrientation());
         recordedList.add(recorder);
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
