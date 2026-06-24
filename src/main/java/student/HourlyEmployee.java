package student;

public class HourlyEmployee extends AbstractEmployee {

    public HourlyEmployee(String name, String id, double payRate, double ytdEarnings,
                          double ytdTaxesPaid, double pretaxDeductions) {
        super(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
    }

    @Override
    public String getEmployeeType() {
        return "HOURLY";
    }

    @Override
    protected double calculateGrossPay(double hoursWorked) {
        if (hoursWorked <= 40.0) {
            return getPayRate() * hoursWorked;
        }

        double regularPay = getPayRate() * 40.0;
        double overtimeHours = hoursWorked - 40.0;
        double overtimePay = overtimeHours * getPayRate() * 1.5;

        return regularPay + overtimePay;
    }
}