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

}
