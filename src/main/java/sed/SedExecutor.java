package sed;

/*
Created by : muditsaxena
*/

import lombok.Builder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class SedExecutor {

  String options;
  String command;
  String fileName;

  SedExecutor(String options, String command, String fileName) {
    this.command = command;
    this.options = options;
    this.fileName = fileName;
  }

  private List<String> readFile(String fileName) throws IOException {
    List<String> output = new ArrayList<>();

    try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
      String line = in.readLine();
      do {
        output.add(line);
        line = in.readLine();
      } while (line != null);
    }

    return output;
  }

  public String execute() {
    String result = null;
    List<String> text = null;
    try {
      text = readFile(fileName);
      if (options == null) {
        if (command.startsWith("s")) {
          String[] commands = command.split("/");
          System.out.println(commands[1] + " " + commands[2]);
          return text.stream()
              .map(input -> input.replaceAll(commands[1], commands[2]))
              .collect(Collectors.joining());
        } else if (command.startsWith("/^$/d")) {
          return text.stream().filter(x -> !(x.isEmpty())).collect(Collectors.joining());
        }

      } else {
          switch (options) {
              case "-n" -> {
                  if (command.contains(",")) {
                      String[] commands = command.split(",");
                      int startIndex = Integer.parseInt(commands[0]);
                      int endIndex = Integer.parseInt(commands[1].trim().replace("p", ""));
                      StringBuilder output = new StringBuilder();
                      for (int index = startIndex; index <= endIndex; index++) {
                          output.append(text.get(index - 1));
                      }
                      return output.toString();
                  } else if (command.startsWith("/")) {
                      String[] commands = command.split("/");
                      System.out.println(commands[1].trim());
                      return text.stream()
                              .filter(input -> (input.contains(commands[1].trim())))
                              .collect(Collectors.joining());
                  }
              }
              case "G" -> {
                  return String.join("\n\n", text);
              }
              case "-i" -> {
                  String[] commands = command.split("/");
                  return text.stream()
                          .map(x -> x.replaceAll(commands[1], commands[2]))
                          .collect(Collectors.joining());
              }
              default -> {
                  System.out.println("Unsupported operation");
                  return null;
              }
          }
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}
