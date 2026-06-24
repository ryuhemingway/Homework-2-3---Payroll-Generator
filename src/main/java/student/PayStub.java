package student;

public class PayStub implements IPayStub {

    private final IEmployee employee;
    private final double pay;
    private final double taxesPaid;

    public PayStub(IEmployee employee, double pay, double taxesPaid) {
        this.employee = employee;
        this.pay = round(pay);
        this.taxesPaid = round(taxesPaid);
    }

    @Override
    public double getPay() {
        return pay;
    }

    @Override
    public double getTaxesPaid() {
        return taxesPaid;
    }

    @Override
    public String toCSV() {
        return employee.getName() + ","
                + pay + ","
                + taxesPaid + ","
                + employee.getYTDEarnings() + ","
                + employee.getYTDTaxesPaid();
    }

    private static double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}