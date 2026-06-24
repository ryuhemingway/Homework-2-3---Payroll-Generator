package student;

// note: java.nio was introduced in Java 17.
// if you have an older version of Java, you will either
// want to update, or convert this code to use Buffered reader
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Contains simple utilities for reading in a file. You are free to modify this file as you
 * need/want, but it is not required.
 */
public final class FileUtil {
    /** header line required when writing out to the employee file. */
    public static final String EMPLOYEE_HEADER =
            "employee_type,name,ID,payRate,pretaxDeductions,YTDEarnings,YTDTaxesPaid";

    /** header line required when writing out to the pay stub file. */
    public static final String PAY_STUB_HEADER =
            "employee_name,net_pay,taxes,ytd_earnings,ytd_taxes_paid";



    /**
     * Private constructor to prevent instantiation.
     */
    private FileUtil() {

    }

    /**
     * Reads in a text file and returns a list of strings, one for each line in the file.
     * 
     * @param file the file name
     * @return a list of strings, one for each line in the file
     */
    public static List<String> readFileToList(String file) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Path.of(file));
            lines.remove(0); // remove the header line
        } catch (IOException e) {
            System.err.println("Error reading employee file: " + e.getMessage());
        }
        return lines;
    }



    /**
     * Writes the lines to the file. Will backup teh file if it exists.
     * 
     * @param outFile the file name
     * @param lines the lines to write
     */
    public static void writeFile(String outFile, List<String> lines) {
        writeFile(outFile, lines, true);
    }

    /**
     * Writes the lines to the file.
     * 
     * @param outFile the file name
     * @param lines the lines to write
     * @param backup if true, will backup the file if it exists
     */
    public static void writeFile(String outFile, List<String> lines, boolean backup) {
        if (backup) {
            try {
                // check for existence
                if (Files.exists(Path.of(outFile))) {
                    // it is alright to overwrite the backup
                    Files.move(Path.of(outFile), Path.of(outFile + ".bak"),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                System.err.println("Error backing up file: " + e.getMessage());
                e.printStackTrace();
                return; // don't write the file if we can't back it up
            }
        }

        try {
            Files.write(Path.of(outFile), lines);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
