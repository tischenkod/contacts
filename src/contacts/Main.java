package contacts;

public class Main {
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu(args.length > 0 ? args[0] : null);
        menu.run();
    }
}
