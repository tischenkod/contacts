package contacts;

public class WildcardMenuItem extends BranchingMenuItem{
    private int action;

    public WildcardMenuItem(String name, String actionName, String caption, Menu menu) {
        super(name, actionName, caption, menu);
    }

    @Override
    protected boolean checkAction(String action) {
        if (action.matches("\\d+")) {
            this.action = Integer.parseInt(action);
            return this.action > 0 && this.action <= menu.book.searchResultsSize();
        }
        return false;
    }

    @Override
    int processAction() {
        menu.book.select(action - 1);
        return super.processAction();
    }
}
