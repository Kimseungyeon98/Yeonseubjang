import java.util.Arrays;
import java.util.stream.Collectors;

public class CodingTest {
    public static void main(String[] args) {
        System.out.println(solution("Bcad"));
    }
    public static String solution(String my_string) {
        return Arrays.stream(my_string.split("")).map(String::toLowerCase).sorted().reduce("", String::concat);
    }
}
