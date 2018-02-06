public class HiddenNode extends Node {

  public HiddenNode(final String ids) {
    super(ids);

    previousEdges = new Edge[App.nodesPerLayer];
    subsequentEdges = new Edge[App.nodesPerLayer];
  }

  @Override
  public double getOutput() {
    return MathExtends.sigmoid(input);
  }
}
