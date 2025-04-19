import java.util.InputMismatchException;
import java.util.Scanner;

public class W2051654_cinemaManagement {
    private static final int AVAILABLE = 0;  //display seat availability
    private static final int SOLD = 1;      // recognizable Developers understand that these values are not meant to change during runtime.
    private static final int[] PRICE = {12, 10, 8}; // to use the  calculate the price.
    private static int[][] seatMatrix;    //2D Arrays to keep track of seat availability.

    //to store tickets sold in the session.
    private static Ticket[] tickets;
    // Maximum tickets to allow in the session.
    private static final int MAX_TICKETS = 48;
    //track the number of sold tickets.
    private static int countTicketSold = 0;

    public static void main(String[] args) {
        System.out.println(" Welcome to The London Lumiere! ");

        //initialize and manage available seats.
        seatMatrix = initializeSeats();

        // initialize store tickets by array.
        tickets = new Ticket[MAX_TICKETS];
        // read user input created by scanner.
        Scanner scanner = new Scanner(System.in);

        boolean banner = true;//to display banner
        // User menu loop
        while (true) {
            try {
                // Display session menu options
                System.out.println("-----------------------------------------------");
                System.out.println("Please Select an Option: ");
                System.out.println("      1) Buy a ticket");
                System.out.println("      2) Cancel ticket");
                System.out.println("      3) see seating plan");
                System.out.println("      4) Find first seat available");
                System.out.println("      5) Print tickets information and total price");
                System.out.println("      6) Search ticket");
                System.out.println("      7) Sort tickets by price");
                System.out.println("      8) Exit");
                System.out.println("--------------------------------------------------");
                System.out.print("select an option: ");

                // Read user input
                int option = scanner.nextInt();

                // Perform actions based on user input
                switch (option) {
                    case 8:
                        banner = false;
                        System.out.println("Thank you for contacting  us...!!");//quit the programme.
                        return;
                    case 1:
                        buy_ticket(scanner);// Book a ticket (Task 1)
                        break;
                    case 2:
                        cancel_ticket(scanner);  // Cancel a ticket (Task 2)
                        break;
                    case 3:
                        print_seating_area();  // Show seating plan (Task 3)
                        break;
                    case 4:
                        find_first_seat_available();  // Find first available seat (Task 4)
                        break;
                    case 5:
                        print_tickets_info();  // Print tickets information and total prices (Task 5)
                        break;
                    case 6:
                        search_ticket(scanner);  // Search ticket (Task 6)
                        break;
                    case 7:
                        sort_tickets(); // Sort tickets by price and display all(Task 07)
                        break;
                    default:
                        System.out.println("Invalid option! Please select a valid option.");//if user input is not valid.
                }
            } catch (InputMismatchException e) { //if user input is not integer.
                System.out.println("Invalid Number! Please select a valid Number.");
                scanner.next();
            }
        }
    }


    ////method to find first available seat.
    private static void find_first_seat_available() {
        boolean found = false;
        // Iterate through each row
        for (int i = 0; i < seatMatrix.length; i++) {
            // Iterate through each seat in the row
            for (int j = 0; j < seatMatrix[i].length; j++) {
                // Check if the seat is available
                if (seatMatrix[i][j] == AVAILABLE && !found) {
                    // Convert to 1-based index for row and seat number
                    int rowNumber = i + 1;
                    int seatNumber = j + 1;
                    // Print the first available seat's row and seat number
                    System.out.println("First available Row Number is " + rowNumber + " and seat Number is " + seatNumber);
                    // Mark as found to prevent multiple prints
                    found = true;
                }
            }
        }
        // Print message if no available seats are found
        if (!found) {
            System.out.println("No available seats found!");
        }

    }

    // Method to initialize seats
    private static int[][] initializeSeats() {
        // Define the seat arrangement.
        seatMatrix = new int[][]{

                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 1 (16 seats);
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 2 (16 seats);
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Row 3 (16 seats);
        };

        return seatMatrix;// Return the seat arrangement.
    }

    // Method to buy  seat
    private static void buy_ticket(Scanner scanner) {
        // Check if maximum tickets have been sold
        if (countTicketSold >= MAX_TICKETS) {
            System.out.println("Maximum number of tickets sold, Cannot sell more tickets!");
            return;// Exit the method if maximum tickets have been sold.
        }
        // Prompt user to input row and seat number
        System.out.print("Enter the row number(1-3): ");
        int rowNumber = scanner.nextInt();
        System.out.print("Enter the seat number(1-16): ");
        int seatNumber = scanner.nextInt();

        // Convert row number to array index
        int row;
        switch (rowNumber) {
            case 1:
                row = 1;
                break;
            case 2:
                row = 2;
                break;
            case 3:
                row = 3;
                break;
            default:
                System.out.println("Invalid row Number! Please enter a valid row Number!(1-3)");
                return;// Exit the method if the row number is invalid.
        }
        // Check if the seat number is valid
        if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
            System.out.println("Invalid seat number! Please enter a valid seat number!(1-16)");
            return;// Exit the method if the seat number is invalid.
        }

        // Check if the seat is available
        if (seatMatrix[row][seatNumber - 1] == AVAILABLE) {    //array indexing starts from 0
            seatMatrix[row][seatNumber - 1] = SOLD;            // Record the seat as sold
            System.out.print("Enter person's name: ");         // Prompt user to input person's information
            String name = scanner.next();
            System.out.print("Enter person's surname: ");
            String surname = scanner.next();
            System.out.print("Enter person's email: ");
            String email = scanner.next();

            Person person = new Person(name, surname, email);    // Create a new Person object
            double price = calculatePrice(rowNumber);  // Calculate price using function based on seat location.
            Ticket ticket = new Ticket((int) rowNumber, (int) seatNumber, price, person);  // Create a new Ticket object
            tickets[countTicketSold++] = ticket;              // Add the ticket to the array of tickets
            System.out.println("The  Row Number " + rowNumber + " and Seat Number " + seatNumber + "  have been booked.");
        } else {
            System.out.println("The  Row Number " + rowNumber + " and Seat Number " + seatNumber + "  is not available.");
        }
    }

    // Calculate price based on seat location
    public static double calculatePrice(int rowNumber) {
        double price;
        if (rowNumber == 1) {
            price = 12;
        } else if (rowNumber == 2) {
            price = 10;
        } else if (rowNumber == 3) {
            price = 8;
        } else {
            return 0;//Default prices  for invalid rows.
        }
        return price;
    }


    //method to seat cancel.
    private static void cancel_ticket(Scanner scanner) {
        System.out.print("Enter the row number you want to cancel(1-3): ");
        int rowNumber = scanner.nextInt();
        System.out.print("Enter the seat number you want to cancel(1-16): ");
        int seatNumber = scanner.nextInt();


        // Convert row Number to array index
        int row;
        switch (rowNumber) {
            case 1:
                row = 1;
                break;
            case 2:
                row = 2;
                break;
            case 3:
                row = 3;
                break;
            default:
                System.out.println("Invalid row Number! Please enter a valid row (1-3).");
                return;
        }
        // Check if the seat number is valid
        if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
            System.out.println("Invalid seat number! Please enter a valid seat number.");
            return;// Exit the method if the seat number is invalid.
        }

        if (seatMatrix[row][seatNumber - 1] == SOLD) {       // Check if the seat is sold
            seatMatrix[row][seatNumber - 1] = AVAILABLE;     // Mark the seat as available
            for (int i = 0; i < countTicketSold; i++) {       // Delete the corresponding ticket from the array of tickets
                Ticket ticket = tickets[i];
                if (ticket.getRow() == rowNumber && ticket.getSeat() == seatNumber) {
                    for (int j = i; j < countTicketSold - 1; j++) {            // Shift tickets one position left to fill the gap
                        tickets[j] = tickets[j + 1];
                    }
                    countTicketSold--;                                       // Reduce the count of tickets sold
                    System.out.println(" The Seat Row Number " + rowNumber + " and Seat Number " + seatNumber + "  has been successfully canceled!");
                    return;//exit the method
                }
            }
        } else {// The seat is not sold.
            System.out.println("Seat Row Number " + rowNumber + " and Seat Number " + seatNumber + "  is already available!");
        }
    }

    //method to show seating area.
    private static void print_seating_area() {
        System.out.println("**********************");
        System.out.println("*     SCREEN    *");
        System.out.println("**********************");

        // Loop through the seat matrix and display 'O' for available seats and 'X' for sold seats
        for (int[] matrix : seatMatrix) {// Loop through each row
            for (int j = 0; j < matrix.length; j++) {// Loop through each seat
                if (j == 8) {// If the seat is in the 9th position
                    System.out.print("  "); // gap between seat 8 & 9)
                } else if (matrix[j] == AVAILABLE) {// If the seat is available
                    System.out.print("O ");
                } else {// If the seat is sold
                    System.out.print("X ");
                }
            }
            System.out.println(); // Move to the next line after each row
        }
    }


    // Method to print tickets information and calculate total price
    private static void print_tickets_info() {
        double totalPrice = 0;// Initialize total price to 0
        System.out.println("Tickets Information:");

        // Iterate through the tickets array and print ticket information
        for (int i = 0; i < countTicketSold; i++) {
            Ticket ticket = tickets[i];
            System.out.println("Ticket " + (i + 1) + ":");// Print ticket number.
            System.out.println("Row: " + ticket.getRow());
            System.out.println("Seat: " + ticket.getSeat());
            System.out.println("Price: £" + ticket.getPrice());
            System.out.println("Person Information:");
            System.out.println("Name: " + Person.getName());
            System.out.println("Surname: " + Person.getSurname());
            System.out.println("Email: " + Person.getEmail());
            totalPrice += ticket.getPrice();
            System.out.println("---------------------------------------");
        }
        System.out.println("Total Price of Tickets Sold: £" + totalPrice);// Print total price
    }

    // Method to ticket search.
    private static void search_ticket(Scanner Scanner) {
        // Prompt user to input row Number and seat number
        System.out.print("Enter the row number you want to search(1-3): ");
        int rowNumber = Scanner.nextInt();
        System.out.print("Enter the seat number you want to search(1-16): ");
        int seatNumber = Scanner.nextInt();

        // Convert row Number to array index
        int row;
        switch (rowNumber) {
            case 1:
                row = 1;
                break;
            case 2:
                row = 2;
                break;
            case 3:
                row = 3;
                break;
            default:// Invalid row Number
                System.out.println("Invalid row Number! Please enter a valid row (1-3).");
                return;// Exit the method
        }

        // Check if the seat number is valid
        if (seatNumber < 1 || seatNumber > seatMatrix[row].length) {
            System.out.println("Invalid seat number! Please enter a valid seat number.");
            return;// Exit the method
        }

        // Check if the seat is sold
        if (seatMatrix[row][seatNumber - 1] == SOLD) {
            // Search for the ticket.
            for (int i = 0; i < countTicketSold; i++) {
                Ticket ticket = tickets[i];// Get ticket from tickets array
                if (ticket.getRow() == rowNumber && ticket.getSeat() == seatNumber) {// Check if the ticket is found
                    // Print ticket and person information
                    System.out.println("Ticket Found!");
                    System.out.println("Ticket Information:");
                    System.out.println("Row: " + ticket.getRow());
                    System.out.println("Seat: " + ticket.getSeat());
                    System.out.println("Price: £" + ticket.getPrice());
                    System.out.println("Person Information:");
                    System.out.println("Name: " + Person.getName());
                    System.out.println("Surname: " + Person.getSurname());
                    System.out.println("Email: " + Person.getEmail());
                    return;// Exit the method

                }
            }
        } else {// If the seat is available
            System.out.println("This seat is available!");
        }
    }


    //Method to Arrange Ticket by price.
    public static void sort_tickets() {
        for (int i = 0; i < countTicketSold - 1; i++) { // bubble sort algorithm to sort tickets by price.
            for (int k = 0; k < countTicketSold - 1; k++) {// Iterate through the tickets array
                if (tickets[k].getPrice() > tickets[k + 1].getPrice()) {// Check if the price of ticket k is greater than ticket k+1
                    //switch tickets[k] and tickets[k+1]
                    Ticket temp = tickets[k];//Store the value in temporary variable
                    tickets[k] = tickets[k + 1];//Assign the value for K+1 to K
                    tickets[k + 1] = temp;//Assign the value of temp to K+1.
                }

            }
        }
        //display Tickets By Order.
        System.out.println("Tickets sorted by price: ");
        for (int i = 0; i < countTicketSold; i++) {// Iterate through the tickets array
            // Print ticket and person information
            System.out.println("Ticket Information:");
            Ticket ticket;//Get ticket from tickets array
            ticket = tickets[i];
            System.out.println("Row: " + ticket.getRow());
            System.out.println("Seat: " + ticket.getSeat());
            System.out.println("Price: £" + ticket.getPrice());
            System.out.println("Person Information:");
            System.out.println("Name: " + Person.getName());
            System.out.println("Surname: " + Person.getSurname());
            System.out.println("Email: " + Person.getEmail());
            return;// Exit the method
        }
        System.out.println("---------------------------------------");
    }
}

























