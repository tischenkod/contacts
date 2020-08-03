package contacts;

public class CountMenuItem extends MenuItem {
    public CountMenuItem(Menu menu) {
        super("count", menu);
    }

    @Override
    int processAction() {
        System.out.println("The Phone Book has " + menu.book.size() + " records.");
        return 1;
    }
}
