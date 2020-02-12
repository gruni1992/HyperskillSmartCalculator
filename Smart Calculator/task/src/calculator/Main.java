package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            String input = scanner.nextLine();
            switch (input){
                case "/help":
                    System.out.println("The program calculates a term with numbers");
                    break;
                case "/exit":
                    System.out.println("Bye!");
                    break loop;
                case "":
                    break;
                default:
                    System.out.println(calculateSum(getTerm(input)));
                    break;
            }
        }
    }

    public static int calculateSum(String input){
        Pattern isNumeric = Pattern.compile("\\d+");
        String[] term = input.split(" ");
        StringBuilder number = new StringBuilder();
        int sum = 0;
        for (String part : term){
            number.append(part);
            Matcher matcher = isNumeric.matcher(part);
            if(matcher.matches()){
                sum += Integer.parseInt(number.toString());
                number = new StringBuilder();
            }
        }
        return sum;
    }

    public static String getTerm(String input) {
        input = input.replaceAll("\\s+", "");
        input = input.replaceAll("-{2}", "+");
        input = input.replaceAll("\\++", "+");
        input = input.replaceAll("\\+-", "-");
        input = input.replaceAll("\\+", " + ");
        input = input.replaceAll("-", " - ");
        return input;
    }
}
