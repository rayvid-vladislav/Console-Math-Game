import java.util.Random;
import java.util.Scanner;
public class Monster {
    static Scanner sc = new Scanner(System.in);
    boolean hasKey = false;
    int x, y;
    Random random = new Random();
    String image;
    Monster(String monsterIcon) {
        this.image = monsterIcon;
        this.x = -1;
        this.y = -1;
    }

    public boolean meetPerson(int personX, int personY) {
        return personX == x && personY == y;
    }

    public boolean taskMonster(int diff){
        if(random.nextBoolean()){
            return sumTask(diff);
        }
        else{
            return multTask(diff);
        }
    }

    public boolean sumTask(int diff){
        int a = random.nextInt(diff);
        int b = random.nextInt(diff);
        int c = a + b;
        System.out.println(a + " + " + b);
        while (!sc.hasNextInt()) {
            System.out.println("Это не число! Введи цифры.");
            sc.next();
        }
        int answer = sc.nextInt();
        return answer == c;
    }

    public boolean multTask(int diff){
        int a = random.nextInt((int) Math.sqrt(diff));
        int b = random.nextInt((int) Math.sqrt(diff));
        int c = a * b;
        System.out.println(a + " * " + b);
        while (!sc.hasNextInt()) {
            System.out.println("Это не число! Введи цифры.");
            sc.next();
        }
        int answer = sc.nextInt();
        return answer == c;
    }


}