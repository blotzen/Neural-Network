public class ArrayExtends {

  // returns index of biggest value, if biggest value is a duplicate, first occurrence is returned
  public static int getIndexOfBiggestValue(final double[] array) {

    double biggestValue = 0.0D;
    int biggestValueIndex = 0;

    for (int i = 0; i < array.length; i++) {
      if (array[i] > biggestValue) {
        biggestValue = array[i];
        biggestValueIndex = i;
      }
    }

    return biggestValueIndex;
  }
}
