package com.mycompany.sudokuswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class SudokuFrame extends JFrame {
    private final JPanel buttonSelectionPanel;
    private final SudokuPanel sPanel;
    private final JLabel lTimerJLabel;
    private final JLabel mistakeJLabel;
    private final JButton btnUndo ;
    private final JButton btnDelete;
    private final JButton btnHint;
    private Clip clip;
    private Sound sound = new Sound();
    private JSlider slider;
    private JLabel modelb;
    private FunctionButton btnPaint;
    private JLabel lbNewGame;
    private FunctionButton btnPrize;
    private FunctionButton btnSetting;
    private FunctionButton btnPauseGame;
    private JToggleButton btnNote;
    private FunctionButton drop;
    private JLabel lbHint;
    private JLabel lbPencil;
    private JLabel lbErase;
    private JLabel lbUndo;
    private boolean noteStatus;
    private Font font18 = new Font("Time New Roman",Font.PLAIN,18);
    private Font font20 = new Font("Time New Roman",Font.PLAIN,20);
    


    public SudokuFrame() {
        SudokuFrame frame = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(700, 800));
        this.setLocationRelativeTo(null);
        //Create menuBar
        JMenuBar menuBar = new JMenuBar();

        //Create Item of Menu
        JMenu mode = new JMenu("Mode");
        JMenu audio = new JMenu("Audio");
        ButtonGroup group = new ButtonGroup();

        //Create child of item (JMenu > JMenuItem)
        JRadioButton easy = new JRadioButton("Easy");
        easy.setActionCommand("Easy");
        JRadioButton medium = new JRadioButton("Medium", true);
        medium.setActionCommand("Medium");
        JRadioButton hard = new JRadioButton("Hard");
        hard.setActionCommand("Hard");
        
        //audio setting
        JMenuItem audioSetting = new JMenuItem("Setting");
        audioSetting.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame setting = new JFrame();
                setting.setLayout(new FlowLayout());
                setting.setLocationRelativeTo(null);
               
                
                slider = new JSlider(-40, 6);
                
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        sound.currentVolume = slider.getValue();
                        if (sound.currentVolume == -40) {
                            sound.currentVolume = -80;
                        }
                        sound.fc.setValue(sound.currentVolume);
                    }
                });
                FunctionButton volumeMute = new FunctionButton(createResizedIcon("sounds/volume.png", 30, 30));
                volumeMute.setOpaque(false);
                volumeMute.setBorderPainted(false);
                volumeMute.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sound.volumeMute(slider);
                        if (sound.isMute()==false) {  
                            volumeMute.setIcon(createResizedIcon("sounds/volume.png", 30, 30));
                        }else{
                            volumeMute.setIcon(createResizedIcon("sounds/mute.png", 30, 30));
                        }
                    }
                }); 
                
                setting.add(volumeMute);
                setting.add(slider);
                setting.pack();
                setting.setVisible(true);
            }
            
        });
        //Add this to menu
        group.add(easy);
        group.add(medium);
        group.add(hard);
        mode.add(easy);
        mode.add(medium);
        mode.add(hard);
        audio.add(audioSetting);
        menuBar.add(mode);
        menuBar.add(audio);
        frame.setJMenuBar(menuBar);

        //Create Panel
        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        windowPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        //Create button
        buttonSelectionPanel = new JPanel();
        
        //Create SudokuPanel
        sPanel = new SudokuPanel();
        sPanel.setFrame(frame);

        //Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,3));
        
        modelb = new JLabel();
        modelb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        modelb.setFont(font18);
        
        btnPaint = new FunctionButton(createResizedIcon("icons/paint.png", 30, 30));
        btnPaint.setFocusable(false);
        btnPaint.setHorizontalAlignment(SwingConstants.LEFT);
        
        lTimerJLabel = new JLabel("00:00");
        lTimerJLabel.setFont(font18);
        lTimerJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        mistakeJLabel = new JLabel("Mistakes: 0/3");
        mistakeJLabel.setFont(font18);
        
        lbNewGame = new JLabel("New Game");
        lbNewGame.setFont(font20);
        
        btnPrize = new FunctionButton(createResizedIcon("icons/prize.png", 30, 30));
        
        btnSetting = new FunctionButton(createResizedIcon("icons/settings.png", 30, 30));
        
        btnPauseGame = new FunctionButton(createResizedIcon("icons/pause.png", 20, 20));
        createPauseAction();
        
        drop = new FunctionButton(createResizedIcon("icons/drop.png", 20, 20));
        drop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem sixBySixGame  = new JMenuItem("6 By 6 Game");
                JMenuItem nineByNineGame  = new JMenuItem("9 By 9 Game");
                JMenuItem twelveByTwelveGame  = new JMenuItem("12 By 12 Game");
                
                sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX, 30, group));
                nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE, 26, group));
                twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE, 20, group));
                
                JRadioButton easy = new JRadioButton("Easy");
                easy.setActionCommand("Easy");
                JRadioButton medium = new JRadioButton("Medium", true);
                medium.setActionCommand("Medium");
                JRadioButton hard = new JRadioButton("Hard");
                hard.setActionCommand("Hard");


                popupMenu.add(sixBySixGame);
                popupMenu.add(nineByNineGame);
                popupMenu.add(twelveByTwelveGame);
                popupMenu.show(lbNewGame, 0, lbNewGame.getHeight());
            }
        });
        
        JPanel nullPanel1 = new JPanel();
        nullPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nullPanel1.add(btnPrize);
        nullPanel1.add(btnSetting);
        JPanel nullPane2 = new JPanel();
        nullPane2.setLayout(new FlowLayout(FlowLayout.CENTER));
        nullPane2.add(lbNewGame);
        nullPane2.add(drop);
        JPanel nullJPanel3 = new JPanel();
        nullJPanel3.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nullJPanel3.add(lTimerJLabel);
        nullJPanel3.add(btnPauseGame);
        
        topPanel.add(btnPaint);
        topPanel.add(nullPane2);
        topPanel.add(nullPanel1);
        topPanel.add(mistakeJLabel);
        topPanel.add(modelb);
        topPanel.add(nullJPanel3);
        
        //tool panel
        JPanel bottomPanel = new JPanel();
        //bottomPanel.setPreferredSize(new Dimension(100,100));
        bottomPanel.setLayout(new GridLayout(2, 4, 50, 0));
        
        btnUndo = new JButton(createResizedIcon("icons/undo.png", 30, 30));
        createUndoAction();

        btnDelete = new JButton(createResizedIcon("icons/eraser.png", 30, 30));
        createDeleteAction();
        
        btnHint = new JButton(createResizedIcon("icons/bulb.png", 40, 40));
        createHintAction();
        
        btnNote = new JToggleButton(createResizedIcon("icons/pencil.png", 30, 30));
        createNoteAction();
        
        lbUndo = new JLabel("Undo");
        lbUndo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUndo.setFont(font18);
        lbErase = new JLabel("Erase");
        lbErase.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbErase.setFont(font18);
        lbPencil = new JLabel("Pencil off");
        lbPencil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPencil.setFont(font18);
        lbHint = new JLabel();
        lbHint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHint.setFont(font18);
        
        bottomPanel.add(btnUndo);
        bottomPanel.add(btnDelete);
        bottomPanel.add(btnNote);
        bottomPanel.add(btnHint);
        bottomPanel.add(lbUndo);
        bottomPanel.add(lbErase);
        bottomPanel.add(lbPencil);
        bottomPanel.add(lbHint);
 
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(sPanel,BorderLayout.CENTER);
        centerPanel.add(bottomPanel,BorderLayout.SOUTH);
        //Add this to frame
        windowPanel.add(centerPanel,BorderLayout.CENTER);
        windowPanel.add(topPanel,BorderLayout.NORTH);    
        windowPanel.add(buttonSelectionPanel,BorderLayout.SOUTH);
        this.add(windowPanel);
        //Use to create new game when openning game (defalut 9x9, font size 26, mode: easy)
        rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26, group);
        playMusic("sounds/gamemusic.wav");
    }

    
    private void createUndoAction() {
        btnUndo.addActionListener((ActionEvent e) -> {
            playSound("sounds/button.wav");
            sPanel.undoMove();
        });
    }
    private void createPauseAction() {
        btnPauseGame.addActionListener((ActionEvent e) -> {
            sound.stop();
            playSound("sounds/pausing.wav");
            btnPauseGame.setIcon(createResizedIcon("icons/play.png", 20, 20));
            if (sPanel.getTimerState()) {
                sPanel.pauseTimer();
                
                // Create a JOptionPane with a custom OK button
                JOptionPane optionPane = new JOptionPane(
                    "Game is pausing",
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[] {"OK"},  // Custom options with only an OK button
                    "OK"  // Default button
                );

                // Create a JDialog from the JOptionPane
                JDialog dialog = optionPane.createDialog(this, "Pause");
                dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

                // Add an action listener to the OK button
                optionPane.addPropertyChangeListener(evt -> {
                    String prop = evt.getPropertyName();

                    if (dialog.isActive()
                            && (evt.getSource() == optionPane)
                            && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                        // Resume the timer when OK is clicked
                        sPanel.resumeTimer();
                        dialog.dispose();
                        playSound("sounds/button.wav");
                        playMusic("sounds/gamemusic.wav");
                        btnPauseGame.setIcon(createResizedIcon("icons/pause.png", 20, 20));
                    }
                });

                dialog.setVisible(true);
            }
        });
    }
    public void updateMistakeLabel(int mistakes) {
           mistakeJLabel.setText("Mistakes: " + mistakes + "/3");
    }
    
    public void updateTimerLabel(String time) {
        lTimerJLabel.setText(time);
    }
    
    public void createDeleteAction(){
        btnDelete.addActionListener((ActionEvent e) -> {
            playSound("sounds/button.wav");
            sPanel.deleteValue();
        });
    }

    public void createHintAction(){
        btnHint.addActionListener((ActionEvent e) -> {
            playSound("sounds/hint.wav");
            if (sPanel.getHint() < 5) {
                sPanel.autoFill();
            }else{
                JOptionPane.showMessageDialog(sPanel, "Đã hết số lần gợi ý");
            }
        });
    }
    
    public void createNoteAction(){
        btnNote.addActionListener((ActionEvent e) -> {
            playSound("sounds/button.wav");
            if (btnNote.isSelected()) {
                noteStatus = true;
                lbPencil.setText("Pencil On");
            }else{
                noteStatus = false;
                lbPencil.setText("Pencil Off");
            }
        });
    }

    public boolean isNoteStatus() {
        return noteStatus;
    }
    
    public void updateHint(int hint){
        lbHint.setText("Hint: " + hint + "/5");
    }
    

//    //Like above
   public void rebuildInterface(SudokuPuzzleType puzzleType, int fontSize, ButtonGroup group) {
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType, group.getSelection().getActionCommand());
        modelb.setText(group.getSelection().getActionCommand());
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        sPanel.resetMistakes();
        sPanel.resetHint();
        sPanel.resetTimer();
        updateHint(sPanel.getHint());
        buttonSelectionPanel.removeAll();
        for (String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            Font numberFont = new Font("Times New Roman", Font.PLAIN,40);
            b.setFont(numberFont);
            b.setContentAreaFilled(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setFocusable(false);
            b.setForeground(Color.BLUE);
            b.setBackground(Color.white);
            b.setPreferredSize(new Dimension(40, 50));
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
        sPanel.resetMoveHistory();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();        
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
    
    public void playMusic(String path){
        sound.setFile(path);
        sound.play();
        sound.loop();
    }
    
    public void playSound(String soundFile) {
        try {
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }
    
   private static ImageIcon createResizedIcon(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    
    //Main: use to run this frame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuFrame frame = new SudokuFrame();
            frame.setVisible(true);
        });
    }
}
