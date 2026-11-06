import java.util.Random;
public class Person {
    boolean hasKey = false;
    static Random random = new Random();
    int x, y, live;
    String image;

    Person(int x, int y, int live, String image){
        this.x = x;
        this.y = y;
        this.live = live;
        this.image = image;
    }

    Person(int boardSize){
        this(random.nextInt(boardSize), boardSize - 1, 3, "🤺");
    }

    public boolean isCorrect(int x, int y) {
        return this.x == x && Math.abs(this.y - y) == 1 || this.y == y && Math.abs(this.x - x) == 1;
    }

    void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void downLive() {
        if(live <= 0){
            live = 0;
        }
        else{
            live--;
        }
    }
}
