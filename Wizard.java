import java.util.Random;

public class Wizard extends Character implements Attacker{

    private static final int MAX_HP = 100;
    private static final int MIN_HP = 50;
    private static final int MAX_INTELLIGENCE = 50;
    private static final int MIN_INTELLIGENCE = 1;
    private static final int MAX_MANA = 50;
    private static final int MIN_MANA = 10;
    private int mana;
    private int intelligence;


    public Wizard(String name, int hp, int intelligence, int mana) {
        super(name, hp);
        setMana(mana);
        setIntelligence(intelligence);
    }

    public Wizard(String name) {
        super(name);
        setHp(generateRandomHp());
        setIntelligence(generateRandomIntelligence());
        setMana(generateRandomMana());
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    private int generateRandomHp() {
        Random random = new Random();
        return random.nextInt((MAX_HP - MIN_HP) + 1) + MIN_HP;
    }

    private int generateRandomIntelligence() {
        Random random = new Random();
        return random.nextInt((MAX_INTELLIGENCE - MIN_INTELLIGENCE) + 1) + MIN_INTELLIGENCE;
    }

    private int generateRandomMana() {
        Random random = new Random();
        return random.nextInt((MAX_MANA - MIN_MANA) + 1) + MAX_MANA;
    }


    public void fireBall(Character character){
            System.out.println("Casting fireball: " + getIntelligence() + " points of damage");
            character.setHp(character.getHp()-(getIntelligence()));
            System.out.println("Character: " + character.getHp() + " health left");
            setMana(getMana()-5);
    }


    public void staffHit(Character character){
        if(getMana()<5){
            System.out.println("Recovering mana");
            setMana(getMana()+1);
        }else{
                System.out.println("Casting Staff hit: 2 points of damage");
                character.setHp(character.getHp()-(2));
                System.out.println("Character: " + character.getHp() + " health left");
                //Mana recovery up to init or max?
                if(getMana()<MAX_MANA){
                    System.out.println("Recovering mana");
                    setMana(getMana()+1);
                }
        }
    }


    @Override
    public void attack(Character character){
        Random random = new Random();
        if(isAlive()){
            if(random.nextDouble()>0.5){
                staffHit(character);
            }else{
                if(getMana()<5){
                    staffHit(character);
                }else {
                    fireBall(character);
                }
            }
            if(character.getHp()<=0){
                character.setAlive(false);
                System.out.println("The character " + character.getName() + " has been defeated");
            }
        }
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "hp=" + getHp() +
                ", mana=" + getMana() +
                ", intelligence=" + getIntelligence() +
                '}';
    }
}

