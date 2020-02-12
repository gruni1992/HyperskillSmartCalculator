package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    Scanner scanner = new Scanner(System.in);
    Map<String, Integer> variables = new HashMap<>();
    
    //MAIN LOOP
    public void turnOn(){
        while (true) {
            String input = scanner.nextLine();
            switch (input){
                case "/help": help(); break;
                case "/exit": exit(); break;
                case "": break;
                default:
                    //Input starts with "/"
                    if (input.matches("/.*")) {
                        System.out.println("Unknown command");
                    //Input contains "="
                    } else if (input.matches(".*=.*")) {
                        tryAssignment(input);
                    //Input is sequence of at least one english character
                    } else if (input.matches("[a-zA-Z]+")){
                        tryReadVariable(input);
                    //Any other input
                    } else {
                        input = insertVariables(input);
                        System.out.println(isValidExpression(input)
                                ? calculateSum(input)
                                : "Invalid expression");
                    }
            }
        }
    }

    //COMMANDS
    private void help(){
        System.out.println("The program calculates a term with numbers. It is possible to assign variables as well.");
    }

    private void exit(){
        System.out.println("Bye!");
        System.exit(0);
    }

    //TRY TO HANDLE USER DATA ACCORDING TO INPUT
    private void tryReadVariable(String input) {
        Integer variable = variables.get(input);
        System.out.println(variable == null ? "Unknown variable" : variable);
    }

    private void tryAssignment(String input){
        //Check if valid identifier
        if(input.matches("[a-zA-Z]+\\s*=.*")) {
            //get the right half of the assignment
            String rightTerm = input.replaceFirst(".*?=", "");
            rightTerm = insertVariables(rightTerm);
            if(isValidExpression(rightTerm)){
                String name = getAssignmentName(input);
                int value = calculateSum(rightTerm);
                variables.put(name, value);
            } else {
                System.out.println("Invalid assignment");
            }
        } else {
            System.out.println("Invalid identifier");
        }
    }

    //HELPER METHODS
    //finds the first word in the input which is the name of the variable
    private String getAssignmentName(String input){
        Pattern namePattern = Pattern.compile("[a-zA-Z]+");
        Matcher nameMatcher = namePattern.matcher(input);
        nameMatcher.find();
        return nameMatcher.group(0);
    }

    //Inserts all knwon variables into a term
    private String insertVariables(String term){
        for (var variable : variables.entrySet()){
            String pattern = "\\b" + variable.getKey() + "\\b";
            term = term.replaceAll(pattern, variable.getValue().toString());
        }
        return term;
    }

    //Calculates the sum of a valid term
    private int calculateSum(String input){
        input = getTerm(input);
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

    //Checks if the expression is valid (only numbers and operators (+ and -))
    private boolean isValidExpression(String input){
        Pattern pattern = Pattern.compile("([+-]*\\s*\\d+\\s*){1}([+-]+\\s*\\d+\\s*)*");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    //Replaces a row of plusses and minusses and trims the term
    private String getTerm(String input) {
        input = input.replaceAll("\\s+", "");
        input = input.replaceAll("-{2}", "+");
        input = input.replaceAll("\\++", "+");
        input = input.replaceAll("\\+-", "-");
        input = input.replaceAll("\\+", " + ");
        input = input.replaceAll("-", " - ");
        return input;
    }
}
