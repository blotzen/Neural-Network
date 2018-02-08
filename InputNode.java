public class InputNode extends Node {

  /**
   * @param ids String id of the node
   * @param hiddenNodes amount of hidden nodes in one hidden layer
   */
  public InputNode(final String ids, final int hiddenNodes) {
    super(ids);

    subsequentEdges = new Edge[hiddenNodes];
  }

  @Override
  public double getOutput() { return input; }
}
