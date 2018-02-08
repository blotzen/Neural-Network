public class OutputNode extends Node {

  /**
   * @param ids String id of the node
   * @param hiddenNodes amount of hidden nodes in one hidden layer
   */
  public OutputNode(final String ids, final int hiddenNodes) {
    super(ids);

    previousEdges = new Edge[hiddenNodes];
  }

  @Override
  public double getOutput() {
    return MathExtends.sigmoid(input);
  }
}
