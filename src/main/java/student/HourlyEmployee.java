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
}