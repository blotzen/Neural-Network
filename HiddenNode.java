public class HiddenNode extends Node {

  /**
   * @param ids String id of the node
   * @param nodesPrev amount of nodes in the previous layer
   * @param nodesSub amount of nodes in the subsequent layer
   */
  public HiddenNode(final String ids, int nodesPrev, int nodesSub) {
    super(ids);

    previousEdges = new Edge[nodesPrev];
    subsequentEdges = new Edge[nodesSub];
  }

  @Override
  public double getOutput() {
    return MathExtends.sigmoid(input);
  }
}
