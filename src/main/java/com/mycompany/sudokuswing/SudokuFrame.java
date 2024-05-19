package com.mycompany.sudokuswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public final class SudokuFrame extends JFrame {

    private final JPanel buttonSelectionPanel;
    private final SudokuPanel sPanel;
    private JLabel lTimerJLabel;
    private JLabel mistakeJLabel;
    private JButton undoButton ;

    public SudokuFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800, 600));
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
        this.setJMenuBar(menuBar);

        //Create Panel
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800, 600));

        //Create button
        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(200, 500));
        
        //Create SudokuPanel
        sPanel = new SudokuPanel();
        sPanel.setFrame(this);

        // Create counting time
        lTimerJLabel = new JLabel("00:00");
        
        //Create mistake
        mistakeJLabel = new JLabel("Mistakes: 0/3");
        
        //Create undo
        undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              sPanel.undoMove();
          }
      });
        
        //Add this to frame
        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        windowPanel.add(lTimerJLabel);    
        windowPanel.add(mistakeJLabel);
        windowPanel.add(undoButton);
        this.add(windowPanel);
        //Use to create new game when openning game (defalut 9x9, font size 26, mode: easy)
        rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26, group);

    }
    
    
    public void updateMistakeLabel(int mistakes) {
           mistakeJLabel.setText("Mistakes: " + mistakes + "/3");
    }
    
    public void updateTimerLabel(String time) {
        lTimerJLabel.setText(time);
    }


//    //Like above
    public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize, ButtonGroup group) {
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType, group.getSelection().getActionCommand());
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        sPanel.resetMistakes() ;
        buttonSelectionPanel.removeAll();
        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
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
        sPanel.repaint();
        sPanel.startTimer();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    //Create new game with option(SudokuPuzzleType(6x6,9x9,12x12))
    private class NewGameListener implements ActionListener {

        private final SudokuPuzzleType puzzleType;
        private final int fontSize;
        private ButtonGroup mode;

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SudokuFrame frame = new SudokuFrame();
                frame.setVisible(true);
            }
        });
    }
}
