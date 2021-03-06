import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

public class mainFrame {
    private JPanel mainPanel;
    private Node node;
    private JButton nodeButton;
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
            public nodePanel(int w, int h){
                setPreferredSize(new Dimension(w,h));
                setAlignmentX(Component.CENTER_ALIGNMENT);
                setAlignmentY(Component.CENTER_ALIGNMENT);
                nodeButton = new JButton("READY");

                nodeButton.addMouseListener(new MouseAdapter() {
                    private int eventCnt = 0;
                    java.util.Timer timer = new java.util.Timer("doubleClickTimer", false);

                    @Override
                    public void mouseClicked(final MouseEvent e) {
                        eventCnt = e.getClickCount();
                        if ( e.getClickCount() == 1 ) {
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                   if ( eventCnt == 2 ) {
                                        System.err.println("you double clicked. Sending Start game to others");
                                        node.analyzeMessage(new Message(6,""));
                                    }
                                    eventCnt = 0;
                                }
                            }, 400);
                        }
                    }
                });
                node.setButton(nodeButton);
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
                        Random ra = new Random();
                        if(ra.nextBoolean()){
                            data = "Mole time exceeded";
                        }else{
                            data = "Incoming Mole miss";
                        }

                        break;
                }
                Message m = new Message(type,data);
                System.out.println(m.getMessageType()+", data:"+m.getMessageType());
                node.analyzeMessage(m);

            }
        }
    }
}
