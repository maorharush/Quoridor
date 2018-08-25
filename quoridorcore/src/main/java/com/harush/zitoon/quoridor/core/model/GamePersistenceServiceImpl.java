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
    public void saveGame(GameSession gameSession){
        GameRecDBO recorder = new GameRecDBO();
        Board board = gameSession.getBoard();
        List<Player> players= gameSession.getPlayers();
        List<Wall> wall = board.getAllWall();
        List<GameRecDBO> recordedList = new ArrayList<>();
        int sizeOfplayersList = players.size();
        int sizeOfWallsList = wall.size();

     while(sizeOfplayersList>=2 || sizeOfWallsList>=0){
         recorder.setGame_id(1);//maybe getMaxGameID()?
         recorder.setPlayer_id(sizeOfplayersList);
         recorder.setCur_col((char)players.get(sizeOfplayersList).pawn.getX());
         recorder.setCur_row(players.get(sizeOfplayersList).pawn.getY());
         recorder.setFence_col(wall.get(sizeOfWallsList).getX());
         recorder.setFence_row(wall.get(sizeOfWallsList).getY());
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
