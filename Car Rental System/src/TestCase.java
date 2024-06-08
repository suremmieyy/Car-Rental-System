import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

public class CarRentalSystemTest {
    private CarRentalSystem carRentalSystem;
    private Car car1;
    private Car car2;
    private Customer customer1;
    private Customer customer2;
    private Date startDate;
    private Date endDate;

    @BeforeEach
    public void setup() {
        carRentalSystem = new CarRentalSystem();

        car1 = new Car(1, "Car 1");
        car2 = new Car(2, "Car 2");

        customer1 = new Customer(1, "Customer 1");
        customer2 = new Customer(2, "Customer 2");

        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);

        carRentalSystem.addCustomer(customer1);
        carRentalSystem.addCustomer(customer2);

        Calendar start = Calendar.getInstance();
        start.set(2022, Calendar.JANUARY, 1);
        Calendar end = Calendar.getInstance();
        end.set(2022, Calendar.JANUARY, 10);
        startDate = start.getTime();
        endDate = end.getTime();
    }

    @Test
    public void testAddCar() {
        Car car3 = new Car(3, "Car 3");
        carRentalSystem.addCar(car3);

        Car foundCar = carRentalSystem.getCarById(3);
        Assertions.assertEquals(car3, foundCar);
    }

    @Test
    public void testRemoveCarById() {
        carRentalSystem.removeCarById(2);

        Car removedCar = carRentalSystem.getCarById(2);
        Assertions.assertNull(removedCar);
    }

    @Test
    public void testAddCustomer() {
        Customer customer3 = new Customer(3, "Customer 3");
        carRentalSystem.addCustomer(customer3);

        Customer foundCustomer = carRentalSystem.getCustomerById(3);
        Assertions.assertEquals(customer3, foundCustomer);
    }

    @Test
    public void testRemoveCustomerById() {
        carRentalSystem.removeCustomerById(2);

        Customer removedCustomer = carRentalSystem.getCustomerById(2);
        Assertions.assertNull(removedCustomer);
    }

    @Test
    public void testRentCar() {
        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, startDate, endDate);

        Assertions.assertNotNull(rentalTransaction);
        Assertions.assertEquals(car1, rentalTransaction.getCar());
        Assertions.assertEquals(customer1, rentalTransaction.getCustomer());
        Assertions.assertTrue(rentalTransaction.getCar().isRented());
    }

    @Test
    public void testRentCarInvalidDates() {
        Calendar invalidEndDate = Calendar.getInstance();
        invalidEndDate.set(2022, Calendar.JANUARY, 1);

        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, endDate, invalidEndDate.getTime());

        Assertions.assertNull(rentalTransaction);
    }

    @Test
    public void testReturnCar() {
        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, startDate, endDate);
        Assertions.assertNotNull(rentalTransaction);

        boolean isReturned = carRentalSystem.returnCar(rentalTransaction.getId(), endDate);
        Assertions.assertTrue(isReturned);
        Assertions.assertFalse(rentalTransaction.getCar().isRented());
    }

    @Test
    public void testReturnCarInvalidReturnDate() {
        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, startDate, endDate);
        Assertions.assertNotNull(rentalTransaction);

        Calendar invalidReturnDate = Calendar.getInstance();
        invalidReturnDate.set(2024, Calendar.JUNE, 10);

        boolean isReturned = carRentalSystem.returnCar(rentalTransaction.getId(), invalidReturnDate.getTime());

        Assertions.assertFalse(isReturned);
        Assertions.assertTrue(rentalTransaction.getCar().isRented());
    }

    @Test
    public void testCalculateRentalCost() {
        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, startDate, endDate);
        Assertions.assertNotNull(rentalTransaction);

        double rentalCost = carRentalSystem.calculateRentalCost(rentalTransaction);
        Assertions.assertEquals(500.0, rentalCost);
    }

    @Test
    public void testRentMultipleCarsAndReturn() {
        RentalTransaction rentalTransaction1 = carRentalSystem.rentCar(1, 1, startDate, endDate);
        RentalTransaction rentalTransaction2 = carRentalSystem.rentCar(2, 1, startDate, endDate);

        Assertions.assertNotNull(rentalTransaction1);
        Assertions.assertNotNull(rentalTransaction2);
        Assertions.assertTrue(rentalTransaction1.getCar().isRented());
        Assertions.assertTrue(rentalTransaction2.getCar().isRented());

        boolean isReturned1 = carRentalSystem.returnCar(rentalTransaction1.getId(), endDate);
        boolean isReturned2 = carRentalSystem.returnCar(rentalTransaction2.getId(), endDate);

        Assertions.assertTrue(isReturned1);
        Assertions.assertTrue(isReturned2);
        Assertions.assertFalse(rentalTransaction1.getCar().isRented());
        Assertions.assertFalse(rentalTransaction2.getCar().isRented());
    }


}
