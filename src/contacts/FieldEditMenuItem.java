package contacts;

public class FieldEditMenuItem extends MenuItem {
    public FieldEditMenuItem(String actionName, Menu menu) {
        super(actionName, menu);
    }

    @Override
    int processAction() {
        System.out.println("Enter " + actionName + ":");
        if (menu.book.updateContact(actionName, menu.scanner.nextLine())) {
            System.out.println("Saved");
            menu.book.info();
            return 1;
        } else {
            return 0;
        }
    }
}
