package Database;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Tran Giap
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String[][] board;
    private final boolean[][] mutable;
    private final int secondsPassed;
    private final int mistake;
    private final int hint;

    public GameState(String[][] board, boolean[][] mutable, int secondsPassed, int mistake, int hint) {
        this.board = board;
        this.mutable = mutable;
        this.secondsPassed = secondsPassed;
        this.mistake = mistake;
        this.hint = hint;
    }

    public String[][] getBoard() {
        return board;
    }

    public boolean[][] getMutable() {
        return mutable;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public int getMistake() {
        return mistake;
    }

    public int getHint() {
        return hint;
    }
    
     @Override
    public String toString() {
        return "GameState{" +
                "board=" + Arrays.deepToString(board) +
                ", mutable=" + Arrays.deepToString(mutable) +
                ", secondsPassed=" + secondsPassed +
                ", mistake=" + mistake +
                ", hint=" + hint +
                '}';
    }
}
