package student;

public abstract class AbstractEmployee implements IEmployee {

    private final String name;
    private final String id;
    private final double payRate;
    private double ytdEarnings;
    private double ytdTaxesPaid;
    private final double pretaxDeductions;

    public AbstractEmployee(String name, String id, double payRate, double ytdEarnings,
                            double ytdTaxesPaid, double pretaxDeductions) {
        this.name = name;
        this.id = id;
        this.payRate = payRate;
        this.ytdEarnings = ytdEarnings;
        this.ytdTaxesPaid = ytdTaxesPaid;
        this.pretaxDeductions = pretaxDeductions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public double getPayRate() {
        return payRate;
    }

    @Override
    public double getYTDEarnings() {
        return ytdEarnings;
    }

    @Override
    public double getYTDTaxesPaid() {
        return ytdTaxesPaid;
    }

    @Override
    public double getPretaxDeductions() {
        return pretaxDeductions;
    }

    @Override
    public String toCSV() {
        return getEmployeeType() + ","
                + name + ","
                + id + ","
                + payRate + ","
                + pretaxDeductions + ","
                + ytdEarnings + ","
                + ytdTaxesPaid;
    }

    @Override
    public IPayStub runPayroll(double hoursWorked) {
        return null;
    }
}