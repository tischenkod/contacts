package contacts;

import java.util.ArrayList;

public class BranchingMenuItem extends MenuItem{
    public final String name;
    public final String caption;

    ArrayList<MenuItem> subItems;

    public BranchingMenuItem(String name, String actionName, String caption, Menu menu) {
        super(actionName, menu);
        this.name = name;
        this.caption = caption;
        subItems = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        subItems.add(item);
    }

    void print() {
        System.out.print("\n[" + name + "] " + caption + " ");
        if (subItems.size() > 0) {
            System.out.print("(");
            for (int i = 0; i < subItems.size() - 1; i++) {
                System.out.print(subItems.get(i).actionName + ", ");
            }
            System.out.print(subItems.get(subItems.size() - 1).actionName + ")");
        }
    }


    int processAction() {
        while (true) {
            print();
            String choice = menu.scanner.nextLine();
            for (MenuItem menuItem :
                    subItems) {
                int exitCode = menuItem.enter(choice);
                if (exitCode >= 0) {
                    return exitCode;
                }
            }
        }
    }

}
