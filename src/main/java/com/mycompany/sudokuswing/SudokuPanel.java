
package com.mycompany.sudokuswing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;



public class SudokuPanel extends JPanel{
    
    private SudokuFrame frame;
    
    //Attribute
    private SudokuPuzzle puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
        private int mistake;
        private Timer timer;
        private int secondsPassed = 0;
        private Stack<int[]> moveHistory = new Stack<>();

	//Contructor
	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE, "Easy");
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	public SudokuPanel(SudokuPuzzle puzzle) {
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
        
	//Method
	public void newSudokuPuzzle(SudokuPuzzle puzzle) {
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
        
        public void setFrame(SudokuFrame frame) {
                this.frame = frame;
        }

        public int getCurrentlySelectedCol() {
            return currentlySelectedCol;
        }

        public int getCurrentlySelectedRow() {
            return currentlySelectedRow;
        }
        
        
        public void resetMistakes() {
            this.mistake = 0;
            if (frame != null) {
                frame.updateMistakeLabel(mistake);
            }
        }
        
        public void resetTimer() {
            stopTimer();  
            secondsPassed = 0; 
            updateTimerDisplay();  
        }


        public void startTimer() {
            resetTimer();  
            timer = new Timer(1000, e -> {
                secondsPassed++;
                updateTimerDisplay();
            });
            timer.start();
        }


        public void stopTimer() {
            if (timer != null) {
                timer.stop();
            }
        }

        private void updateTimerDisplay() {
            int minutes = secondsPassed / 60;
            int seconds = secondsPassed % 60;
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);
            if (frame != null) {
                frame.updateTimerLabel(timeFormatted); 
            }
        }
        
        public void undoMove() {
        if (!moveHistory.isEmpty()) {
            int[] lastMove = moveHistory.pop();
            int row = lastMove[0];
            int col = lastMove[1];

            puzzle.board[row][col] = "";
            repaint();
                }
        }
        
        public void resetMoveHistory() {
            moveHistory.clear();
        }
        
        public void deleteValue(){
            if (!moveHistory.isEmpty()) {
                int[] move = moveHistory.pop();
                for (int row = 0; row < move.length; row+=2) {
                    for (int col = 1; col < move.length; col+=2) {
                        int r = move[row];
                        int c = move[col];
                        if (puzzle.board[currentlySelectedRow][currentlySelectedCol].equals(puzzle.board[r][c])) {
                            puzzle.board[currentlySelectedRow][currentlySelectedCol] = "";
                             repaint();
                        }
                    }
                }
            }
            
        }

        
	//Use to draw grid layout
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f)); //white
                
		//Use to calculate width and height of one box by use panel widht and height / numCols and numRows
		int slotWidth = this.getWidth()/puzzle.getNumColumns();
		int slotHeight = this.getHeight()/puzzle.getNumRows();
		
		usedWidth = (this.getWidth()/puzzle.getNumColumns())*puzzle.getNumColumns();
		usedHeight = (this.getHeight()/puzzle.getNumRows())*puzzle.getNumRows();
		
		g2d.fillRect(0, 0,usedWidth,usedHeight);
		
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
                
                //Use to draw vertical line
		for(int x = 0;x <= usedWidth;x+=slotWidth) {
			if((x/slotWidth) % puzzle.getBoxWidth() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
                
                //Use to draw horizontal line
		for(int y = 0;y <= usedHeight;y+=slotHeight) {
			if((y/slotHeight) % puzzle.getBoxHeight() == 0) {
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
                
                //Use to set font, width, height of text in box 
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getNumRows();row++) {
			for(int col=0;col < puzzle.getNumColumns();col++) {
				if(!puzzle.isSlotAvailable(row, col)) {
					int textWidth = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(row, col), fContext).getHeight();
					g2d.drawString(puzzle.getValue(row, col), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
                
                //Use to set color while clicking in box
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
        
                
        public void gameOver(int mistake){
            if (mistake == 3) {
                JOptionPane.showMessageDialog(this, "Game Over!", "Error", JOptionPane.OK_OPTION);
                playAgain();
                this.mistake = 0; // Reset mistake
                if (frame != null) {
                    frame.updateMistakeLabel(this.mistake); 
                }
            }
        }
       
        
        public void messageFromNumActionListener(String buttonValue) {
            if (!puzzle.isValidMove(currentlySelectedRow, currentlySelectedCol, buttonValue)) {             
                        mistake++;
                        frame.updateMistakeLabel(mistake);
                        gameOver(mistake);
            }
            if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
                puzzle.makeMove(currentlySelectedRow, currentlySelectedCol, buttonValue, true);
                moveHistory.push(new int[]{currentlySelectedRow, currentlySelectedCol});
    
                repaint();
                if (puzzle.boardFull()) {
                    int option = JOptionPane.showOptionDialog(
                    this,
                    "Bạn đã chiến thắng! Bạn có muốn chơi lại không?",
                    "Chúc mừng",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Play Again", "Exit"},
                    "Play Again"
                    );       
                    if (option == JOptionPane.YES_OPTION) {
                        playAgain();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
        
        private void playAgain() {
        SudokuPuzzle newPuzzle = new SudokuGenerator().generateRandomSudoku(SudokuPuzzleType.NINEBYNINE, "Medium");
        newSudokuPuzzle(newPuzzle);
        currentlySelectedCol = -1;
        currentlySelectedRow = -1;
        startTimer();
        resetMoveHistory();
        repaint();
    }
        
	
	public class NumActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageFromNumActionListener(((JButton) e.getSource()).getText());	
		}
	}
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				int slotWidth = usedWidth/puzzle.getNumColumns();
				int slotHeight = usedHeight/puzzle.getNumRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
}
