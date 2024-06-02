
package com.mycompany.sudokuswing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//TO DO: Create method getSolvedSudoku to compare with btnValue

public class SudokuGenerator {
    
    //Create random sudoku
    //String mode
    public SudokuPuzzle generateRandomSudoku(SudokuPuzzleType puzzleType, String mode) {
		SudokuPuzzle puzzle = new SudokuPuzzle(puzzleType.getRows(), puzzleType.getColumns(), puzzleType.getBoxWidth(), puzzleType.getBoxHeight(), puzzleType.getValidValues());
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);
		Random randomGenerator = new Random();
                
		//Use to get valid value (see in enum SudokuPuzzleType)
		List<String> notUsedValidValues =  new ArrayList<> (Arrays.asList(copy.getValidValues()));
		for(int r = 0;r < copy.getNumRows();r++) {
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			copy.makeMove(r, 0, notUsedValidValues.get(randomValue), false);
			notUsedValidValues.remove(randomValue);
		}
                
		//Resolve this
		backtrackSudokuSolver(0, 0, copy);
		puzzle.setSolution(copy.getBoard());
		//random number to keep
                //Optional: can be add if according to mode(easy,medium,hard) instead of 0.55555555
                int numberOfValuesToKeep;
                numberOfValuesToKeep = (int) (
					switch (mode) 
					{
						case "Easy" -> 0.7777777777*(copy.getNumRows()*copy.getNumRows());
						case "Medium" -> 0.5555555555*(copy.getNumRows()*copy.getNumRows());
						default -> 0.3333333333*(copy.getNumRows()*copy.getNumRows());
       				});
           
		//random row and randow col to fill number
		for(int i = 0;i < numberOfValuesToKeep;) {
			int randomRow = randomGenerator.nextInt(puzzle.getNumRows());
			int randomColumn = randomGenerator.nextInt(puzzle.getNumColumns());
			if(puzzle.isSlotAvailable(randomRow, randomColumn)) {
				puzzle.makeMove(randomRow, randomColumn, copy.getValue(randomRow, randomColumn), false);
				i++;
			}
		}
		return puzzle;
	}
	/**
	 * Solves the Sudoku puzzle
	 * @param r: the current row
	 * @param c: the current column
	 * @return valid move or not or done
	 */
    
    private boolean backtrackSudokuSolver(int r,int c,SudokuPuzzle puzzle) {
    	//If the move is not valid return false
		if(!puzzle.inRange(r,c)) {
			return false;
		}
		
		//if the current space is empty
		if(puzzle.isSlotAvailable(r, c)) {
			
                    //loop to find the correct value for the space
                    for (String validValue : puzzle.getValidValues()) {
                        //if the current number works in the space
                        if (!puzzle.numInRow(r, validValue) && !puzzle.numInCol(c, validValue) && !puzzle.numInBox(r, c, validValue)) {
                            //make the move
                            puzzle.makeMove(r, c, validValue, true);
                            //if puzzle solved return true
                            if(puzzle.boardFull()) {
                                System.out.println(puzzle);
                                return true;
                            }
                            //go to next move
                            if(r == puzzle.getNumRows() - 1) {
                                if(backtrackSudokuSolver(0,c + 1,puzzle)) return true;
                            } else {
                                if(backtrackSudokuSolver(r + 1,c,puzzle)) return true;
                            }
                        }
                    }
		}
		
		//if the current space is not empty
		else {
			//got to the next move
			if(r == puzzle.getNumRows() - 1) {
				return backtrackSudokuSolver(0,c + 1,puzzle);
			} else {
				return backtrackSudokuSolver(r + 1,c,puzzle);
			}
		}
		
		//undo move
		puzzle.makeSlotEmpty(r, c);
		//backtrack
		return false;
	}

    }

