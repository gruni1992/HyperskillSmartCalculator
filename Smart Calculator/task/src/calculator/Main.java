package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            String input = scanner.nextLine();
            switch (input){
                case "/help":
                    System.out.println("The program calculates the sum of numbers");
                    break;
                case "/exit":
                    System.out.println("Bye!");
                    break loop;
                case "":
                    break;
                default:
                    System.out.println(calculateSum(input));
                    break;
            }
        }
    }

    public static int calculateSum(String input) {
        int sum = 0;
        for(String number : input.split(" ")) {
            sum += Integer.parseInt(number);
        }
        return sum;
    }
}
