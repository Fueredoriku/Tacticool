package com.anything.tacticool.model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int[] board;
    private int width;
    private int heigth;
    private List<Player> players;
    private boolean turn;
    private boolean isGameWon;
    private int winningPlayer;

    public Grid(String board, int width, int heigth, boolean turn) {
        this.heigth = heigth;
        this.width = width;
        this.board = new int[width * heigth];
        this.turn = turn;
        players = new ArrayList<>();
        String[] boardSplit = board.split(",");
        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = Integer.parseInt(boardSplit[i]);
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setWinState(boolean isGameWon, int playerID) {
        this.isGameWon = isGameWon;
        this.winningPlayer = playerID;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    /**
     * Gets the winning player if the game is won.
     * @throws  IllegalStateException   if the game is not won.
     * @return  a player if he won or null if everyone is dead and it is a tie.
     */
    public Player getWinningPlayer() {
        if (!isGameWon) {
            throw new IllegalStateException("This method should only be called if the game is won");
        }

        for (Player player : players) {
            if (player.getPlayerID() == winningPlayer) {
                return player;
            }
        }
        return null;
    }

    public int[] getBoard() {
        return board;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int playerID) {
        for (Player player : players) {
            if (player.getPlayerID() == playerID) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i : board) {
            result += String.format("%d,", i);
        }
        result += "\n" + turn;
        return result + "\n" + players.toString();
    }
}
