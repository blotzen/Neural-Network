public class ArrayExtends {

  public static int indexOfBiggestValue(final double[] values) {
    double biggestValue = 0.0D;
    int biggestValueIndex = 0;

    for (int i = 0; i < values.length; i++) {
      if (values[i] > biggestValue) {
        biggestValue = values[i];
        biggestValueIndex = i;
      }
    }

    return biggestValueIndex;
  }

}
