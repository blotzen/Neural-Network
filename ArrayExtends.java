public class ArrayExtends {

  // multiplies value to each value of array then returns the sum
  public static double sumMultArray(final double[] array1, final double x) {
    double sum = 0.0D;

    for (int i = 0; i < array1.length; i++) {
      sum += array1[i] * x;
    }

    return sum;
  }
}
