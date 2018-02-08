public class App {

  /** Important values to set! Deep feed forward neural network in Java
   */
  final static double learningRate = 0.1;

  final static String inputPath = "C:\\Users\\Franz\\Desktop\\trainShort.csv";
  final static int inputLineCount = 100;

  final static String targetPath = "C:\\Users\\Franz\\Desktop\\targetShortAuto.csv";
  final static int targetsLineCount = 100;

  final static int epochs = 1;
  /**
  */

  public static void main(final String[] args) {

    final int netDepth = 3; // has to be >= 3
    final int inputNodes = 784;
    final int hiddenNodesPerLayer = 100;
    final int outputNodes = 10;

    try {
      final Nodes nodesInstance = Nodes.createInstance(netDepth, inputNodes, hiddenNodesPerLayer, outputNodes);

      for (int i = 0; i < epochs; i++) {
        if (inputLineCount == targetsLineCount) {
          for (int j = 0; j < inputLineCount; j++) {

            System.out.println(j);

            nodesInstance.updateInputs(new Input(inputPath, inputLineCount, 256).getLine());
            nodesInstance.updateTargets(new Input(targetPath, targetsLineCount, 10).getLine());

            think(nodesInstance.nodes);
          }
        } else {
          throw new Exception("Input and target csv length mismatch!");
        }
      }

      // prints results (output of output Nodes)
      Visuals.printResult(nodesInstance.nodes[netDepth - 1]);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  private static void think(Node[][] nodes) {
    // clears all inputs before thinking again
    for (Node[] nodes1:nodes) {
      for (Node node : nodes1) {
        // only reset inputs in the hidden and output nodes
        if (node instanceof HiddenNode || node instanceof OutputNode) {
          node.input = 0.0D;
        }
      }
    }

    // weights the outputs of input- and hidden-nodes and sets them as new inputs
    for (Node[] nodes1:nodes) {
      for (Node node:nodes1) {
        if (node instanceof InputNode || node instanceof HiddenNode) {
          for (Edge subEdge:node.subsequentEdges) {
            subEdge.weigh();
          }
        }
      }
    }

    for (Node[] nodes1:nodes) {
      for (Node node:nodes1) {
        // only backpropagate hidden- and output-nodes
        if (node instanceof HiddenNode || node instanceof OutputNode) {
          node.backpropagate();
        }
      }
    }

  }
}