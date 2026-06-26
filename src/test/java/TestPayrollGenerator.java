/*
 * Students, build off this class. We are providing one sample test case as file reading is new to
 * you.
 * 
 * NOTE: you may end up changing this completely depending on how you setup your project.
 * 
 * we are just using .main() as we know that is an entry point that we specified.
 * 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import student.PayrollGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import student.ITimeCard;
import student.TimeCard;
import student.IPayStub;
import student.PayStub;
import student.IEmployee;
import student.HourlyEmployee;
import student.SalaryEmployee;
import student.Builder;

public class TestPayrollGenerator {

    @TempDir
    static Path tempDir;


    @Test
    public void testFinalPayStub() throws IOException {
        // copy employees.csv into tempDir
        Path employees = tempDir.resolve("employees.csv");
        Files.copy(Paths.get("resources/employees.csv"), employees);

        // get the path of the paystubs.csv
        Path payStubs = tempDir.resolve("paystubs.csv");


        String[] args = {"-e", employees.toString(), "-t", "resources/time_cards.csv", // allowed,
                // this isn't
                // modified -
                // so safe
                "-o", payStubs.toString()};

        // run main method
        PayrollGenerator.main(args);


        String expectedPayStubs = Files
                .readString(Paths.get("resources/original/pay_stubs_solution_to_original.csv"));

        String actualPayStubs = Files.readString(payStubs);

        assertEquals(expectedPayStubs, actualPayStubs);

    }

    @Test
    public void testTimeCardStoresEmployeeIDAndHoursWorked() {
        ITimeCard timeCard = new TimeCard("s192", 45.0);

        assertEquals("s192", timeCard.getEmployeeID());
        assertEquals(45.0, timeCard.getHoursWorked(), 0.001);
    }
    @Test
    public void testPayStubStoresPayAndTaxes() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);
        IPayStub payStub = new PayStub(employee, 1102.24, 322.76);

        assertEquals(1102.24, payStub.getPay(), 0.001);
        assertEquals(322.76, payStub.getTaxesPaid(), 0.001);
    }

    @Test
    public void testPayStubToCSV() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);
        IPayStub payStub = new PayStub(employee, 1102.24, 322.76);

        assertEquals("Luffy,1102.24,322.76,20000.0,4530.0", payStub.toCSV());
    }

    @Test
    public void testHourlyEmployeeStoresBasicInfo() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);

        assertEquals("HOURLY", employee.getEmployeeType());
        assertEquals("Luffy", employee.getName());
        assertEquals("s192", employee.getID());
        assertEquals(30.0, employee.getPayRate(), 0.001);
        assertEquals(20000.0, employee.getYTDEarnings(), 0.001);
        assertEquals(4530.0, employee.getYTDTaxesPaid(), 0.001);
        assertEquals(0.0, employee.getPretaxDeductions(), 0.001);
    }

    @Test
    public void testSalaryEmployeeStoresBasicInfo() {
        IEmployee employee = new SalaryEmployee("Nami", "s193", 200000.0, 17017.0, 4983.0, 1000.0);

        assertEquals("SALARY", employee.getEmployeeType());
        assertEquals("Nami", employee.getName());
        assertEquals("s193", employee.getID());
        assertEquals(200000.0, employee.getPayRate(), 0.001);
        assertEquals(17017.0, employee.getYTDEarnings(), 0.001);
        assertEquals(4983.0, employee.getYTDTaxesPaid(), 0.001);
        assertEquals(1000.0, employee.getPretaxDeductions(), 0.001);
    }
    @Test
    public void testEmployeeToCSV() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);

        assertEquals("HOURLY,Luffy,s192,30.0,0.0,20000.0,4530.0", employee.toCSV());
    }
    @Test
    public void testHourlyEmployeePayrollWithOvertime() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);

        IPayStub payStub = employee.runPayroll(45.0);

        assertEquals(1102.24, payStub.getPay(), 0.001);
        assertEquals(322.76, payStub.getTaxesPaid(), 0.001);
        assertEquals(21102.24, employee.getYTDEarnings(), 0.001);
        assertEquals(4852.76, employee.getYTDTaxesPaid(), 0.001);
        assertEquals("Luffy,1102.24,322.76,21102.24,4852.76", payStub.toCSV());
    }
    @Test
    public void testSalaryEmployeePayroll() {
        IEmployee employee = new SalaryEmployee("Nami", "s193", 200000.0, 17017.0, 4983.0, 1000.0);

        IPayStub payStub = employee.runPayroll(60.0);

        assertEquals(5672.33, payStub.getPay(), 0.001);
        assertEquals(1661.0, payStub.getTaxesPaid(), 0.001);
        assertEquals(22689.33, employee.getYTDEarnings(), 0.001);
        assertEquals(6644.0, employee.getYTDTaxesPaid(), 0.001);
        assertEquals("Nami,5672.33,1661.0,22689.33,6644.0", payStub.toCSV());
    }
    @Test
    public void testNegativeHoursReturnNullAndDoNotUpdateYTD() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);

        IPayStub payStub = employee.runPayroll(-5.0);

        assertNull(payStub);
        assertEquals(20000.0, employee.getYTDEarnings(), 0.001);
        assertEquals(4530.0, employee.getYTDTaxesPaid(), 0.001);
    }
    @Test
    public void testBuilderCreatesHourlyEmployeeFromCSV() {
        IEmployee employee = Builder.buildEmployeeFromCSV("HOURLY,Luffy,s192,30.00,0,20000,4530");

        assertEquals("HOURLY", employee.getEmployeeType());
        assertEquals("Luffy", employee.getName());
        assertEquals("s192", employee.getID());
        assertEquals(30.0, employee.getPayRate(), 0.001);
        assertEquals(0.0, employee.getPretaxDeductions(), 0.001);
        assertEquals(20000.0, employee.getYTDEarnings(), 0.001);
        assertEquals(4530.0, employee.getYTDTaxesPaid(), 0.001);
    }
    @Test
    public void testBuilderCreatesSalaryEmployeeFromCSV() {
        IEmployee employee = Builder.buildEmployeeFromCSV("SALARY,Nami,s193,200000,1000,17017,4983");

        assertEquals("SALARY", employee.getEmployeeType());
        assertEquals("Nami", employee.getName());
        assertEquals("s193", employee.getID());
        assertEquals(200000.0, employee.getPayRate(), 0.001);
        assertEquals(1000.0, employee.getPretaxDeductions(), 0.001);
        assertEquals(17017.0, employee.getYTDEarnings(), 0.001);
        assertEquals(4983.0, employee.getYTDTaxesPaid(), 0.001);
    }
    @Test
    public void testBuilderCreatesTimeCardFromCSV() {
        ITimeCard timeCard = Builder.buildTimeCardFromCSV("s192,45");

        assertEquals("s192", timeCard.getEmployeeID());
        assertEquals(45.0, timeCard.getHoursWorked(), 0.001);
    }
    @Test
    public void testBuilderRejectsBadCSV() {
        assertThrows(IllegalArgumentException.class,
                () -> Builder.buildEmployeeFromCSV("HOURLY,Luffy,s192"));

        assertThrows(IllegalArgumentException.class,
                () -> Builder.buildEmployeeFromCSV("CONTRACTOR,Zoro,s999,50,0,0,0"));

        assertThrows(IllegalArgumentException.class,
                () -> Builder.buildEmployeeFromCSV("HOURLY,Luffy,s192,notANumber,0,20000,4530"));

        assertThrows(IllegalArgumentException.class,
                () -> Builder.buildTimeCardFromCSV("s192,notANumber"));
    }
    @Test
    public void testFinalEmployeeFileIsUpdated() throws IOException {
        Path employees = tempDir.resolve("employees_update_test.csv");
        Files.copy(Paths.get("resources/employees.csv"), employees);

        Path payStubs = tempDir.resolve("paystubs_update_test.csv");

        String[] args = {
                "-e", employees.toString(),
                "-t", "resources/time_cards.csv",
                "-o", payStubs.toString()
        };

        PayrollGenerator.main(args);

        String expectedEmployees = Files
                .readString(Paths.get("resources/original/employees_original_solution.csv"));

        String actualEmployees = Files.readString(employees);

        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    public void testHourlyEmployeePayrollWithoutOvertime() {
        IEmployee employee = new HourlyEmployee("Luffy", "s192", 30.0, 20000.0, 4530.0, 0.0);

        IPayStub payStub = employee.runPayroll(40.0);

        assertEquals(928.2, payStub.getPay(), 0.001);
        assertEquals(271.8, payStub.getTaxesPaid(), 0.001);
        assertEquals(20928.2, employee.getYTDEarnings(), 0.001);
        assertEquals(4801.8, employee.getYTDTaxesPaid(), 0.001);
    }

}
