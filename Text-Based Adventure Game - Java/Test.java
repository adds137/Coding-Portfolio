import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        Scanner userinput = new Scanner(System.in);
        String aaron = userinput.next();
        System.out.println("Type start to begin");
        while (aaron != "start") {
            System.out.println("Type start to begin");
             aaron = userinput.next();
        }
        System.out.println("Begin");
        userinput.close();
    }
}