package contacts;

public class DeleteMenuItem extends MenuItem {
    public DeleteMenuItem(Menu menu) {
        super("delete", menu);
    }

    @Override
    int processAction() {
        menu.book.deleteSelected();
        System.out.println("Record deleted");
        return 1;
    }
}
