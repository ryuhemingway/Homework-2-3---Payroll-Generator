package student;

public abstract class AbstractEmployee implements IEmployee {

    protected static final double TAX_RATE = 0.2265;

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

    protected abstract double calculateGrossPay(double hoursWorked);

    @Override
    public IPayStub runPayroll(double hoursWorked) {
        if (hoursWorked < 0) {
            return null;
        }

        double grossPay = calculateGrossPay(hoursWorked);
        double taxablePay = Math.max(0.0, grossPay - pretaxDeductions);

        double unroundedTaxes = taxablePay * TAX_RATE;
        double roundedTaxes = round(unroundedTaxes);

        double payStubNetPay = round(grossPay - pretaxDeductions - unroundedTaxes);
        double ytdNetPay = round(grossPay - pretaxDeductions - roundedTaxes);

        ytdEarnings = round(ytdEarnings + ytdNetPay);
        ytdTaxesPaid = round(ytdTaxesPaid + roundedTaxes);

        return new PayStub(this, payStubNetPay, roundedTaxes);
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

    protected static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}