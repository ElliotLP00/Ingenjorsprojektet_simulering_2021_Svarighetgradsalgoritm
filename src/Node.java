public class Node {
    private mainFrame frame;
    private int difficulty;
    private int connectedNodes;
    public Node() {
        frame = new mainFrame();
    }

    private void analyzeMessage(Message message) {
        switch (message.getMessageType()){
            case 1:
                connectedNodes++;
                System.out.println("Incoming New node. Total:"+connectedNodes);
                break;
            case 2:
                System.out.println("Incoming Keep alive. Sending back answer");
                break;
            case 3:
                System.out.println("Incoming Mole active");
                break;
            case 4:
                System.out.println("Incoming Mole hit");
                break;
            case 5:
                System.out.println("Incoming Mole miss");
                break;
            case 6:
                System.out.println("Incoming Start game");
                break;
            case 7:
                System.out.println("Incoming Stop game");
                break;
            case 8:
                System.out.println("Incoming Node disconnected");

                break;
        }
    }
}
