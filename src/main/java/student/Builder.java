package student;

/**
 * This class helps build payroll objects from CSV strings.
 */
public final class Builder {

    private static final int EMPLOYEE_COLUMNS = 7;
    private static final int TIME_CARD_COLUMNS = 2;

    private Builder() {
        // Prevents someone from creating a Builder object.
    }

    /**
     * Builds an employee object from a CSV string.
     *
     * CSV format:
     * employee_type,name,ID,payRate,pretaxDeductions,YTDEarnings,YTDTaxesPaid
     *
     * Example:
     * HOURLY,Luffy,s192,30.00,0,20000,4530
     *
     * @param csv the CSV string
     * @return the employee object
     */
    public static IEmployee buildEmployeeFromCSV(String csv) {
        String[] parts = splitAndValidate(csv, EMPLOYEE_COLUMNS, "employee");

        String employeeType = parts[0].toUpperCase();
        String name = parts[1];
        String id = parts[2];

        double payRate = parseDouble(parts[3], "pay rate");
        double pretaxDeductions = parseDouble(parts[4], "pretax deductions");
        double ytdEarnings = parseDouble(parts[5], "YTD earnings");
        double ytdTaxesPaid = parseDouble(parts[6], "YTD taxes paid");

        if (employeeType.equals("HOURLY")) {
            return new HourlyEmployee(
                    name,
                    id,
                    payRate,
                    ytdEarnings,
                    ytdTaxesPaid,
                    pretaxDeductions
            );
        }

        if (employeeType.equals("SALARY")) {
            return new SalaryEmployee(
                    name,
                    id,
                    payRate,
                    ytdEarnings,
                    ytdTaxesPaid,
                    pretaxDeductions
            );
        }

        throw new IllegalArgumentException("Unknown employee type: " + parts[0]);
    }

    /**
     * Builds a time card object from a CSV string.
     *
     * CSV format:
     * employee_id,hours_worked
     *
     * Example:
     * s192,45
     *
     * @param csv the CSV string
     * @return the time card object
     */
    public static ITimeCard buildTimeCardFromCSV(String csv) {
        String[] parts = splitAndValidate(csv, TIME_CARD_COLUMNS, "time card");

        String employeeID = parts[0];
        double hoursWorked = parseDouble(parts[1], "hours worked");

        return new TimeCard(employeeID, hoursWorked);
    }

    /**
     * Splits a CSV row and checks that it has the expected number of columns.
     *
     * @param csv the original CSV line
     * @param expectedColumns how many columns the row should have
     * @param rowType description for error messages
     * @return cleaned-up array of values
     */
    private static String[] splitAndValidate(String csv, int expectedColumns, String rowType) {
        if (csv == null || csv.isBlank()) {
            throw new IllegalArgumentException("Blank " + rowType + " row");
        }

        String[] parts = csv.split(",", -1);

        if (parts.length != expectedColumns) {
            throw new IllegalArgumentException("Invalid " + rowType + " row: " + csv);
        }

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();

            if (parts[i].isEmpty()) {
                throw new IllegalArgumentException("Empty value in " + rowType + " row: " + csv);
            }
        }

        return parts;
    }

    /**
     * Parses a string into a double.
     *
     * @param value the string value
     * @param fieldName the field name for error messages
     * @return the double value
     */
    private static double parseDouble(String value, String fieldName) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number for " + fieldName + ": " + value);
        }
    }
}