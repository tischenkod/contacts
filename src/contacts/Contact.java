package contacts;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum Gender implements Serializable{
    MALE,
    FEMALE;

    public static Gender parse(String value) {
        switch (value) {
            case "M":
            case "m":
                return Gender.MALE;
            case "F":
            case "f":
                return Gender.FEMALE;
            default:
                System.out.println("Bag gender!");
                return null;
        }
    }

    public String asString() {
        switch (this) {
            case MALE:
                return "Male";
            case FEMALE:
                return "Female";
            default:
                return "[no data]";
        }
    }
}

abstract class Contact implements Serializable{

    String phone;
    LocalDateTime created;
    LocalDateTime changed;

    public Contact(String phone) {
        this.phone = phone;
        changed = created = LocalDateTime.now().withSecond(0).withNano(0);
    }

    List<String> getEditList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("number");
        return list;
    }

    public boolean update(String fieldToEdit, String value) {
        if (fieldToEdit.equals("number")) {
            setPhone(value);
            change();
            return true;
        } else {
            return false;
        }
    }

    protected void change() {
        changed = LocalDateTime.now().withSecond(0).withNano(0);
    }

    protected void setPhone(String value) {
        if (value.matches("\\+?(\\(\\w+\\)([ -]\\w{2,})?|\\w+([ -]\\(\\w{2,}\\))?)([ -]\\w{2,})*")) {
            phone = value;
        } else {
            phone = "[No number]";
            System.out.println("Invalid number!");
        }
    }

    public abstract void info();

    public abstract String toInfo();
}

class PersonContact extends Contact {
    String name;
    String surname;
    LocalDate birthDay;
    Gender gender;

    public PersonContact(String name, String surname, String phone, LocalDate birthDay, Gender gender) {
        super(phone);
        this.name = name;
        this.surname = surname;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return name + " " + surname;// + " " + phone;
    }

    @Override
    List<String> getEditList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("name");
        list.add("surname");
        list.add("birth");
        list.add("gender");
        list.addAll(super.getEditList());
        return list;
    }

    @Override
    public boolean update(String fieldToEdit, String value) {
        if (!super.update(fieldToEdit, value)){
            switch (fieldToEdit) {
                case "name":
                    name = value;
                    change();
                    return true;
                case "surname":
                    surname = value;
                    change();
                    return true;
                case "birth":
                    try {
                        birthDay = LocalDate.parse(value);
                    } catch (DateTimeParseException e) {
                        System.out.println("Bad birth date!");
                        birthDay =  null;
                    }
                    change();
                    return true;
                case "gender":
                    gender = Gender.parse(value);
                    change();
                    return true;
                default:
                    return false;
            }
        };
        return true;
    }

    @Override
    public void info() {
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Birth date: " + (birthDay == null ? "[no data]" : birthDay));
        System.out.println("Gender: " + (gender == null ? "[no data]" : gender));
        System.out.println("Number: " + phone);
        System.out.println("Time created: " + created);
        System.out.println("Time last edit: " + changed);
    }

    @Override
    public String toInfo() {
        return name + surname + (birthDay == null ? "[no data]" : birthDay) + (gender == null ? "[no data]" : gender) + phone + created + changed;
    }
}

class OrganizationContact extends Contact {
    String orgName;
    String address;

    public OrganizationContact(String orgName, String address, String phone) {
        super(phone);
        this.orgName = orgName;
        this.address = address;
    }

    @Override
    public String toString() {
        return orgName;// + " " + phone;
    }

    @Override
    List<String> getEditList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("organization name");
        list.add("address");
        list.add("number");
        return list;
    }

    @Override
    public boolean update(String fieldToEdit, String value) {
        if (!super.update(fieldToEdit, value)){
            switch (fieldToEdit) {
                case "organization name":
                    orgName = value;
                    change();
                    return true;
                case "address":
                    address = value;
                    change();
                    return true;
                case "number":
                    setPhone(value);
                    change();
                    return true;
                default:
                    return false;
            }
        };
        return true;
    }

    @Override
    public void info() {
        System.out.println("Organization name: " + orgName);
        System.out.println("Address: " + address);
        System.out.println("Number: " + phone);
        System.out.println("Time created: " + created);
        System.out.println("Time last edit: " + changed);
    }

    @Override
    public String toInfo() {
        return orgName + address + phone + created + changed;
    }
}

class AddressBook {
    private ArrayList<Contact> contactList;
    String databaseName;
    private int activeItem;
    private ArrayList<Integer> searchResults;

    public AddressBook(String databaseName) throws IOException, ClassNotFoundException {
        this.databaseName = databaseName;
        contactList = new ArrayList<>();
        searchResults = new ArrayList<>();
        if (databaseName != null) {
            FileInputStream fis = new FileInputStream(databaseName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Contact contact;
            while ((contact = (Contact)ois.readObject()) != null) {
                contactList.add(contact);
            }
            ois.close();
            activeItem = -1;
        }
    }

    public void close() throws IOException {
        if (databaseName != null) {
            FileOutputStream fos = new FileOutputStream(databaseName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            for (Contact contact :
                    contactList) {
                oos.writeObject(contact);
            }
            oos.close();
        }
    }

    public void addPerson(String name, String surname, String phone, LocalDate birthDay, Gender gender) {
        contactList.add(new PersonContact(name, surname, phone, birthDay, gender));
    }

    public void addOrganization(String orgName, String address, String phone) {
        contactList.add(new OrganizationContact(orgName, address, phone));
    }

    public int size() {
        return contactList.size();
    }

    public void print() {
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println((i + 1) + ". " + contactList.get(i).toString());
        }
    }

    public void remove(int index) {
        contactList.remove(index);
    }

    public Contact getContact(int index) {
        return contactList.get(index);
    }

    public int search(String pattern) {
        searchResults.clear();
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < contactList.size(); i++) {
            Matcher matcher = p.matcher(contactList.get(i).toInfo());
            if (matcher.find()) {
                searchResults.add(i);
            }
        }
        return searchResults.size();
    }

    public void printResults() {
        System.out.println("Found " + searchResults.size() + " results:");
        for (int i = 0; i < searchResults.size(); i++) {
            System.out.println((i + 1) + ". " + toString(searchResults.get(i)));
        }
    }

    public String toString(int id) {
        return contactList.get(id).toString();
    }

    int select(int index) {
        activeItem = (index < contactList.size() && index >=0) ? index : -1;
        if (activeItem >= 0) {
            info();
        }
        return activeItem;
    }

    public void info() {
        contactList.get(activeItem).info();
    }

    public String toInfo() {
        return contactList.get(activeItem).toInfo();
    }

    public int searchResultsSize() {
        return searchResults.size();
    }

    public boolean updateContact(String fieldName, String value) {
        return contactList.get(activeItem).update(fieldName, value);
    }

    public List<String> getEditList() {
        return contactList.get(activeItem).getEditList();
    }

    public void deleteSelected() {
        contactList.remove(activeItem);
    }
}

