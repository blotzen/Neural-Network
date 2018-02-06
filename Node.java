public abstract class Node {

  protected final String ids;
  protected double input;
  protected double error;
  double targetValue;

  public Node(final String ids) {
    this.ids = ids;
  }

  protected Edge[] subsequentEdges;
  protected Edge[] previousEdges;

  public void printPrevEdges() {
    for (Edge edge:previousEdges) {
      Visuals.printConnection(edge.prevNode.ids, edge.subNode.ids, edge.weight);
    }
  }


  public void printSubEdges() {
    for (Edge edge:subsequentEdges) {
      Visuals.printConnection(edge.prevNode.ids, edge.subNode.ids, edge.weight);
    }
  }


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


  public void backpropagate() {

    if (this instanceof InputNode) {
      return;
    }

    for (Edge edge:previousEdges) {
      double sumWeightedOutput = 0.0;

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

  public abstract double getOutput();
}
