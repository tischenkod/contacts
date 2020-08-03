package contacts;

public class ListMenuItem extends BranchingMenuItem {
    public ListMenuItem(String caption, Menu menu) {
        super("list", "list", caption, menu);
    }

    @Override
    int processAction() {
        menu.book.search("");
        menu.book.printResults();
        return super.processAction();
    }
}
