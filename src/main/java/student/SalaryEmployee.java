package student;

public class SalaryEmployee extends AbstractEmployee {

    public SalaryEmployee(String name, String id, double payRate, double ytdEarnings,
                          double ytdTaxesPaid, double pretaxDeductions) {
        super(name, id, payRate, ytdEarnings, ytdTaxesPaid, pretaxDeductions);
    }

    @Override
    public String getEmployeeType() {
        return "SALARY";
    }

    @Override
    protected double calculateGrossPay(double hoursWorked) {
        return getPayRate() / 24.0;
    }
}