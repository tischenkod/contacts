package contacts;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

class Menu {
//    State state;
    Scanner scanner;
    AddressBook book;
    BranchingMenuItem root;
    MenuItem activeItem;

    public Menu(String databaseName) throws Exception{
        scanner = new Scanner(System.in);
        book = new AddressBook(databaseName);
    }

    public void run() throws IOException {
        setRoot(new BranchingMenuItem("menu", "menu", "Enter action", this));
        root.addItem(new AddContactMenuItem(this));
        ListMenuItem listMenuItem = new ListMenuItem("Enter action", this);
        root.addItem(listMenuItem);
        SearchMenuItem searchMenuItem = new SearchMenuItem("Enter action" , this);
        root.addItem(searchMenuItem);
        WildcardMenuItem recordMenuItem = new WildcardMenuItem("record", "[number]", "Enter action", this);
        listMenuItem.addItem(recordMenuItem);
        searchMenuItem.addItem(recordMenuItem);
        searchMenuItem.addItem(new GoToMenuItem("back", root, this));
        searchMenuItem.addItem(new GoToMenuItem("again", searchMenuItem, this));
        BranchingMenuItem editMenuItem = new EditMenuItem("edit", "edit", "Select a field",this);
        recordMenuItem.addItem(editMenuItem);
        recordMenuItem.addItem(new DeleteMenuItem(this));
        recordMenuItem.addItem(new GoToMenuItem("menu", root, this));
        root.addItem(new CountMenuItem(this));
        root.addItem(new GoToMenuItem("exit", null, this));
        while (activeItem != null) {
            activeItem.enter();
        }
        book.close();
    }

    private void setRoot(BranchingMenuItem menuItem) {
        root = menuItem;
        activeItem = menuItem;
    }

    LocalDate readBirthDay() {
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            return null;
        }
    }

    String readPhone() {
        System.out.println("Enter the number:");
        return scanner.nextLine();
    }

    Gender readGender() {
        System.out.println("Enter the gender (M, F):");
        switch (scanner.nextLine()) {
            case "M": return Gender.MALE;
            case "F": return Gender.FEMALE;
            default:
                System.out.println("Bad gender!");
                return null;
        }
    }

}
