public class InputNode extends Node {

  public InputNode(final String ids) {
    super(ids);

    subsequentEdges = new Edge[App.nodesPerLayer];
  }

  @Override
  public double getOutput() { return input; }


}
