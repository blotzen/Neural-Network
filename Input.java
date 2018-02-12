import java.io.BufferedReader;
import java.io.FileReader;

public class Input {

  private final int totalLineCount;
  private final InputType type;
  private int currentReadLine = 0;
  private String[][] inputsStrings;
  private double[][] inputsDoubles;

  public Input(final String path, final int totalLineCount, final InputType type) throws Exception {
    this.totalLineCount = totalLineCount;
    this.type = type;

    inputsStrings = readCSV(path, 0, totalLineCount);
    inputsDoubles = stringToDouble(inputsStrings);
  }

  public double[] getLine() {
    double[] output = inputsDoubles[currentReadLine];
    currentReadLine++;

    return output;
  }

  // startLineIndex for possible later extension of the function
  private String[][] readCSV(final String path, final int startLineIndex, final int totalLineCount) throws Exception {
    BufferedReader br;
    String[][] inputLines = new String[totalLineCount][];
    String line;
    String csvSplitBy = ",";

    br = new BufferedReader(new FileReader(path));

    int i = 0;
    while ((line = br.readLine()) != null) {
      if (i >= startLineIndex){
        inputLines[i] = line.split(csvSplitBy);
      }
      i++;
    }

    return inputLines;
  }


  private double[][] stringToDouble(final String[][] stringArray) {
    double[][] doubles = new double[stringArray.length][stringArray[0].length];
    for (int i = 0; i < stringArray.length; i++) {
      for (int j = 0; j < stringArray[0].length; j++) {
        if (type == InputType.InputTrain || type == InputType.InputTest) {
          doubles[i][j] = Double.parseDouble(stringArray[i][j]) / 255.0 * 0.99 + 0.01;
        } else {
          double currentNum = Double.parseDouble(stringArray[i][j]);
          if (currentNum == 0.0D) {
            doubles[i][j] = 0.01D;
          } else {
            doubles[i][j] = currentNum - 0.01D;
          }
        }
      }
    }
    return doubles;
  }

  public enum InputType {
    InputTrain, TargetTrain, InputTest
  }

}