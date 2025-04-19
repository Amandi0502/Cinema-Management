import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

public class Ticket {
    private int row;
    private int seat;
    private double price;
    private Person person;


    // Constructor to initialize the ticket data.
    public Ticket(int row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;

    }


    // Getters and setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print information of a Ticket & Person.
    public void printTicketInfo() {
        System.out.println("Ticket Information: ");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + price);
        System.out.println("Person Information: ");
        person.printPersonInfo();// Print person information
    }

    // Method to store  ticket information data  to a file.
    public void store() {
        String fileName = "ticket" + row + seat + ".txt";// Create a file name
        try (FileWriter fileWriter = new FileWriter(fileName)) {// Create a file writer
            // Write ticket information to the file
            fileWriter.write("Ticket Information:\n");
            fileWriter.write("Row: " + row + "\n");
            fileWriter.write("Seat: " + seat + "\n");
            fileWriter.write("Price: £" + price + "\n");
            fileWriter.write("Person Information:\n");
            fileWriter.write("Name: " + Person.getName() + "\n");
            fileWriter.write("Surname: " + Person.getSurname() + "\n");
            fileWriter.write("Email: " + Person.getEmail() + "\n");
            System.out.println(fileName + "for a ticket sold in row" + row + "seat" + seat + "has been saved.");
        } catch (IOException e) {
            System.out.println("Error saving ticket information to file: " + e.getMessage());// Print error message.
        }
    }
}







