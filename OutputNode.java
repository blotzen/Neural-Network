public class OutputNode extends Node {

    public OutputNode(final String ids, final double targetValue) {
    super(ids);

    previousEdges = new Edge[App.nodesPerLayer];
    this.targetValue = targetValue;
  }

  @Override
  public double getOutput() {
    return MathExtends.sigmoid(input);
  }
}
