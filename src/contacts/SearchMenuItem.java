package contacts;

import java.util.ArrayList;

public class SearchMenuItem extends BranchingMenuItem {
    public SearchMenuItem(String caption, Menu menu) {
        super("search", "search", caption, menu);
    }

    @Override
    int processAction() {
        System.out.println("Enter search query:");
        menu.book.search(menu.scanner.nextLine());
        menu.book.printResults();
        return super.processAction();
    }
}
