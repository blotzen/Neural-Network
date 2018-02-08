import java.io.BufferedReader;
import java.io.FileReader;

public class Input {

  private final String path;
  private final int totalLineCount;
  private final int divisor;
  private int currentReadLine = 0;
  private String[][] inputsStrings;
  private double[][] inputsDoubles;

  public Input(final String path, final int totalLineCount, final int divisor) throws Exception {
    this.path = path;
    this.totalLineCount = totalLineCount;
    this.divisor = divisor;

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
    BufferedReader br = null;
    String[][] inputLines = new String[totalLineCount][];
    String line = "";
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
        doubles[i][j] = Double.parseDouble(stringArray[i][j]) / divisor;
      }
    }
    return doubles;
  }

}