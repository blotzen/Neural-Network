public abstract class Node {

  protected final String ids;
  protected double input;
  protected double error;
  protected double targetValue;

  protected Edge[] subsequentEdges;
  protected Edge[] previousEdges;

  public Node(final String ids) {
    this.ids = ids;
  }

  public abstract double getOutput();

  private double getError() {
    if (this instanceof OutputNode) {
      return targetValue - getOutput();
    }

    double error = 0.0D;

    for (Edge edge:subsequentEdges) {
      error += edge.subNode.error;
    }

    return error;
  }

  /**
   * sets new weights according to the error of the node
   */
  public void backpropagate() {

    for (Edge edge:previousEdges) {
      double sumWeightedOutput = 0.0D;

      for (int i = 0; i < previousEdges.length; i++) {
        sumWeightedOutput += previousEdges[i].weight * previousEdges[i].prevNode.getOutput();
      }

      double weightOffset = -1 * getError() * MathExtends.sigmoidFirstDerivative(sumWeightedOutput) * edge.prevNode.getOutput();
      edge.weight = edge.weight - App.learningRate * weightOffset;
    }
  }

  public String print() {
    return ids;
  }
}