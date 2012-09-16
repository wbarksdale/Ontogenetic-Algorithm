/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

/**
 *
 * @author wfbarksdale
 */
public class Piece {
    public static final int PAWN = 0;
    public static final int ROOK = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int QUEEN = 4;
    public static final int KING = 5;
    
    public boolean lastMovedPiece = false;
    public int moveCount = 0;
    public int color;
    public int type;
    
    public Piece(int t, int c){
        color = c;
        type = t;
    }
    
}
