import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class mainFrame {
    public mainFrame() {
        JFrame frame = new JFrame("Sick wack-a-mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new mainPanel());
        frame.setLocation(-1030, 0);
        frame.pack();
        frame.setVisible(true);
    }

    public class mainPanel extends JPanel {
        private nodePanel nodePanel;
        private controlPanel controlPanel;
        public mainPanel(){
            nodePanel = new nodePanel(600,600);
            controlPanel = new controlPanel(200,600);
            add(nodePanel);
            add(controlPanel);
        }

        private class nodePanel extends JPanel{
            public nodePanel(int w, int h){
                setPreferredSize(new Dimension(w,h));
            }
        }

        private class controlPanel extends JPanel implements ActionListener {
            private LinkedList<JButton> buttons;
            public controlPanel(int w, int h){
                setPreferredSize(new Dimension(w,h));
                buttons = new LinkedList<>();
                JButton buttonConnect = new JButton("Connect a node");
                buttons.add(buttonConnect);

                JButton buttonDisconnectChild = new JButton("Disconnect a child node");
                buttons.add(buttonDisconnectChild);

                JButton buttonDisconnectParent = new JButton("Disconnect a parent node");
                buttons.add(buttonDisconnectParent);

                JButton buttonDisconnectHead = new JButton("Disconnect a head node");
                buttons.add(buttonDisconnectHead);

                JButton buttonStart = new JButton("Start game");
                buttons.add(buttonStart);

                JButton buttonExit = new JButton("Exit game");
                buttons.add(buttonExit);

                JButton buttonReset = new JButton("Reset");
                buttons.add(buttonReset);

                for (JButton b : buttons) {
                    b.setPreferredSize(new Dimension(getPreferredSize().width - 20, 50));
                    b.setFont(new Font("Arial", Font.PLAIN, 10));
                    b.addActionListener(this);
                }
            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        }
    }
}
