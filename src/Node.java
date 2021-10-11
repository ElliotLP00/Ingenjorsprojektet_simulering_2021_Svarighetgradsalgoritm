public class Node {
    private mainFrame frame;
    private int difficulty;
    private int connectedNodes;
    private int activeMoles;
    private boolean gameActive;

    public static void main(String[] args) {
        new Node();
    }
    public Node() {
        frame = new mainFrame();
    }

    private void analyzeMessage(Message message) {
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
                System.out.println("Incoming Mole active");
                activeMoles++;
                break;
            case 4:
                System.out.println("Incoming Mole hit.");
                String data = message.getData();
                difficulty += Integer.parseInt(data);
                activeMoles--;
                break;
            case 5:
                System.out.println("Incoming Mole miss");
                if(difficulty > 0)
                    difficulty--;
                if(message.getData()=="Mole time exceeded"){
                    activeMoles--;
                }
                break;
            case 6:
                System.out.println("Incoming Start game");
                activeMoles = 0;
                gameActive = true;
                new Game().start();
                break;
            case 7:
                System.out.println("Incoming Stop game");
                gameActive = false;
                break;
            case 8:
                System.out.println("Incoming Node disconnected");
                connectedNodes--;
                break;
        }
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
