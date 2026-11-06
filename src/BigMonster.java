import java.util.Random;
import java.util.Scanner;
import static java.lang.Math.pow;

public class BigMonster extends Monster{

    BigMonster(String monsterIcon) {

        super(monsterIcon);
    }

    @Override
    public boolean sumTask(int difficulty) {
        int c = random.nextInt(1, difficulty / 10);
        int a = c * c;
        System.out.println("√" + a);
        int answer = sc.nextInt();
        return answer == c;
    }

    @Override
    public boolean multTask(int difficulty) {
        int c = random.nextInt(1, difficulty / 10);
        int a = 1 << c;
        System.out.println("log₂" + a);
        int answer = sc.nextInt();
        return answer == c;
    }
}
