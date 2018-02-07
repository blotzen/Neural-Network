public class HiddenNode extends Node {

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
