Car Rental System
The Car Rental System is a software application designed to automate and manage car rental operations. It provides functionalities such as adding cars, managing customers, renting and returning cars, calculating rental costs, and more.

Features;
Add and remove cars from the system.
Add and remove customers from the system.
Rent cars to customers for a specified time period.
Return rented cars and update their status.
Calculate the rental cost based on the rental duration, car type, and customer type.
Support for different customer types (Regular, VIP, and Corporate) with customizable discounts.
Robust error handling and validations to ensure data integrity.
Modular and extensible design for easy maintenance and scalability.

Technologies Used
Java: Programming language used for developing the Car Rental System.
Object-Oriented Design: Utilized to implement modular and reusable code.
JUnit: Testing framework for creating unit tests to validate the functionality of the system.
Git: Version control system for managing and tracking changes to the codebase.

Getting Started
To run the Car Rental System, follow these steps:

Make sure you have Java Development Kit (JDK) installed on your machine.
Clone the repository to your local machine using the following command:

$ git clone <repository_url>
Open the project in your preferred Java IDE.
Build the project to compile the source code.
Run the Main class to start the Car Rental System application.

Usage
The Car Rental System application provides a Java API for managing car rental operations. You can use the provided classes and methods to interact with the system programmatically.

Here's an example of how to rent a car using the Car Rental System:

// Create an instance of CarRentalSystem
CarRentalSystem carRentalSystem = new CarRentalSystem();

// Adding cars and customers to the system (using addCar() and addCustomer() methods)

// Renting a car
RentalTransaction rentalTransaction = carRentalSystem.rentCar(carId, customerId, startDate, endDate);
if (rentalTransaction != null) {
    // Car rented successfully
} else {
    // Failed to rent car
}

// Returning a car
boolean isReturned = carRentalSystem.returnCar(rentalId, returnDate);
if (isReturned) {
    // Car returned successfully
} else {
    // Failed to return car
}

// Calculate rental cost
double rentalCost = carRentalSystem.calculateRentalCost(rentalTransaction);
// Use the rental cost as needed
Refer to the code documentation and JavaDoc comments for a detailed description of each method and its usage.

Testing
The Car Rental System includes a comprehensive suite of unit tests written using JUnit. These tests cover various scenarios and edge cases to ensure the correctness and reliability of the system's functionalities.

To run the tests, you can execute the test suite using your preferred IDE . Ensure that the required testing dependencies, such as JUnit, are installed and properly configured.



