package com.harush.zitoon.quoridor.core.model;

import java.util.*;
import java.util.stream.Collectors;

import com.harush.zitoon.quoridor.core.dao.GameRecDAO;
import com.harush.zitoon.quoridor.core.dao.dbo.GameRecDBO;

/**
 * represent a save\load game handler
 */
public class GamePersistenceServiceImpl implements GamePersistenceService {

    private GameRecDAO gameRecDAO;
    private PlayersFactory playersFactory; //TODO Manush: Implement

    public GamePersistenceServiceImpl(GameRecDAO gameRecDAO, PlayersFactory playersFactory) {
        this.gameRecDAO = gameRecDAO;
        this.playersFactory = playersFactory;
    }


    @Override
    public void saveTurn(GameSession gameSession) {
        GameRecDBO gameRecord = new GameRecDBO();
        Board board = gameSession.getBoard();
        List<Player> players = gameSession.getPlayers();
        List<Wall> walls = board.getAllWalls();
        List<GameRecDBO> recordedList = new ArrayList<>();
        int sizeOfplayersList = players.size();
        int sizeOfWallsList = walls.size();

        while (sizeOfplayersList >= 0 || sizeOfWallsList >= 0) {
            //TODO find a solution to manage game_id
            gameRecord.setGame_id(gameSession.getGameID());
            if (sizeOfplayersList >= 0) {
                gameRecord.setPlayer_name(players.get(sizeOfplayersList).getName());
                gameRecord.setPawn_x(players.get(sizeOfplayersList).pawn.getX());
                gameRecord.setPawn_y(players.get(sizeOfplayersList).pawn.getY());
            }
            if (sizeOfWallsList >= 0) {
                gameRecord.setWall_x(walls.get(sizeOfWallsList).getX());
                gameRecord.setWall_y(walls.get(sizeOfWallsList).getY());
                gameRecord.setFence_orien(walls.get(sizeOfWallsList).getOrientation());
            }
            recordedList.add(gameRecord);
            sizeOfplayersList--;
            sizeOfWallsList--;
        }
        gameRecDAO.insert(recordedList);
    }


    @Override
    public GameSession loadGame() {
        int lastGameID = gameRecDAO.getMaxID();
        List<GameRecDBO> recordedList = gameRecDAO.getGameRecords(lastGameID);
        Set<String> playerNames = recordedList.stream().map(GameRecDBO::getPlayer_name).collect(Collectors.toSet());
        Map<String, List<GameRecDBO>> playerName2Moves = new HashMap<>();
        //TODO Manush: Implement - gameRecDAO.getPlayerRecords(lastGameID, playerName)
        playerNames.forEach(playerName -> playerName2Moves.put(playerName, gameRecDAO.getPlayerRecords(lastGameID, playerName)));

        List<Player> players = getPlayers(playerName2Moves);

        Board board = populateBoard(recordedList, players);
        GameSession gameSession = new GameSession(board, Settings.getSingleton().getRuleType(), new WinnerDeciderLogic());

        players.forEach(player -> gameSession.addPlayer(player));

        gameSession.checkForWinnerAndUpdateTurn();

        return gameSession;
    }

    private Board populateBoard(List<GameRecDBO> recordedList, List<Player> players) {
        Board board = new Board(Settings.getSingleton().getBoardWidth(), Settings.getSingleton().getBoardHeight());
        populateBoardWithHorizontalWalls(board, recordedList, players);
        populateBoardWithVerticalWalls(board, recordedList, players);
        populateBoardWithPawns(board, recordedList);
        return board;
    }

    private void populateBoardWithVerticalWalls(Board board, List<GameRecDBO> recordedList, List<Player> players) {
        List<GameRecDBO> gameRecVWalls = recordedList.stream().filter(rec -> rec.getFence_orien() == 'v').collect(Collectors.toList());




//        board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y(), true, true, placedByPlayer);
//        board.setWall(gameRecDBO.getWall_x() + 1, gameRecDBO.getWall_y(), true, false, placedByPlayer);
    }

    private void populateBoardWithHorizontalWalls(Board board, List<GameRecDBO> recordedList, List<Player> players) {
        List<GameRecDBO> gameRecHWalls = recordedList.stream().filter(rec -> rec.getFence_orien() == 'h').collect(Collectors.toList());

        for (GameRecDBO gameRecDBO : gameRecHWalls) {
            Optional<Player> optionalPlayer = players.stream().filter(player -> player.getName().equals(gameRecDBO.getPlayer_name())).findAny();
            if (!optionalPlayer.isPresent()) {
                String errMsg = String.format("Can't find player that placed wall from player list:%s and game record:%s",
                        Arrays.toString(players.toArray(new Player[]{})), gameRecDBO);
                throw new RuntimeException(errMsg);
            }

            Player placedByPlayer = optionalPlayer.get();

            board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y(), false, true, placedByPlayer);
            board.setWall(gameRecDBO.getWall_x(), gameRecDBO.getWall_y() + 1, false, false, placedByPlayer);
        }
    }

    private List<Player> getPlayers(Map<String, List<GameRecDBO>> playerName2Moves) {
        List<Player> players = new ArrayList<>();
        for (Map.Entry<String, List<GameRecDBO>> player2MovesEntry : playerName2Moves.entrySet()) {
            String playerName = player2MovesEntry.getKey();
            List<GameRecDBO> moves = player2MovesEntry.getValue();
            int wallPlacements = getNumOfWallsPlacements(moves);
            int numWallsLeft = Settings.getSingleton().getWalls() - wallPlacements;

            int pawnX = getPawnX(moves);
            int pawnY = getPawnY(moves);

            Player player = playerFactory.getPlayer(playerName, true, pawnX, pawnY, numWallsLeft);
            players.add(player);
        }
        return players;
    }


}
