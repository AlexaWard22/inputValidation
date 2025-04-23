import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class loginModule{
    private static final String[] usernames= {"scientist", "engineer", "security"};
    private static final String[] passwords= {"Sci12345", "Engine456", "Secur789" };

    public static void main(String[] args){
        saveDatabase();

        Scanner scanner= new Scanner(System.in);

        System.out.print("Please enter your username: ");
        String username= scanner.nextLine();

        validation validation = new validation();

        if(validation.validateUsername(username)){
            System.out.println("Username validation passed.");
        }else{
            System.out.println("Username validation failed. SQL injection characters detected.");
         }

        Console console= System.console();
        String password= new String(console.readPassword("Please enter your password: "));

        if(validation.validatePassword(password)){
            System.out.println("Password validation passed.");
        }else{
            System.out.println("Password validation failed. SQL injection characters policy violation detected.");
        }

        System.out.print("Please enter your MFA code(10-digit number): ");
        String mfaCode = scanner.nextLine();

        if(validation.validateMFA(mfaCode)){
            System.out.println("MFA validation passed.");
        }else{
            System.out.println("MFA validation failed. Must be a 10-digit number.");
        }

        if(validation.validateUsername(username)&& validation.validatePassword(password)&& validation.validateMFA(mfaCode)&& checkLogin(username,password)){
             System.out.println("Welcome, " +username);
        }else{ 
            System.out.println("Incorrect username, password, or MFA code. Please enter username and password again.");
        }
        scanner.close();
    }

    private static void saveDatabase(){
        try(FileWriter writer=new FileWriter("database.txt")){
            for(int i=0; i<usernames.length; i++){
                writer.write(usernames[i]+ ":" + passwords[i] + "\n");
            }
        }catch(IOException e){
            System.out.println("An error occurred with saving your user database.");
            e.printStackTrace();
        }
    }
    private static boolean checkLogin(String username, String password){
        for(int i=0; i<usernames.length; i++){
            if(usernames[i].equals(username)&& passwords[i].equals(password)){
                return true;
            }
        }
        return false;
    }
}
