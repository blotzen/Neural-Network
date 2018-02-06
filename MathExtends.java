public class MathExtends {

  public static double sigmoid(final double x) {
    return 1.0D / (1 + Math.pow(Math.E, -x));
  }

  public static double sigmoidFirstDerivative(final double x) {
    return sigmoid(x) * (1 - sigmoid(x));
  }

}
