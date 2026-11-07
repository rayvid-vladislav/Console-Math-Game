public class Config {

    public int zombies;
    public int vampires;
    public int boardSize;
    public int difficulty;
    public int lives;
    public String playerIcon;
    public String monsterIcon;
    public String bigMonsterIcon;
    public String guardMonsterIcon;
    public String guardBrokenIcon;
    public String castleIcon;
    public Config(int zombies, int vampires, int boardSize, int difficulty, int lives, String playerIcon, String monsterIcon, String bigMonsterIcon, String guardMonsterIcon, String guardBrokenIcon, String castleIcon) {
        this.zombies = zombies;
        this.vampires = vampires;
        this.boardSize = boardSize;
        this.difficulty = difficulty;
        this.lives = lives;
        this.playerIcon = playerIcon;
        this.monsterIcon = monsterIcon;
        this.bigMonsterIcon = bigMonsterIcon;
        this.guardMonsterIcon = guardMonsterIcon;
        this.guardBrokenIcon = guardBrokenIcon;
        this.castleIcon = castleIcon;
    }

    public Config(int zombies, int vampires, int boardSize, int difficulty) {
        this(zombies, vampires, boardSize, difficulty, 3, "🤺", "🧟", "🧛", "🛡️", "🩻", "🏰");
    }

}