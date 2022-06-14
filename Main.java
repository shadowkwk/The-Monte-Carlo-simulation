import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int success = 0;
        Maze maze = new Maze();
        for (int i = 0; i < 1000000; i++) {
            Mouse mouse = new Mouse();
            if (maze.nextMove(mouse)) {
                success++;
            }
        }
        System.out.println("The Monte Carlo simulation result of one million runs:");
        System.out.println("No. of successful escape: " + success);
        System.out.printf("Success Rate P: %.3f\n", success / 1000000.0);
    }
}

class Mouse {

    final int upperBoundX;
    final int upperBoundY;
    int positionX;
    int positionY;
    int[][] previousMove;

    Mouse() {
        this.upperBoundY = 6;
        this.upperBoundX = 6;
        this.previousMove = new int[upperBoundX + 1][upperBoundY + 1];
        positionX = 0;
        positionY = 0;
        previousMove[0][0] = 1;
    }
}

class Maze {

    Random rand = new Random();
    final int upperBoundX = 6;
    final int upperBoundY = 6;

    boolean nextMove(Mouse mouse) {
        int newX = 0, newY = 0;
        boolean N = false, W = false, E = false, S = false;
        while (mouse.positionX != upperBoundX || mouse.positionY != upperBoundY) {
            int direction = rand.nextInt(10);
            if ((direction == 0 || direction == 1)) {     //N
                newX = mouse.positionX - 1;
                N = true;
            } else if ((direction == 2 || direction == 3)) {      //W
                newY = mouse.positionY - 1;
                W = true;
            } else if (direction >= 4 && direction <= 6) {      //E
                newY = mouse.positionY + 1;
                E = true;
            } else if (direction >= 7 && direction <= 9) {      //S
                newX = mouse.positionX + 1;
                S = true;
            }
            if (newX < 0 || newX > upperBoundX || newY < 0 || newY > upperBoundY) {
                newX = mouse.positionX;
                newY = mouse.positionY;
                continue;
            }
            if (mouse.previousMove[newX][newY] == 0) {
                mouse.positionX = newX;
                mouse.positionY = newY;
                mouse.previousMove[newX][newY] = 1;
                N = false;
                W = false;
                E = false;
                S = false;
            } else if (mouse.previousMove[newX][newY] == 1) {
                newX = mouse.positionX;
                newY = mouse.positionY;
            }
            if (N && W && E && S) {
                return false;
            }
        }
        return true;
    }
}
