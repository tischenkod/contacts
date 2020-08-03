package contacts;

import java.time.LocalDate;

public class AddContactMenuItem extends MenuItem {
    public AddContactMenuItem(Menu menu) {
        super("add", menu);
    }

    @Override
    int processAction() {
        System.out.print("[" + actionName + "]");
        System.out.println("Enter the type (person, organization):");
        switch (menu.scanner.nextLine()) {
            case "person":
                System.out.println("Enter the name:");
                String name = menu.scanner.nextLine();
                System.out.println("Enter the surname:");
                String surname = menu.scanner.nextLine();
                System.out.println("Enter the birth date:");
                LocalDate birthDay = menu.readBirthDay();
                Gender gender = menu.readGender();
                menu.book.addPerson(name, surname, menu.readPhone(), birthDay, gender);
                System.out.println("The record added.");
                break;
            case "organization":
                System.out.println("Enter the organization name:");
                String orgName = menu.scanner.nextLine();
                System.out.println("Enter the address:");
                String address = menu.scanner.nextLine();
                menu.book.addOrganization(orgName, address, menu.readPhone());
                System.out.println("The record added.");
                break;
        }
        return 0;
    }
}
