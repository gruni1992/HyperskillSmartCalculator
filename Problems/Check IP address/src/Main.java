import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);
        String maybeIp = scanner.nextLine();
        String numberEx = "(1?\\d?\\d|2[0-4]\\d|25[0-5])";
        String regEx = String.format("(%s\\.){3}%s", numberEx, numberEx);
        System.out.println(maybeIp.matches(regEx) ? "YES" : "NO");
    }
}