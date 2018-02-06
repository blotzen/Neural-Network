public class App {

  static int netDepth = 0;
  static int nodesPerLayer = 0;
  static double learningRate = 0.1;

  static Node[][] nodes;

  static String[] inputs;
  static String[] targets;


  public static void main(final String[] args) {

    netDepth = Integer.parseInt(args[0]);
    nodesPerLayer = Integer.parseInt(args[1]);

    // array which holds all inputs
    //inputs = java.util.Arrays.copyOfRange(args, 2, args.length - 1);
    inputs = new String[3];
    inputs[0] = "0.8";
    inputs[1] = "0.2";
    inputs[2] = "0.7";

    // array which holds all target values
    // still missing good implementation
    targets = new String[3];
    targets[0] = "0.1";
    targets[1] = "0.8";
    targets[2] = "0.6";


    if (inputs.length == nodesPerLayer) {
      nodes = generateNodes(inputs, targets);
      generateEdges();

      for (int i = 0; i < 1000; i++) {
        think();
      }

      printResult();
    } else {
      System.out.println("Inputs are not matching the nodes per layer count!\nNo operation executed!");
    }
  }


  private static Node[][] generateNodes(String[] inputs, String[] targets) {

    Node[][] nodes = new Node[netDepth][nodesPerLayer];

    for (int i = 0; i < netDepth; i++) {
      for (int j = 0; j < nodesPerLayer; j++) {

        if (i == 0) {
          Node inputNode = new InputNode(String.format("%d%d", i, j));
          // sets the input from the array to the node
          inputNode.input = Double.parseDouble(inputs[j]);
          nodes[i][j] = inputNode;
        } else if (i > 0 && i < netDepth - 1) {
          nodes[i][j] = new HiddenNode(String.format("%d%d", i, j));
        } else {                                                    // assigns target values to output nodes
          nodes[i][j] = new OutputNode(String.format("%d%d", i, j), Double.parseDouble(targets[j]));
        }

        //System.out.print(nodes[i][j].ids + " ");
      }

      //System.out.println();
    }

    return nodes;
  }


  private static void generateEdges() {

    for (int i = 0; i < netDepth; i++) {
      for (int j = 0; j < nodesPerLayer; j++) {
        for (int k = 0; k < nodesPerLayer; k++) {

          if (i == 0) {
            Node inputNode = nodes[i][j];

            Node subsequentNode = nodes[i + 1][k];
            Edge edge = new Edge(inputNode, subsequentNode, Edge.generateWeight());
            inputNode.subsequentEdges[k] = edge;

            subsequentNode.previousEdges[j] = edge;

          } else if (i > 0 && i < netDepth - 1) {
            Node hiddenNode = nodes[i][j];

            Node subsequentNode = nodes[i + 1][k];
            Edge edge = new Edge(hiddenNode, subsequentNode, Edge.generateWeight());
            hiddenNode.subsequentEdges[k] = edge;

            subsequentNode.previousEdges[j] = edge;
          }
        }

      }
    }
  }


  // processes the inputs
  private static void think() {

    // clears old inputs in hidden and output layer
    for (int i = 1; i < netDepth; i++) {
      for (int j = 0; j < nodesPerLayer; j++) {
        nodes[i][j].input = 0.0D;
      }
    }

    for (int i = 0; i < netDepth; i++) {
      for (int j = 0; j < nodesPerLayer; j++) {

        Node currentNode = nodes[i][j];

        if (currentNode instanceof InputNode || currentNode instanceof HiddenNode) {
          for (Edge subEdge:currentNode.subsequentEdges) {
            subEdge.weigh();
          }
          //currentNode.printSubEdges();
        }

      }
    }

    //System.out.println("---------------------------------------------");

    for (int i = netDepth - 1; i > 0; i--) {
      for (int j = 0; j < nodesPerLayer; j++) {

        Node currentNode = nodes[i][j];

        currentNode.backpropagate();

        // draws nothing with size below 2 because of loops set up
        if (!(currentNode instanceof OutputNode)) {
          //currentNode.printSubEdges();
        }

      }
    }

  }

  private static void printResult() {
    for (int i = 0; i < nodesPerLayer; i++) {
      System.out.println("Output: " + nodes[netDepth -  1][i].getOutput());
    }
  }

  private static void printInfos() {
    for (int i = 1; i < netDepth; i++) {
      for (int j = 0; j < nodesPerLayer; j++) {
        for (int k = 0; k < nodesPerLayer; k++) {
          System.out.println(nodes[i][j].previousEdges[k].print());
        }
      }
    }
  }
}