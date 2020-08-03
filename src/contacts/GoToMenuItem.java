package contacts;

public class GoToMenuItem extends MenuItem {
    MenuItem nextHop;
    public GoToMenuItem(String actionName, MenuItem nextHop, Menu menu) {
        super(actionName, menu);
        this.nextHop = nextHop;
    }

    @Override
    int processAction() {
        menu.activeItem = nextHop;
        return 1;
    }
}
