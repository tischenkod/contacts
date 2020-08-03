package contacts;

public abstract class MenuItem {
    public final String actionName;
    public final Menu menu;

//    MenuItem parent;

    public MenuItem(String actionName, Menu menu) {
        this.actionName = actionName;
//        this.parent = parent;
        this.menu = menu;
    }

    int enter(String action) {
        if (!checkAction(action)) {
            return -1;
        } else {
            return processAction();
        }
    }

    protected boolean checkAction(String action) {
        return action.equals(actionName);
    }

    abstract int processAction();

    public void enter() {
        enter(actionName);
    }

}
