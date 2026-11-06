public class GuardMonster extends Monster{
    boolean shield = true;
    String brokenImage;
    GuardMonster(String monsterIcon, String brokenImage) {
        super(monsterIcon);
        this.brokenImage = brokenImage;
    }
    public boolean defeat(){
        if(shield){
            shield = false;
            this.image = this.brokenImage;;
            return true;
        }
        else{
            return false;
        }
    }
}
