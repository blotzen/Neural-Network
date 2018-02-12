public class App {

  /** Important values to set! Deep feed forward neural network in Java
   */
  final static double learningRate = 0.2;

  final static String inputPathTrain = "C:\\Users\\Franz\\Desktop\\trainShort.csv";
  final static int inputLineCountTrain = 100;

  final static String targetPathTrain = "C:\\Users\\Franz\\Desktop\\trainTargetShort.csv";
  final static int targetsLineCountTrain = 100;

  final static String inputPathTest = "C:\\Users\\Franz\\Desktop\\testShort.csv";
  final static int inputLineCountTest = 10;

  final static String targetPathTest = "C:\\Users\\Franz\\Desktop\\testTargetShort.csv";
  final static int targetLineCountTest = 10;

  final static int epochs = 25;
  /**
  */

  public static void main(final String[] args) {

    final int netDepth = 3; // has to be >= 3
    final int inputNodes = 784;
    final int hiddenNodesPerLayer = 150;
    final int outputNodes = 10;

    try {
      final Nodes nodesInstance = Nodes.createInstance(netDepth, inputNodes, hiddenNodesPerLayer, outputNodes);

      final long startTimeSec = System.currentTimeMillis() / 1000;

      System.out.println("Starting training:");

      for (int i = 1; i <= epochs; i++) {
        System.out.println("\nEpoch: " + i);
        if (inputLineCountTrain == targetsLineCountTrain) {

          Input inputTrain = new Input(inputPathTrain, inputLineCountTrain, Input.InputType.InputTrain);
          Input inputTarget = new Input(targetPathTrain, targetsLineCountTrain, Input.InputType.TargetTrain);

          for (int j = 0; j < inputLineCountTrain; j++) {
            Visuals.progress(j, "Training the set:");
            nodesInstance.updateInputs(inputTrain.getLine());
            nodesInstance.updateTargets(inputTarget.getLine());
            train(nodesInstance.nodes);
          }
        } else {
          throw new Exception("Input and target csv length mismatch!");
        }
      }

      final long endTimeSec = System.currentTimeMillis() / 1000;

      System.out.printf("%nTraining took %d min.%nStarting testing:%n", (endTimeSec - startTimeSec) / 60);

      final Input inputTest = new Input(inputPathTest, inputLineCountTest, Input.InputType.InputTest);

      // testing
      if (inputLineCountTest == targetLineCountTest) {
        for (int i = 0; i < inputLineCountTest; i++) {
          nodesInstance.updateInputs(inputTest.getLine());
          think(nodesInstance.nodes);

          Visuals.printResult(nodesInstance.nodes[netDepth - 1]);
        }
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  private static void train(Node[][] nodes) {
    think(nodes);
    backpropagate(nodes);
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
  }

  private static void backpropagate(Node[][] nodes) {
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