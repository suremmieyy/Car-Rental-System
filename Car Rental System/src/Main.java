import java.util.*;

class Car {
    private int id;
    private String name;
    private boolean rented;

    public Car(int id, String name) {
        this.id = id;
        this.name = name;
        this.rented = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}

class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class RentalTransaction {
    private int id;
    private Car car;
    private Customer customer;
    private Date startDate;
    private Date endDate;

    public RentalTransaction(int id, Car car, Customer customer, Date startDate, Date endDate) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<RentalTransaction> rentalTransactions;
    private Map<String, Integer> customerTypes;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentalTransactions = new ArrayList<>();
        customerTypes = new HashMap<>();
        customerTypes.put("Regular", 0);
        customerTypes.put("VIP", 1);
        customerTypes.put("Corporate", 2);
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCarById(int carId) {
        cars.removeIf(car -> car.getId() == carId);
    }

    public Car getCarById(int carId) {
        for (Car car : cars) {
            if (car.getId() == carId) {
                return car;
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void removeCustomerById(int customerId) {
        customers.removeIf(cust -> cust.getId() == customerId);
    }

    public Customer getCustomerById(int customerId) {
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null;
    }

    public RentalTransaction rentCar(int carId, int customerId, Date startDate, Date endDate) {
        Car car = getCarById(carId);
        Customer customer = getCustomerById(customerId);

        if (car != null && customer != null && !car.isRented() && startDate.before(endDate)) {
            RentalTransaction rentalTransaction = new RentalTransaction(
                    rentalTransactions.size() + 1, car, customer, startDate, endDate);
            car.setRented(true);
            rentalTransactions.add(rentalTransaction);
            return rentalTransaction;
        }
        return null;
    }

    public boolean returnCar(int rentalId, Date returnDate) {
        for (RentalTransaction rentalTransaction : rentalTransactions) {
            if (rentalTransaction.getId() == rentalId && rentalTransaction.getEndDate().before(returnDate)) {
                rentalTransaction.getCar().setRented(false);
                return true;
            }
        }
        return false;
    }

    public double calculateRentalCost(RentalTransaction rentalTransaction) {
        int days = getRentalDurationInDays(rentalTransaction);
        double baseCost = rentalTransaction.getCar().isRented() ? getCarBaseCost(rentalTransaction.getCar()) : 0;

        int customerType = getCustomerType(rentalTransaction.getCustomer());
        double discount = getDiscount(customerType);

        return baseCost * days * (1 - discount);
    }

    private int getRentalDurationInDays(RentalTransaction rentalTransaction) {
        long diffInMillies = rentalTransaction.getEndDate().getTime() - rentalTransaction.getStartDate().getTime();
        return (int) (diffInMillies / (24 * 60 * 60 * 1000));
    }

    private double getCarBaseCost(Car car) {

        return 50;
    }

    private int getCustomerType(Customer customer) {

        return customerTypes.getOrDefault(customer.getName(), 0);
    }

    private double getDiscount(int customerType) {

        switch (customerType) {
            case 1:
                return 0.1;
            case 2:
                return 0.2;
            default:
                return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Create car rental system
        CarRentalSystem carRentalSystem = new CarRentalSystem();

        // Create cars
        Car car1 = new Car(1, "Car 1");
        Car car2 = new Car(2, "Car 2");
        Car car3 = new Car(3, "Car 3");

        // Add cars to the system
        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);
        carRentalSystem.addCar(car3);

        // Create customers
        Customer customer1 = new Customer(1, "Customer 1");
        Customer customer2 = new Customer(2, "Customer 2");

        // Add customers to the system
        carRentalSystem.addCustomer(customer1);
        carRentalSystem.addCustomer(customer2);

        // Rent a car
        Calendar start = Calendar.getInstance();
        start.set(2024, Calendar.JUNE, 5);
        Calendar end = Calendar.getInstance();
        end.set(2024, Calendar.JUNE, 10);
        Date startDate = start.getTime();
        Date endDate = end.getTime();

        RentalTransaction rentalTransaction = carRentalSystem.rentCar(1, 1, startDate, endDate);
        if (rentalTransaction != null) {
            System.out.println("Car rented: " + rentalTransaction.getCar().getName() + " by "
                    + rentalTransaction.getCustomer().getName());
        } else {
            System.out.println("Failed to rent car");
        }

        // Return the rented car
        Calendar returnDate = Calendar.getInstance();
        returnDate.set(2024, Calendar.JUNE, 5);
        Date returnDt = returnDate.getTime();
        boolean isReturned = carRentalSystem.returnCar(rentalTransaction.getId(), returnDt);
        if (isReturned) {
            System.out.println("Car returned");
        } else {
            System.out.println("Failed to return car");
        }

        // Remove a car
        carRentalSystem.removeCarById(3);
    }
}
