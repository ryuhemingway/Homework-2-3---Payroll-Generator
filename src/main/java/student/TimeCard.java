package student;

public class TimeCard implements ITimeCard {
    private final String employeeID;
    private final double hoursWorked;

    public TimeCard(String employeeID, double hoursWorked) {
        this.employeeID = employeeID;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public double getHoursWorked() {
        return hoursWorked;
    }
}