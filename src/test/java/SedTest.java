/*
Created by : muditsaxena
*/

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import sed.CommandBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SedTest {

  private static final String fileName =
      "src/test/resources/initial.txt";
  private static final String test01_fileName =
      "src/test/resources/test01_expected_output.txt";
  private static final String test02_fileName =
      "src/test/resources/test02_expected_output.txt";
  private static final String test03_fileName =
      "src/test/resources/test03_expected_output.txt";
  private static final String test04_fileName =
      "src/test/resources/test04_expected_output.txt";
  private static final String test05_fileName =
      "src/test/resources/test05_input.txt";

  private static final String test06_fileName =
            "src/test/resources/test06_expected_output.txt";

  @BeforeAll
  public static void setUp() throws IOException {

    // System.out.println("Starting test suite");
    BufferedReader in = new BufferedReader(new FileReader(fileName));

    String line = in.readLine();
    do {
      // System.out.print(line);
      line = in.readLine();
    } while (line != null);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void test01_regexSubstitution() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().command("s/\"//g").fileName(fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test01_fileName);
    assertEquals(expectedOutput, output);
  }

  @Test
  void test02_fileRange() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().options("-n").command("2,4p").fileName(fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test02_fileName);
    assertEquals(expectedOutput, output);
  }

  @Test
  void test03_linesWithPattern() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().options("-n").command("/roads/p").fileName(fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test03_fileName);
    assertEquals(expectedOutput, output);
  }

  @Test
  void test04_addDoubleSpaces() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().options("G").fileName(test01_fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test04_fileName, "\n");
    assertEquals(expectedOutput, output);
  }

  @Test
  void test05_removeTrailingSpaces() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().command("/^$/d").fileName(test05_fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test01_fileName);
    assertEquals(expectedOutput, output);
  }

  @Test
  void test06_editInPlace() throws IOException {
    CommandBuilder sedCommand =
        CommandBuilder.builder().options("-i").command("s/Life/Code/g").fileName(test01_fileName).build();
    String output = sedCommand.execute();
    String expectedOutput = readFile(test06_fileName);
    assertEquals(expectedOutput, output);
  }

  private String readFile(String fileName) throws IOException {
    return readFile(fileName, "");
  }
  private String readFile(String fileName, String delimiter) throws IOException {
    List<String> output;
    try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
      output = new ArrayList<>();
      String line = in.readLine();
      int count = 0;
      do {
        //System.out.println("Line # " + ++count + " " + line);
        output.add(line);
        line = in.readLine();
      } while (line != null);
    }

    return String.join(delimiter, output);
  }
}
