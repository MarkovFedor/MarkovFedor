import CLI.Application;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        try {
            application.Start();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
