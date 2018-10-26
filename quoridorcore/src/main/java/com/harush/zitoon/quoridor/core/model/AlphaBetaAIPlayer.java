package com.harush.zitoon.quoridor.core.model;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A very simple AI player.
 */
public class AlphaBetaAIPlayer extends Player {

    private final GameSession gameSession;

    private final WallUtil wallUtil;

    private final PawnUtil pawnUtil;

    private final CloneUtil cloneUtil;

    private final int DEPTH = 1;


    public AlphaBetaAIPlayer(String name, GameSession gameSession, Pawn pawn, Wall[][] verticalWalls, Wall[][] horizontalWalls) {
        super(name, pawn, verticalWalls, horizontalWalls, true);
        this.gameSession = gameSession;
        this.wallUtil = new WallUtilImpl();
        this.pawnUtil = new PawnUtilImpl();
        this.cloneUtil = new CloneUtilImpl();

    }

    @Override
    public void play() {
        System.out.println("I am alpha beta AI... `Thinking`...");

        Board boardClone = cloneUtil.clone(gameSession.getBoard(), Board.class);
        List<Player> playersClones = cloneUtil.clone(gameSession.getPlayers(), Player.class, boardClone);
        Player alphaBetaAIPlayerClone = cloneUtil.clone(this, Player.class, boardClone);

        List<PlayerAction> moves = generateMoves(alphaBetaAIPlayerClone, boardClone);
        PlayerAction maxMove = moves.remove(0);
        int maxScore = alphaBeta(playersClones, alphaBetaAIPlayerClone, gameSession.getCurrentPlayerIndex(), makePotentialMove(maxMove, boardClone), 0, -1000000, +1000000);
        for (PlayerAction current : moves) {
            int tempScore = alphaBeta(playersClones, alphaBetaAIPlayerClone, gameSession.getCurrentPlayerIndex(), makePotentialMove(current, boardClone), 0, -1000000, +1000000);
            if (tempScore >= maxScore) {
                maxMove = current;
                break;
            }
        }

        makeChosenMove(maxMove);
    }

    private void makeChosenMove(PlayerAction maxMove) {
        if (maxMove.getPlayerActionType().equals(PlayerActionType.MOVE_PAWN)) {
            pawn.move(maxMove.getX(), maxMove.getY());
        } else if (maxMove.isHorizontal()) {
            horizontalWalls[maxMove.getX()][maxMove.getY()].placeWall();
        } else {
            verticalWalls[maxMove.getX()][maxMove.getY()].placeWall();
        }
    }


    /**
     * Alpha-beta algorithm
     *
     * @param players List of players
     * @param max     Player to maximise
     * @param board   Current board state
     * @param level   Number of levels of recursion
     * @param alpha   alpha score
     * @param beta    beta score
     * @return min-max score
     */
    public int alphaBeta(List<Player> players, Player max, int currentPlayerIndex, Board board, int level, int alpha, int beta) {
        int score;

        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        Player player = players.get(currentPlayerIndex);

        if (level == DEPTH) {
            return calcScore(player, cloneUtil.clone(players, Player.class, board));
        } else {
            List<PlayerAction> moves = generateMoves(player, board);
            if (player.equals(max)) {
                for (PlayerAction next : moves) {
                    score = alphaBeta(players, max, currentPlayerIndex, makePotentialMove(next, board), level + 1, alpha, beta);
                    if (score > alpha) {
                        alpha = score;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
                return alpha;
            } else {
                for (PlayerAction next : moves) {
                    score = alphaBeta(players, max, currentPlayerIndex, makePotentialMove(next, board), level + 1, alpha, beta);
                    if (score < beta) {
                        beta = score;
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
                return beta;
            }
        }
    }

    private int calcScore(Player player, List<Player> others) {
        PlayerAction move = getShortestPath(player);
        others.remove(player);
        int min = getShortestPath(others.remove(0)).getPathLength();
        for (Player other : others) {
            int s = getShortestPath(other).getPathLength();
            if (s > min) {
                min = s;
            }
        }
        return (min - move.getPathLength());
    }

    private PlayerAction getShortestPath(Player player) {
        Queue<PlayerAction> q = new LinkedList<>();
        LinkedList<PlayerAction> visited = new LinkedList<>();
        PlayerAction current = new Coordinate2PlayerActionConverterImpl().toPlayerAction(player.getPawn().getCurrentCoordinate());

        boolean finished = false;

        q.add(current);
        visited.add(current);

        while (!q.isEmpty() && !finished) {
            current = q.remove();

            List<PlayerAction> pawnMoves = pawnUtil.generatePawnMoves(player);
            for (PlayerAction p : pawnMoves) {
                if (!visited.contains(p)) {
                    p.setParent(current);
                    visited.add(p);
                    q.add(p);
                    if (new WinnerDeciderLogic().isWinner(player)) {
                        current = p;
                        finished = true;
                        break;
                    }
                }
            }
        }

        return current;
    }

    private Board makePotentialMove(PlayerAction potentialMove, Board board) {
        Board boardClone = cloneUtil.clone(board, Board.class);

        if (potentialMove.getPlayerActionType().equals(PlayerActionType.MOVE_PAWN)) {
            boardClone.movePawn(pawn.getX(), pawn.getY(), potentialMove.getX(), potentialMove.getY());
            pawn.move(potentialMove.getX(), potentialMove.getY());
        } else if (potentialMove.getPlayer().getNumWalls() > 0) {
            if (potentialMove.isHorizontal()) {
                potentialMove.getPlayer().decrementWalls();
                boardClone.setWall(potentialMove.getX(), potentialMove.getY(), true, true, potentialMove.getPlayer());
            } else {
                potentialMove.getPlayer().decrementWalls();
                boardClone.setWall(potentialMove.getX(), potentialMove.getY(), false, true, potentialMove.getPlayer());
            }
        }

        return boardClone;
    }

    private List<PlayerAction> generateMoves(Player player, Board board) {
        List<PlayerAction> allMoves = Lists.newArrayList();
        List<PlayerAction> playerActions = pawnUtil.generatePawnMoves(player);

        if (player.getNumWalls() > 0) {
            List<PlayerAction> validWallPlacements = wallUtil.findValidWallPlacements(board, player);
            allMoves.addAll(validWallPlacements);
        }
        allMoves.addAll(playerActions);
        return allMoves;
    }

//    public class DummyGameSession implements IGameSession {
//
//        private Board board;
//
//        private List<Player> players;
//
//        private int currentPlayerIndex;
//
//        public DummyGameSession(Board board, List<Player> players, int currentPlayerIndex) {
//            this.board = board;
//            this.players = players;
//            this.currentPlayerIndex = currentPlayerIndex;
//        }
//
//        public DummyGameSession() {
//        }
//
//
//        public void setBoard(Board board) {
//            this.board = board;
//        }
//
//        public void setPlayers(List<Player> players) {
//            this.players = players;
//        }
//
//        @Override
//        public Player getCurrentPlayer() {
//            return players.get(currentPlayerIndex);
//        }
//
//        @Override
//        public LogicResult isCurrentTurn(PawnType pawnType) {
//            return new LogicResult(true);
//        }
//
//        @Override
//        public Board getBoard() {
//            return board;
//        }
//
//        @Override
//        public void checkForWinnerAndUpdateTurn(PlayerAction playerAction) {
//
//        }
//
//        @Override
//        public List<Player> getPlayers() {
//            return players;
//        }
//
//        @Override
//        public int getCurrentPlayerIndex() {
//            return currentPlayerIndex;
//        }
//
//        public void setCurrentPlayerIndex(int currentPlayerIndex) {
//            this.currentPlayerIndex = currentPlayerIndex;
//        }
//
//
//        public Player getPlayer(int playerIndex) {
//            return players.get(playerIndex);
//        }
//    }
}

