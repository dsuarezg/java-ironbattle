import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Play {

    private static void battle(Character character1, Character character2){
        int round = 0;
        while (character1.isAlive() && character2.isAlive()){
            round++;
            System.out.println("Round " + round);
            System.out.println(character1.getName() + " vs " + character2.getName());
            character1.attack(character2);
            character2.attack(character1);
        }
    }

    //If fight until one of them wins, modify method to return and push the winner back to a new winner stack for loop until stacks are empty
    private static void battle(Stack<Character> characters){
        int counter = 0;
        while (characters.size() > 1){
            counter++;
            System.out.println("Battle " + counter);
            System.out.println("====================================");
            var character1 = characters.pop();
            var character2 = characters.pop();
            battle(character1,character2);
        }
    }

    private static Stack<Character> readFromCSV(File file) throws FileNotFoundException {
        Stack<Character> characters = new Stack<>();

        var fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            var line = fileScanner.nextLine();
            var data = line.split(",");

            String characterType = data[0];
            String characterName = data[1];

            if (characterType.equals("Warrior")) {
                characters.push(new Warrior(characterName));
            } else if (characterType.equals("Wizard")) {
                characters.push(new Wizard(characterName));
            }
        }
        fileScanner.close();
        return characters;

    }

    //Can use Faker or read names from CSV and generate random characters
    private static Stack<Character> charactersFactory(int numberOfCharacters) {
        Stack<Character> characters = new Stack<>();
        var random = new Random();
        for (int i = 0; i < numberOfCharacters; i++) {
            if(random.nextDouble()>0.5){
                characters.push(new Warrior("Warrior " + i));
            } else {
                characters.push(new Wizard("Wizard " + i));
            }
        }
        return characters;
    }

    private static Character charactersFactory(String name) {
         var random = new Random();
        if(random.nextDouble()>0.5){
            return new Warrior(name);
        } else {
           return new Wizard(name);
        }
    }

    private static void menu() {
      Scanner scanner = new Scanner(System.in);
      boolean running = true;

      while (running) {
          displayMenu();
          String option = scanner.nextLine();

          switch (option) {
              case "1":
                  try {
                      var file = new File("characters.csv");
                      battle(readFromCSV(file));
                  } catch (FileNotFoundException e) {
                      System.out.println("Error: File not found");
                  }
                  break;

              case "2":
                  System.out.println("Enter the number of characters to generate: ");
                  try {
                      int numberOfCharacters = Integer.parseInt(scanner.nextLine());
                      battle(charactersFactory(numberOfCharacters));
                  } catch (NumberFormatException e) {
                      System.out.println("Error: Please enter a valid number");
                  }
                  break;

              case "3":
                  System.out.println("Enter the name of the first character: ");
                  String name = scanner.nextLine();
                  Character character1 = charactersFactory(name);

                  System.out.println("Enter the name of the second character: ");
                  name = scanner.nextLine();
                  Character character2 = charactersFactory(name);

                  battle(character1, character2);
                  break;

              case "4", "exit":
                  running = false;
                  break;

              default:
                  System.out.println("Invalid option. Please try again.");
          }
      }
      scanner.close();
  }

    private static void displayMenu() {
        System.out.println("\n======== Iron Battle ========");
        System.out.println("1. Read from CSV");
        System.out.println("2. Generate random characters");
        System.out.println("3. Create your own characters");
        System.out.println("4. Exit");
        System.out.println("=============================");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {

        menu();
    }
}


