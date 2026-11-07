import java.util.*;

public class Main {

    static boolean first_fail, fog;
    static int steps, x, y;
    static String difficulty, ans;
    public static Scanner sc = new Scanner(System.in);
    static Random random = new Random();
    static String[][] board;
    static Config settings;

    public static void main(String[] args) {

        String HelpText = """
                Привет, вот правила если забыл или не знаешь:
                1.Чтобы начать игру необходимо написать ГОТОВ,
                2.После этого необходимо выбрать сложность игры:
                easy - поле 4 на 4, 3 маленьких монстра(зомби),
                medium - поле 5 на 5, 5 маленьких монстров(зомби) и 2 больших(вампир),
                hard - поле 6 на 6, 10 маленьких монстров(зомби) и 5 больших(вампир),
                custom - после этого выбора необходимо будет выбрать еще и размер поля(n * n),
                количество монстров сложность заданий от 1 до 1 000 000 000(medium = 1000),
                и иконки монстров замка и игрока.
                3.Далее тебе выведется поле, где ты - 🤺, маленький монстр - 🧟, большой монстр - 🧛, замок - 🏰, стражи - 🛡️.
                4.Чтобы сделать ход необходимо написать координаты клетки в которую ты хочешь походить:
                1 координата x - отсчитывается от верхнего левого угла(1, 1) слева направо,
                2 координата y - отсчитывается от верхнего левого угла(1, 1) сверху вниз.
                5.Твоя цель - добраться до замка, но если ты наступаешь на клетку с монстром - он дает тебе задание
                если ты его не выполнишь - у тебя отнимается жизнь изначально у тебя 3 жизни
                если ты выполнишь задание то ты встаешь на эту клетку и можешь идти дальше
                если у тебя осталось 0 жизней то тебе дается 2 шанс
                после выполнения которого ты проигрываешь или получаешь + 1 жизнь
                также чтобы победить необходимо иметь ключ - он выпадает из 1 из монстров(включая стражей)
                """;

        while (true) {
            steps = 0;
            first_fail = true;
            System.out.println("Привет, ты попал в консольную игру про рыцаря.\n" +
                    "Если ты играешь впервые пиши HELP если уже играл пиши ГОТОВ");
            ans = sc.nextLine().trim().toUpperCase();
            if (ans.isEmpty()) {
                System.out.println("Ты ничего не ввел!");
                continue;
            }

            if (ans.equals("HELP")) {
                System.out.println(HelpText + "\n Теперь ты готов начинать?(пиши ГОТОВ если да)");
                ans = sc.nextLine().trim().toUpperCase();
                if (ans.isEmpty()) {
                    System.out.println("Ты ничего не ввел!");
                    continue;
                }

                if (!ans.equals("ГОТОВ")) {
                    System.out.println("Жаль, приходи еще");
                    System.exit(0);
                }
            }
            if (ans.equals("ГОТОВ")) {
                System.out.println("Раз уж ты готов, давай выберем сложность:\n пиши easy / medium / hard / custom");
                settings = choose_difficulty();
            }
            else{
                System.out.println("Напиши английскими буквами HELP если играешь впервые/забыл что делать или ГОТОВ если готов играть");
                continue;
            }
            board = new String[settings.boardSize][settings.boardSize];
            for (int i = 0; i < settings.boardSize; i++) {
                Arrays.fill(board[i], "  ");
            }
            List<Monster> monsters = new ArrayList<>();
            Person player = new Person(random.nextInt(settings.boardSize), settings.boardSize - 1, settings.lives, settings.playerIcon);
            board[player.y][player.x] = player.image;
            int castleX = random.nextInt(settings.boardSize);
            board[0][castleX] = settings.castleIcon;
            if (castleX - 1 >= 0) {
                GuardMonster g1 = new GuardMonster(settings.guardMonsterIcon, settings.guardBrokenIcon);
                g1.x = castleX - 1;
                g1.y = 0;
                board[g1.y][g1.x] = g1.image;
                monsters.add(g1);
            }
            if (castleX + 1 < settings.boardSize) {
                GuardMonster g2 = new GuardMonster(settings.guardMonsterIcon, settings.guardBrokenIcon);
                g2.x = castleX + 1;
                g2.y = 0;
                board[g2.y][g2.x] = g2.image;
                monsters.add(g2);
            }
            GuardMonster g3 = new GuardMonster(settings.guardMonsterIcon, settings.guardBrokenIcon);
            g3.x = castleX;
            g3.y = 1;
            board[g3.y][g3.x] = g3.image;
            monsters.add(g3);
            List<int[]> emptyCells = new ArrayList<>();
            for (int i = 0; i < settings.boardSize; i++) {
                for (int j = 0; j < settings.boardSize; j++) {
                    if (board[i][j].equals("  ")) {
                        emptyCells.add(new int[]{i, j});
                    }
                }
            }
            Collections.shuffle(emptyCells);

            for(int k = 0; k < settings.zombies; k ++){
                int[] cords = emptyCells.get(k);
                int i = cords[0], j = cords[1];
                Monster m = new Monster(settings.monsterIcon);
                m.x = j;
                m.y = i;

                monsters.add(m);
                board[i][j] = m.image;
            }
            for(int k = settings.zombies; k < settings.vampires + settings.zombies; k ++){
                int[] cords = emptyCells.get(k);
                int i = cords[0], j = cords[1];

                BigMonster m = new BigMonster(settings.bigMonsterIcon);
                m.x = j;
                m.y = i;

                monsters.add(m);
                board[i][j] = m.image;
            }
            if (!monsters.isEmpty()) {
                int randomIndex = random.nextInt(monsters.size());
                monsters.get(randomIndex).hasKey = true;
            }
            System.out.println("Включить туман войны?(Да/Нет)");
            ans = sc.nextLine().trim().toLowerCase();
            if (ans.isEmpty()) {
                System.out.println("Ты ничего не ввел!");
                continue;
            }
            fog = ans.equals("да");
            while(true){
                if(fog){
                    print_board_fog(player);
                }
                else{
                    print_board_normal();
                }
                System.out.println("Введи свой ход в формате x y, где (x, y) - это координаты клетки в которую ты хочешь перейти");
                while (!sc.hasNextInt()) {
                    System.out.println("Ошибка! Введи число.");
                    sc.next();
                }
                x = sc.nextInt();
                y = sc.nextInt();
                x -= 1;
                y -= 1;
                if(player.isCorrect(x, y)){
                    if (board[y][x].equals("  ")) {
                        board[player.y][player.x] = "  ";
                        player.move(x, y);
                        steps ++;
                        board[player.y][player.x] = player.image;
                        System.out.println("Ход корректный; Новые координаты: " + (x + 1) + ", " + (y + 1) + " Жизни: " + player.live + "\nХод номер: " + steps);
                    }
                    else if (board[y][x].equals(settings.castleIcon)) {
                        if (player.hasKey) {
                            System.out.println("Вы открыли замок ключом и прошли игру! 🥳");
                            break;
                        }
                        else {
                            System.out.println("Замок заперт! Вам нужен ключ. Ищите его у монстров.");
                            continue;
                        }
                    }
                    else {
                        for (int i = 0; i < monsters.size(); i++) {
                            Monster monster = monsters.get(i);
                            if (monster.meetPerson(x, y)) {
                                if (monster.taskMonster(settings.difficulty)) {
                                    if (monster instanceof GuardMonster guard) {
                                        if (guard.defeat()) {
                                            board[guard.y][guard.x] = guard.image;
                                            steps++;
                                            System.out.println("Ты сломал щит стража! Но он все еще жив.");
                                        } else {
                                            monsters.remove(monster);
                                            board[player.y][player.x] = "  ";
                                            player.move(x, y);
                                            steps++;
                                            board[player.y][player.x] = player.image;
                                            if (monster.hasKey) {
                                                player.hasKey = true;
                                                System.out.println("🎉 УРА! Ты нашел КЛЮЧ ОТ ЗАМКА! 🗝️");
                                            }
                                        }
                                    }
                                    else{
                                        board[player.y][player.x] = "  ";
                                        monsters.remove(i);
                                        player.move(x, y);
                                        steps++;
                                        board[player.y][player.x] = player.image;
                                        if (monster.hasKey) {
                                            System.out.println("🎉 УРА! Ты нашел КЛЮЧ ОТ ЗАМКА! 🗝️");
                                            player.hasKey = true;
                                        } else {
                                            System.out.println("Ладно на этот раз ты выжил!");
                                        }
                                    }
                                } else {
                                    player.downLive();
                                    steps ++;
                                    System.out.println("ХАХАХАХА ТЫ ТЕРЯЕШЬ ЖИЗНЬ🧟🫀💔.Осталось жизней:" + player.live + "Ход номер: " + steps);
                                }
                                break;
                            }
                        }
                    }
                }
                else{
                    System.out.println("Ход некорректный ходить можно только на 1 клетку вверх/вниз или влево/вправо");
                }
                if (player.live <= 0) {
                    if (first_fail) {
                        Monster tempMonster = new Monster(settings.monsterIcon);
                        System.out.println("У тебя 0 жизней! Дается последний шанс!");
                        if(tempMonster.taskMonster(settings.difficulty)){
                            player.live += 1;
                            System.out.println("Ладно на этот раз ты выжил!");
                        }
                        else{
                            player.live -= 1;
                            System.out.println("Ты проиграл, учи математику подаван!");
                            break;
                        }
                        first_fail = false;
                    }
                    else {
                        System.out.println("Ты проиграл, учи математику подаван!");
                        break;
                    }
                }
            }
        }
    }

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ошибка! Введи целое число: ");
            }
        }
    }

    static void print_board_normal() {
        String leftBlock = " |  ";
        String rightBlock = " |";
        String wall = " + —— ";

        for (String[] row : board) {
            for (int i = 0; i < settings.boardSize; i++) {
                System.out.print(wall);
            }
            System.out.println("+");

            for (String cell : row) {
                System.out.print(leftBlock + cell);
            }
            System.out.println(rightBlock);
        }

        for (int i = 0; i < settings.boardSize; i++) {
            System.out.print(wall);
        }
        System.out.println("+");
    }
    static void print_board_fog(Person player){
        String leftBlock = " |  ";
        String rightBlock = " |";
        String wall = " + —— ";
        for (int i = 0; i < settings.boardSize; i++) {
            for (int k = 0; k < settings.boardSize; k++) {
                System.out.print(wall);
            }
            System.out.println("+");
            for (int j = 0; j < settings.boardSize; j++) {
                int dx = Math.abs(j - player.x);
                int dy = Math.abs(i - player.y);

                if (dx > 1 || dy > 1) {
                    System.out.print(" |  ? ");
                } else {
                    System.out.print(leftBlock + board[i][j]);
                }
            }
            System.out.println(rightBlock);
        }

        for (int i = 0; i < settings.boardSize; i++) {
            System.out.print(wall);
        }
        System.out.println("+");
    }

    static Config choose_difficulty() {
        difficulty = sc.nextLine().trim().toLowerCase();

        switch (difficulty) {
            case "easy":
                return new Config(3, 0, 4, 100);

            case "medium":
                return new Config(5, 2, 5, 1000);

            case "hard":
                return new Config(10, 5, 6, 100000);

            case "custom":
                System.out.println("Введи: зомби вампиры размер сложность жизни иконка игрока иконка монстра иконка большого монстра, иконка стража, иконку замка");

                System.out.print("Зомби: ");
                int numOfZombs = readInt();

                System.out.print("Вампиры: ");
                int numOfVamps = readInt();

                System.out.print("Размер поля: ");
                int boardSize = readInt();

                System.out.print("Сложность: ");
                int diff = readInt();

                System.out.print("Жизни: ");
                int playerHP = readInt();

                System.out.print("Иконка игрока: ");
                String pIcon = sc.next();
                System.out.print("Иконка монстра: ");
                String mIcon = sc.next();
                System.out.print("Иконка большого монстра: ");
                String bmIcon = sc.next();
                System.out.print("Иконка стража: ");
                String gmIcon = sc.next();
                System.out.print("Иконка сломанного стража: ");
                String gbmIcon = sc.next();
                System.out.print("Иконка замка: ");
                String castleIcon = sc.next();
                sc.nextLine();
                return new Config(numOfZombs, numOfVamps, boardSize, diff, playerHP, pIcon, mIcon, bmIcon, gmIcon, gbmIcon, castleIcon);
            default:
                System.out.println("Ставлю medium по умолчанию");
                return new Config(5, 2, 5, 1000);
        }
    }
}