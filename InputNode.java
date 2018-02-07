public class InputNode extends Node {

  public InputNode(final String ids, final double input) {
    super(ids);

    if (App.nodesHidden == 0) {
      subsequentEdges = new Edge[App.nodesOutput];
    } else {
      subsequentEdges = new Edge[App.nodesHidden];
    }

    this.input = input;
  }

  @Override
  public double getOutput() { return input; }


}
