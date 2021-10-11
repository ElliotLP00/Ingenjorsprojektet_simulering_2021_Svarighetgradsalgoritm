import javax.swing.*;

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

        }

        private class nodePanel {
        }

        private class controlPanel {
        }
    }
}
