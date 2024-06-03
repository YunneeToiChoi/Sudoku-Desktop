package com.mycompany.sudokuswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public final class SudokuFrame extends JFrame {
    private final JPanel buttonSelectionPanel;
    private final SudokuPanel sPanel;
    private final JLabel lTimerJLabel;
    private final JLabel mistakeJLabel;
    private final JButton undoButton ;
    private final JButton delete;
    private final JPanel notePanel;
    private final JButton btnTakeNote;
    private final JButton btnHint;
    private final JLabel lbHint;
    public JTextField[][] cells;
   

    public SudokuFrame() {
        SudokuFrame frame = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(1000, 600));
        //Create menuBar
        JMenuBar menuBar = new JMenuBar();

        //Create Item of Menu
        JMenu file = new JMenu("Game");
        JMenu newGame = new JMenu("New Game");
        JMenu mode = new JMenu("Mode");
        ButtonGroup group = new ButtonGroup();

        //Create child of item (JMenu > JMenuItem)
        JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game");
        sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX, 30, group));
        JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
        nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE, 26, group));
        JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");
        twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE, 20, group));
        JRadioButton easy = new JRadioButton("Easy");
        easy.setActionCommand("Easy");
        JRadioButton medium = new JRadioButton("Medium", true);
        medium.setActionCommand("Medium");
        JRadioButton hard = new JRadioButton("Hard");
        hard.setActionCommand("Hard");
        
        //Add this to menu
        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);
        newGame.add(twelveByTwelveGame);
        group.add(easy);
        group.add(medium);
        group.add(hard);
        mode.add(easy);
        mode.add(medium);
        mode.add(hard);
        file.add(newGame);
        menuBar.add(file);
        menuBar.add(mode);
        frame.setJMenuBar(menuBar);

        //Create Panel
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        windowPanel.setPreferredSize(new Dimension(1000, 600));
        
        //Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(500,50));
        
        //Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        JPanel nullPanel = new JPanel();
        nullPanel.setLayout(new FlowLayout());
        

        //Create button
        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(200, 200));
        
        
        //Create SudokuPanel
        sPanel = new SudokuPanel();
        sPanel.setFrame(frame);

        // Create counting time
        lTimerJLabel = new JLabel("00:00");
        lTimerJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Font font = new Font("Time New Roman", Font.PLAIN, 30);
        lTimerJLabel.setFont(font);
        
        //Create mistake
        mistakeJLabel = new JLabel("Mistakes: 0/3");
        mistakeJLabel.setFont(new Font("Roboto",Font.PLAIN,26));

        //Create undo
        undoButton = new JButton("Undo");
        undoButton.setBackground(Color.white);
        undoButton.setPreferredSize(new Dimension(100, 50));
        createUndoAction();
//        undoButton.addActionListener(new ActionListener() {
//          public void actionPerformed(ActionEvent e) {
//              sPanel.undoMove();
//          }
//      });
        
        
        //
        notePanel = new JPanel();
        notePanel.setPreferredSize(new Dimension(200,200));
        
        JLabel noteTitle = new JLabel("Press ENTER to take notes !");
        noteTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        btnTakeNote = new JButton("Take Note");
        createTakeNoteAction();

        delete = new JButton("Delete");
        createDeleteAction();
        
        lbHint = new JLabel("Hint: 0/5");
        
        btnHint = new JButton("Hint");
        createHintAction();
        
        
        nullPanel.add(notePanel);
        rightPanel.add(nullPanel,BorderLayout.CENTER);
        rightPanel.add(noteTitle,BorderLayout.NORTH);
        //
        bottomPanel.add(mistakeJLabel);
        bottomPanel.add(delete);
        bottomPanel.add(undoButton);
        bottomPanel.add(btnTakeNote);
        bottomPanel.add(btnHint);
        bottomPanel.add(lbHint);
        //Add this to frame
        windowPanel.add(sPanel,BorderLayout.CENTER);
        windowPanel.add(buttonSelectionPanel,BorderLayout.WEST);
        windowPanel.add(rightPanel, BorderLayout.EAST);
        windowPanel.add(lTimerJLabel,BorderLayout.NORTH);    
        windowPanel.add(bottomPanel,BorderLayout.SOUTH);
        
        this.add(windowPanel);
        //Use to create new game when openning game (defalut 9x9, font size 26, mode: easy)
        rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26, group);

    }

    
    private void createUndoAction() {
        undoButton.addActionListener((ActionEvent e) -> {
            sPanel.undoMove();
        });
    }

    
    
    public void updateMistakeLabel(int mistakes) {
           mistakeJLabel.setText("Mistakes: " + mistakes + "/3");
    }
    
    public void updateTimerLabel(String time) {
        lTimerJLabel.setText(time);
    }
    
    public void createDeleteAction(){
        delete.addActionListener((ActionEvent e) -> {
            sPanel.deleteValue();
        });
    }

    public void createTakeNoteAction(){
        btnTakeNote.addActionListener((ActionEvent e) -> {
            if (sPanel.getCurrentlySelectedRow() == -1 && sPanel.getCurrentlySelectedCol() == -1) {
                JOptionPane.showMessageDialog(sPanel, "Mời chọn ô để ghi chú");
            }else{
                JOptionPane.showMessageDialog(sPanel, "Nhấn Enter để ghi chú ô");
                cells[sPanel.getCurrentlySelectedRow()][sPanel.getCurrentlySelectedCol()].requestFocus();
            }
        });
    }

    public void createHintAction(){
        btnHint.addActionListener((ActionEvent e) -> {
            if (sPanel.hint < 5) {
                sPanel.autoFill();
            }else{
                JOptionPane.showMessageDialog(sPanel, "Đã hết số lần gợi ý");
            }
        });
    }
    
    public void updateHint(int hint){
        lbHint.setText("Hint: " + hint + "/5");
    }

//    //Like above
   public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize, ButtonGroup group) {
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType, group.getSelection().getActionCommand());
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        sPanel.resetMistakes();
        buttonSelectionPanel.removeAll();
        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            b.setBackground(Color.white);
            b.setPreferredSize(new Dimension(50, 50));
            b.addActionListener(sPanel.new NumActionListener());
            b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(value), value);
            b.getActionMap().put(value, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    b.doClick();
                }
            });
            buttonSelectionPanel.add(b);
        }
        
        
        //take note
        notePanel.setLayout(new GridLayout(generatedPuzzle.getNumRows(),generatedPuzzle.getNumColumns()));
        cells = new JTextField[generatedPuzzle.getNumRows()][generatedPuzzle.getNumColumns()];
        notePanel.removeAll();
        for (int i = 0; i < generatedPuzzle.getNumRows(); i++) {
            for (int j = 0; j < generatedPuzzle.getNumColumns(); j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setEditable(false);
                notePanel.add(cells[i][j]);
            }
            
        }
        for (int i = 0; i < generatedPuzzle.getNumRows(); i++) {
            for (int j = 0; j < generatedPuzzle.getNumColumns(); j++) {
                cells[i][j].addActionListener((ActionEvent e) -> {
                    JTextField source = (JTextField) e.getSource();
                    String oldNote = source.getText();
                    String note = JOptionPane.showInputDialog(sPanel, "Ghi chú: " + oldNote, "Ghi chú ô Sudoku", JOptionPane.PLAIN_MESSAGE);
                    if (note != null) {
                        source.setText(oldNote + note);  
                    }
                });
            }
        }
        
        sPanel.repaint();
        sPanel.startTimer();
        sPanel.resetMoveHistory();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
        notePanel.revalidate();
        notePanel.repaint();
        
    }


    //Create new game with option(SudokuPuzzleType(6x6,9x9,12x12))
    private class NewGameListener implements ActionListener {

        private final SudokuPuzzleType puzzleType;
        private final int fontSize;
        private final ButtonGroup mode;

        public NewGameListener(SudokuPuzzleType puzzleType, int fontSize, ButtonGroup mode) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
            this.mode = mode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType, fontSize, mode);
        }
    }
    
    //Main: use to run this frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuFrame frame = new SudokuFrame();
            frame.setVisible(true);
        });
    }
}
