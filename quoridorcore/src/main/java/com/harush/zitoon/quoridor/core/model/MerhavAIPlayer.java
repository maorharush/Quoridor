package com.harush.zitoon.quoridor.core.model;

import java.util.List;
import java.util.Random;

/**
 * A very simple AI player.
 */
public class MerhavAIPlayer extends Player {

    private final GameSession gameSession;

    private final WallUtil wallUtil;

    public MerhavAIPlayer(String name, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls, GameSession gameSession) {
        super(name, pawn, verticalWalls, horizontalWalls, true);
        this.gameSession = gameSession;
        this.wallUtil = new WallUtilImpl();
    }

    @Override
    public void play() {

        System.out.println("I am Merhav AI... `Thinking`...");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done thinking, now moving...");

        boolean shouldMovePawn = randomBoolean();

        if (shouldMovePawn) {
            movePawn();
        } else {
            placeWall();
        }
    }

    public void placeWall() {
        Coordinate enemyPawnCoordinate = getEnemyPawnCoordinate();
        boolean shouldPlaceVertical = randomBoolean();
        if (shouldPlaceVertical) {
            placeVerticalWall(enemyPawnCoordinate);

        } else {
            placeHorizontalWall(enemyPawnCoordinate);
        }
    }

    public void placeHorizontalWall(Coordinate enemyPawnCoordinate) {
        System.out.println("Placing vertical wall");
        Wall wall = horizontalWalls[enemyPawnCoordinate.getX()][enemyPawnCoordinate.getY()];
        LogicResult logicResult = wall.validateWallPlacement();
        if (logicResult.isSuccess()) {
            wall.placeWall();
        } else {
            setWallRandomly();
        }
    }

    public void placeVerticalWall(Coordinate enemyPawnCoordinate) {
        System.out.println("Placing horizontal wall");

        Wall wall = verticalWalls[enemyPawnCoordinate.getX()][enemyPawnCoordinate.getY()];
        LogicResult logicResult = wall.validateWallPlacement();
        if (logicResult.isSuccess()) {
            wall.placeWall();
        } else {
            setWallRandomly();
        }
    }

    public void movePawn() {
        System.out.println("Moving pawn");
        List<Coordinate> validMoves = pawn.getValidMoves();
        Random random = new Random();
        int rand = random.nextInt(validMoves.size());
        int x = validMoves.get(rand).getX();
        int y = validMoves.get(rand).getY();
        pawn.move(x, y);
    }

    private void setWallRandomly() {
        List<PlayerAction> validWallPlacements = wallUtil.findValidWallPlacements(gameSession.getBoard(), getEnemyPlayer());
        Random random = new Random();
        int rand = random.nextInt(validWallPlacements.size());
        PlayerAction randomMove = validWallPlacements.get(rand);

        boolean shouldPlaceHorizontal = randomBoolean();

        if (shouldPlaceHorizontal) {
            horizontalWalls[randomMove.getX()][randomMove.getY()].placeWall();
        } else {
            verticalWalls[randomMove.getX()][randomMove.getY()].placeWall();
        }
    }

    private Coordinate getEnemyPawnCoordinate() {
        Player enemyPlayer = getEnemyPlayer();
        return enemyPlayer.getPawn().getCurrentCoordinate();
    }

    private Player getEnemyPlayer() {
        int currentPlayerIndex = gameSession.getCurrentPlayerIndex();
        return gameSession.getPlayer((currentPlayerIndex + 1) % 2);
    }

    private boolean randomBoolean() {
        Random random = new Random();
        int randNum = random.nextInt(100);
        return randNum % 2 == 0;
    }

}
