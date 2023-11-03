package monte_carlo.src.main.java.com.monte_carlo;


import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option by inputting a number:");
        System.out.println("1. Basic Monte Carlo Simulation");
        System.out.println("2. Advanced Monte Carlo Simulation");
        int N;
        do {
            System.out.print("Enter a value 1 or 2: ");
            N = scanner.nextInt();
        } while (N < 1 || N > 2); // keep inputs in range specified
        if (N == 1){
            Monte_Carlo.main(new String[0]);
        } else {
            Monte_Carlo_Advanced.main(new String[0]);
        }

    }

}
