import java.util.ArrayList;
import java.util.List;

class Passenger {
    private String name;
    private String passengerNumber;
    private String passengerType;
    private double balance;
    private List<Activity> activitiesSignedUp;

    public Passenger(String name, String passengerNumber, String passengerType, double balance) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.passengerType = passengerType;
        this.balance = balance;
        this.activitiesSignedUp = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPassengerNumber() {
        return passengerNumber;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public double getBalance() {
        return balance;
    }

    public boolean signUpForActivity(Activity activity) {
        if (activity.isAvailable()) {
            double cost = activity.getCost();
            if (passengerType.equals("standard")) {
                if (balance >= cost) {
                    balance -= cost;
                    activitiesSignedUp.add(activity);
                    activity.incrementCurrentCapacity();
                    return true;
                }
            } else if (passengerType.equals("gold")) {
                double discountedCost = cost * 0.9;
                if (balance >= discountedCost) {
                    balance -= discountedCost;
                    activitiesSignedUp.add(activity);
                    activity.incrementCurrentCapacity();
                    return true;
                }
            } else if (passengerType.equals("premium")) {
                activitiesSignedUp.add(activity);
                activity.incrementCurrentCapacity();
                return true;
            }
        }
        return false;
    }

    public List<Activity> getActivitiesSignedUp() {
        return activitiesSignedUp;
    }
}

class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int currentCapacity;
    private Destination destination;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.currentCapacity = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public boolean isAvailable() {
        return currentCapacity < capacity;
    }

    public void incrementCurrentCapacity() {
        currentCapacity++;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        activity.setDestination(this);
    }
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Destination> itinerary;
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity, List<Destination> itinerary) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.itinerary = itinerary;
        this.passengers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public List<Destination> getItinerary() {
        return itinerary;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Example usage
        Destination destination1 = new Destination("Mountain Resort");
        Destination destination2 = new Destination("Beach Resort");

        Activity activity1 = new Activity("Hiking", "Enjoy a hike in the mountains.", 20, 10);
        Activity activity2 = new Activity("Scuba Diving", "Explore the underwater world.", 50, 5);

        destination1.addActivity(activity1);
        destination2.addActivity(activity2);

        List<Destination> itinerary = new ArrayList<>();
        itinerary.add(destination1);
        itinerary.add(destination2);

        TravelPackage travelPackage = new TravelPackage("Adventure Package", 10, itinerary);

        Passenger passenger1 = new Passenger("John Doe", "P001", "standard", 100);
        Passenger passenger2 = new Passenger("Jane Doe", "P002", "gold", 200);
        Passenger passenger3 = new Passenger("Alice Smith", "P003", "premium", 0);

        travelPackage.addPassenger(passenger1);
        travelPackage.addPassenger(passenger2);
        travelPackage.addPassenger(passenger3);

        // Signing up passengers for activities
        passenger1.signUpForActivity(activity1);
        passenger2.signUpForActivity(activity2);

        // Print details of passengers and their signed up activities
        for (Passenger passenger : travelPackage.getPassengers()) {
            System.out.println(passenger.getName() + ":");
            for (Activity activity : passenger.getActivitiesSignedUp()) {
                System.out.println("- " + activity.getName() + " at " + activity.getDestination().getName());
            }
        }
    }
}
