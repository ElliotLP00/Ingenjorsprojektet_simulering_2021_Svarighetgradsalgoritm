import javax.swing.*;

public class Node {
    private mainFrame frame;
    private int difficulty;
    private int connectedNodes;
    private int activeMoles;
    private boolean gameActive;
    private JButton button;

    public static void main(String[] args) {
        new Node();
    }
    public Node() {
        frame = new mainFrame(this);
    }

    public void analyzeMessage(Message message) {

        switch (message.getMessageType()){
            case 1:
                System.out.println("Incoming New node.");
                connectedNodes++;
                System.out.println("Total:"+connectedNodes);
                break;
            case 2:
                System.out.println("Incoming Keep alive. Sending back answer");
                Message m = new Message(2,"From:this, To:message.getSentFrom");
                //send(Stirng to = message.getSentFrom(), Message message)
                break;
            case 3:
                if(gameActive){
                    System.out.println("Incoming Mole active");
                    activeMoles++;
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 4:
                if(gameActive){
                    System.out.println("Incoming Mole hit.");
                    String data = message.getData();
                    difficulty += Integer.parseInt(data);
                    activeMoles--;
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 5:
                if(gameActive){
                    System.out.println("Incoming Mole miss");
                    if(difficulty > 0)
                        difficulty--;
                    if(message.getData()=="Mole time exceeded"){
                        activeMoles--;
                    }
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 6:
                if(!gameActive && connectedNodes >= 4){
                    System.out.println("Incoming Start game");
                    activeMoles = 0;
                    gameActive = true;
                    new Game().start();
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 7:
                if (gameActive){
                    System.out.println("Incoming Stop game");
                    gameActive = false;
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 8:
                System.out.println("Incoming Node disconnected");
                connectedNodes--;
                System.out.println("Total:"+connectedNodes);
                break;
        }
    }

    public void setButton(JButton nodeButton) {
        button = nodeButton;
    }

    private class Game extends Thread{
        private float timeLeft;
        private int minCooldowntime;
        private int maxCooldowntime;
        private boolean active;

        public Game(){

        }

        @Override
        public void run() {
            super.run();
            while(gameActive){
                System.out.println("Game still in progress");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Game stopped");
        }
    }
}
