package blake.rps;
/*******************************************************************
 *  RpsSwingInterface class
 *  Description: This is the class where all of the interface and
 *  running code is, main class is here.
 *******************************************************************/

// Imported Libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class RpsSwingInterface extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    public static JTextArea textarea = new JTextArea();

    public RpsSwingInterface() {
        // set flow layout for the frame
        this.getContentPane().setLayout(new FlowLayout());
        // create play panel
        JPanel playPanel = new JPanel();
        JButton rock = new JButton("Rock");
        JButton paper = new JButton("Paper");
        JButton scissors = new JButton("Scissors");
        // set action listeners for buttons
        rock.addActionListener(this);
        paper.addActionListener(this);
        scissors.addActionListener(this);
        // add buttons to the frame
        playPanel.add(rock);
        playPanel.add(paper);
        playPanel.add(scissors);
        // create scroll pane
        JScrollPane scrollPane = new JScrollPane(textarea);
        scrollPane.setPreferredSize(new Dimension(580, 250));
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setEditable(false);
        textarea.setMargin( new Insets(5,5,5,5) );
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret) textarea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        // create save panel
        JPanel savePanel = new JPanel();
        JButton save = new JButton("Save Results");
        save.addActionListener(this);
        savePanel.add(save);
        // adding components to the frame.
        this.getContentPane().add(BorderLayout.NORTH, playPanel);
        this.getContentPane().add(BorderLayout.CENTER, scrollPane);
        this.getContentPane().add(BorderLayout.SOUTH, savePanel);
        // add initial message to text area
        textarea.setText("------------------------------------------------------\nLet's play Roshambo! Press the buttons above to begin.\n");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        RpsLogic.setNeededVariables();
        if (action.equals("Rock")) {
            //System.out.println("Rock Button pressed!");
            RpsLogic.getCurrentPlaySession("Rock");
        }
        else if (action.equals("Paper")) {
            //System.out.println("Paper Button pressed!");
            RpsLogic.getCurrentPlaySession("Paper");
        }
        else if (action.equals("Scissors")) {
            //System.out.println("Scissors Button pressed!");
            RpsLogic.getCurrentPlaySession("Scissors");
        }
        else if (action.equals("Save Results")) {
            RpsJsonTools.saveToFile();
        }
    }

    private static void createAndShowGUI() {
        // create and set up the window
        JTextArea ta = new JTextArea();
        JFrame frame = new RpsSwingInterface();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 390));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("Roshambo");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}

