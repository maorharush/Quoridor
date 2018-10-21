
package com.harush.zitoon.quoridor.core.theirs;


import java.io.*;


/**
 * Provide a command line interface to a human player.
 * 
 * @author Joey Tuong
 * @author Luke Pearson
 *
 */
public class Human extends Player {
    
    private boolean suppressPrompt;
    
    public Human() {
        super();
        suppressPrompt = false;
    }
    
    @Override
    public void onFailure(Board b) {
        System.out.println("Invalid move!");
    }
    
    @Override
    public String nextMove(Board b) {
        String temp = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        b.printBoard();
        System.out.print("Enter the move for player "
                + this.getID().toString() + ": ");
        try {
            temp = in.readLine();
        } catch (IOException ignored) {
        }
        return temp;
    }
    
}
