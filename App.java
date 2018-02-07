public class App {

  // deep feed forward?

  static int netDepth = 3;
  static int nodesHidden = 3;
  static int nodesInput = 5;
  static int nodesOutput = 2;

  static double learningRate = 0.5;

  // partly instantiated jagged array of Nodes
  static Node[][] nodes = new Node[netDepth][];

  static String[] inputs;
  static String[] targets;


  public static void main(final String[] args) {

    // array which holds all inputs
    //inputs = java.util.Arrays.copyOfRange(args, 2, args.length - 1);
    inputs = new String[5];
    inputs[0] = "0.8";
    inputs[1] = "0.2";
    inputs[2] = "0.4";
    inputs[3] = "0.1";
    inputs[4] = "0.3";


    // array which holds all target values
    // still missing good implementation
    targets = new String[2];
    targets[0] = "0.3";
    targets[1] = "0.8";


    if (inputs.length == nodesInput && targets.length == nodesOutput) {

      generateInputNodes(inputs);
      generateHiddenNodes();
      generateOutputNodes(targets);

      for (int i = 0; i < netDepth - 1; i++) {
        generateEdges(i);
      }

      for (int i = 0; i < 2000; i++) {
        think();
      }

      printResult();
    } else {
      System.out.println("Input count not matching nodes count in the first layer!\nNo operation executed!");
    }
  }


  //region Generates all Nodes
  private static void generateInputNodes(final String[] inputs) {

    nodes[0] = new Node[nodesInput];

    for (int i = 0; i < nodesInput; i++) {
      nodes[0][i] = new InputNode(String.format("0%d", i), Double.parseDouble(inputs[i]));
    }
  }

  private static void generateHiddenNodes() {

    for (int i = 1; i < netDepth - 1; i++) {

      nodes[i] = new Node[nodesHidden];

      int prevNodes = nodesInput;
      int subNodes = nodesHidden;

      // changes size of previous and subsequent edge arrays
      if (i > 1) {
        prevNodes = nodesHidden;
      }

      if (i == netDepth - 2) {
        subNodes = nodesOutput;
      }

      for (int j = 0; j < nodesHidden; j++) {
        nodes[i][j] = new HiddenNode(String.format("%d%d", i, j), prevNodes, subNodes);
      }
    }
  }

  private static void generateOutputNodes(final String[] targets) {

    nodes[netDepth - 1] = new Node[nodesOutput];

    for (int i = 0; i < nodesOutput; i++) {
      nodes[netDepth - 1][i] = new OutputNode(String.format("%d%d", netDepth - 1, i), Double.parseDouble(targets[i]));
    }
  }
  //endregion


  private static void generateEdges(int layerId) {

    int nodesLayer = nodesInput;
    int nodesNextLayer = nodesHidden;

    if (layerId > 0 && layerId < netDepth - 1) {
      nodesLayer = nodesHidden;

      if (layerId == netDepth - 2) {
        nodesNextLayer = nodesOutput;
      }
    }

    for (int j = 0; j < nodesLayer; j++) {
      for (int k = 0; k < nodesNextLayer; k++) {

        if (layerId == 0) {
          Node inputNode = nodes[layerId][j];

          Node subsequentNode = nodes[layerId + 1][k];
          Edge edge = new Edge(inputNode, subsequentNode, Edge.generateWeight());
          inputNode.subsequentEdges[k] = edge;

          subsequentNode.previousEdges[j] = edge;

        } else if (layerId > 0 && layerId < netDepth - 1) {
          Node hiddenNode = nodes[layerId][j];

          Node subsequentNode = nodes[layerId + 1][k];
          Edge edge = new Edge(hiddenNode, subsequentNode, Edge.generateWeight());
          hiddenNode.subsequentEdges[k] = edge;

          subsequentNode.previousEdges[j] = edge;
        }
      }

    }
  }


  // processes the inputs
  private static void think() {

    for (Node[] nodes1:nodes) {
      for (Node node:nodes1) {
        node.input = 0.0D;
      }
    }

    // clears old inputs in hidden and output layer
    for (int i = 0; i < netDepth; i++) {
      for (Node node:nodes[i]) {

        if (node instanceof InputNode || node instanceof HiddenNode) {
          for (Edge subEdge:node.subsequentEdges) {
            subEdge.weigh();
          }
          //node.printSubEdges();
        }
      }
    }

    //System.out.println("---------------------------------------------");

    for (int i = netDepth - 1; i > 0; i--) {
      for (Node node:nodes[i]) {

        node.backpropagate();
        // draws nothing with size below 2 because of loops set up
        if (!(node instanceof InputNode)) {
          //node.printPrevEdges();
        }

      }
    }

  }

  private static void printResult() {
    for (Node node:nodes[netDepth - 1]) {
      System.out.println("Output: " + node.getOutput());
    }
  }
}