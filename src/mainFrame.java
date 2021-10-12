import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;

public class mainFrame {
    private JPanel mainPanel;
    private Node node;
    public mainFrame(Node node) {
        this.node = node;
        mainPanel = new mainPanel();
        JFrame frame = new JFrame("Sick wack-a-mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setLocation(-1040, 0);
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
            JButton nodeButton;
            public nodePanel(int w, int h){
                setPreferredSize(new Dimension(w,h));
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setAlignmentY(Component.CENTER_ALIGNMENT);
                nodeButton = new JButton("READY");
                nodeButton.setPreferredSize(new Dimension(w*8/9,h*8/9));
                add(nodeButton);
            }
        }



        private class controlPanel extends JPanel implements ActionListener {
            private LinkedList<JButton> buttons;
            public controlPanel(int w, int h){
                setPreferredSize(new Dimension(w,h));
                buttons = new LinkedList<>();
                for(int i = 1; i<9; i++){
                    String s = "Receive " + i;
                    JButton buttonConnect = new JButton(s);
                    buttons.add(buttonConnect);
                }
                /*
                for(int i = 1; i<10; i++){
                    String s = "Send " + i;
                    JButton buttonConnect = new JButton(s);
                    buttons.add(buttonConnect);
                }*/

                for (JButton b : buttons) {
                    b.setPreferredSize(new Dimension(getPreferredSize().width - 20, (getPreferredSize().height-100)/(buttons.size())));
                    b.setFont(new Font("Arial", Font.PLAIN, 10));
                    b.addActionListener(this);
                    add(b);
                }

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButton button = (JButton) actionEvent.getSource();
                String text = button.getText();
                int type = Integer.parseInt(String.valueOf(text.charAt(text.length()-1)));
                String data = "";
                switch (type){
                    //case 1,3,6,7,8 dose not need to send any data only type.
                    case 2:
                        data = "From server, To node_ID";
                        break;
                    case 4:
                        Random r = new Random();
                        int i = r.nextInt(3)+1;
                        data = ""+i;
                        break;
                    case 5:
                        data = "-1";
                        break;
                }
                Message m = new Message(type,data);
                System.out.println(m.getMessageType()+", data:"+m.getMessageType());
                node.analyzeMessage(m);

            }
        }
    }
}
