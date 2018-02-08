public class Nodes {

  private static Nodes instance = null;
  public Node[][] nodes;

  private final int inputNodes;
  private final int hiddenNodes;
  private final int outputNodes;
  private final int netDepth;

  private Nodes(final int netDepth, final int inputCount, final int hiddenCount, final int outputCount) {
    this.netDepth = netDepth;
    this.inputNodes = inputCount;
    this.hiddenNodes = hiddenCount;
    this.outputNodes = outputCount;

    nodes = new Node[netDepth][];

    generateInputNodes();
    generateHiddenNodes();
    generateOutputNodes();

    generateEdges();
  }

  /**
   * @param netDepth depth of net
   * @param inputCount amount of input nodes
   * @param hiddenCount amount of hidden nodes per hidden layer
   * @param outputCount amount of output nodes
   * @return instance of Nodes, only creates new if none was created before
   */
  public static Nodes createInstance(final int netDepth, final int inputCount, final int hiddenCount, final int outputCount) {
    if (instance == null) {
      instance = new Nodes(netDepth, inputCount, hiddenCount, outputCount);
    }
    return instance;
  }

  /**
   * @return instance of Nodes, returns null if there is none created
   */
  public Nodes getInstance() {
    return instance;
  }

  //region Getter
  public int getNetDepth() {
    return netDepth;
  }
  //endregion

  //region Generates all Nodes
  private void generateInputNodes() {
    nodes[0] = new Node[inputNodes];
    for (int i = 0; i < inputNodes; i++) {
      nodes[0][i] = new InputNode(String.format("0%d", i), hiddenNodes);
    }
  }

  private void generateHiddenNodes() {
    for (int i = 1; i < netDepth - 1; i++) {
      nodes[i] = new Node[hiddenNodes];

      int prevNodes = inputNodes;
      int subNodes = hiddenNodes;

      // changes size of previous and subsequent edge arrays
      if (i > 1) {
        prevNodes = hiddenNodes;
      }

      if (i == netDepth - 2) {
        subNodes = outputNodes;
      }

      for (int j = 0; j < hiddenNodes; j++) {
        nodes[i][j] = new HiddenNode(String.format("%d%d", i, j), prevNodes, subNodes);
      }
    }
  }

  private void generateOutputNodes() {
    nodes[netDepth - 1] = new Node[outputNodes];
    for (int i = 0; i < outputNodes; i++) {
      nodes[netDepth - 1][i] = new OutputNode(String.format("%d%d", netDepth - 1, i), hiddenNodes);
    }
  }
  //endregion

  // generates the Edges, only possible after generating all the nodes
  private void generateEdges() {
    for (int currentLayer = 0; currentLayer < netDepth - 1; currentLayer++) {
      int nodesCurrentLayer = inputNodes;
      int nodesSubLayer = hiddenNodes;

      // finds current and subsequent layer size for appropriate edge generation
      if (currentLayer >= 1 && currentLayer <= netDepth - 2) {
        nodesCurrentLayer = hiddenNodes;
        if (currentLayer == netDepth - 2) {
          nodesSubLayer = outputNodes;
        }
      }

      for (int j = 0; j < nodesCurrentLayer; j++) {
        for (int k = 0; k < nodesSubLayer; k++) {

          if (currentLayer == 0) {
            Node inputNode = nodes[currentLayer][j];

            Node subsequentNode = nodes[currentLayer + 1][k];
            Edge edge = new Edge(inputNode, subsequentNode, Edge.generateWeight(inputNodes));
            inputNode.subsequentEdges[k] = edge;

            subsequentNode.previousEdges[j] = edge;

          } else if (currentLayer > 0 && currentLayer < netDepth - 1) {
            Node hiddenNode = nodes[currentLayer][j];

            Node subsequentNode = nodes[currentLayer + 1][k];
            Edge edge = new Edge(hiddenNode, subsequentNode, Edge.generateWeight(hiddenNodes));
            hiddenNode.subsequentEdges[k] = edge;

            subsequentNode.previousEdges[j] = edge;
          }
        }
      }

    }
  }

  //region Input and target handling
  /**
   * @param inputs array of double values representing the new inputs
   * @throws Exception error if amount of input nodes and length of inputs array mismatch
   */
  public void updateInputs(final double[] inputs) throws Exception {
    if (inputs.length == inputNodes) {
      for (int i = 0; i < inputNodes; i++) {
        nodes[0][i].input = inputs[i];
      }
    } else {
      throw new Exception("Input length not matching amount of input nodes!");
    }
  }

  /**
   * @param targets array of double values representing the new targets
   * @throws Exception error if amount of output nodes and length of targets array mismatch
   */
  public void updateTargets(final double[] targets) throws Exception {
    if (targets.length == outputNodes) {
      for (int i = 0; i < outputNodes; i++) {
        nodes[netDepth - 1][i].targetValue = targets[i];
      }
    } else {
      throw new Exception("Target length not matching amount of output nodes");
    }
  }
  //endregion
}