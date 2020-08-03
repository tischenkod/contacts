package contacts;

import java.util.ArrayList;
import java.util.List;

public class EditMenuItem extends BranchingMenuItem{
    public EditMenuItem(String name, String actionName, String caption, Menu menu) {
        super(name, actionName, caption, menu);

    }

    @Override
    int processAction() {
        subItems.clear();
        List<String> fieldList = menu.book.getEditList();
        for (String fieldName :
                fieldList) {
            addItem(new FieldEditMenuItem(fieldName,menu));
        }
        return super.processAction();
    }
}
