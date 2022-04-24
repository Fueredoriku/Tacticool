package com.anything.tacticool.model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int[] board;
    private int width;
    private int heigth;
    private List<Player> players;
    private boolean turn;

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

    public List<Player> getPlayers() {
        return players;
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
