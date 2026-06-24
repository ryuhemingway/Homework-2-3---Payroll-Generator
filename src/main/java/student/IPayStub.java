package student;

/**
 * An interface for the concept of the pay stub.
 * 
 * DO NOT MODIFY THIS FILE! (unless you are cleaning up comments/style changes)
 * 
 */
public interface IPayStub {

    /**
     * Gets the pay for the current pay period.
     * 
     * @return the pay for the current pay period
     */
    double getPay();

    /**
     * Gets the taxes paid for the current pay period.
     * 
     * @return the taxes paid for the current pay period
     */
    double getTaxesPaid();

    /**
     * Converts the PayStub object to a CSV string.
     * 
     * Format of the CSV string is: "employee_name,net_pay,taxes,ytd_earnings,ytd_taxes_paid"
     * 
     * @return the CSV string
     */
    String toCSV();

}
