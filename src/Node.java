import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
                if(activeMoles < 4 && gameActive){
                    System.out.println("Incoming Mole active");
                    activeMoles++;
                }else{
                    System.err.println("NOT POSSIBLE IN THIS STATE");
                }
                break;
            case 4:
                if(activeMoles > 0 && gameActive){
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
                    if(message.getData()=="Mole time exceeded" && activeMoles>0){
                        activeMoles--;
                    }else{
                        System.err.println("NOT POSSIBLE IN THIS STATE");
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
        System.out.println("\n");
    }

    public void setButton(JButton nodeButton) {
        button = nodeButton;
    }

    private class Game extends Thread{
        private float timeLeft;
        private int minCooldowntime = 1000;
        private int maxCooldowntime = 4000;
        private boolean ready;
        private boolean active;

        public Game(){
            ready = true;
            active = false;
            button.setBackground(Color.WHITE);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Deactivating mole");
                    if (button.getBackground() == Color.green){
                        button.setBackground(Color.white);
                        activeMoles--;
                        active = false;
                        ready = false;
                    }
                }
            });
        }

        @Override
        public void run() {
            super.run();
            while(gameActive){
                if(activeMoles < 4 && !active && ready){
                    System.out.println("Seting mole to active");
                    active=true;
                    ready = false;
                    button.setBackground(Color.green);
                    activeMoles++;
                }else if(!ready && !active){
                    System.out.println("Mole hit starting cooldown");
                    Random r = new Random();
                    try {
                        sleep(r.nextInt(maxCooldowntime-minCooldowntime)+minCooldowntime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ready = true;
                    System.out.println("Mole ready");
                }
                Random r = new Random();
                try {
                    sleep(r.nextInt(20)+20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Game stopped");
        }
    }
}
