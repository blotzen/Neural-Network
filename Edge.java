public class Edge {

  final Node prevNode;
  final Node subNode;
  double weight;

  Edge(final Node prevNode, final Node subNode, final double weight) {
    this.prevNode = prevNode;
    this.subNode = subNode;
    this.weight = weight;
  }

  /**
   * weights the output of node1 and sets it as input for node2
   */
  public void weigh() {
    subNode.input += prevNode.getOutput() * weight;
  }

  public String print(){
    return String.format("N%s--N%s", prevNode.ids, subNode.ids);
  }

  /**
   * @param nodesInLayer nodes in the layer
   * @return new weight
   */
  static double generateWeight(final int nodesInLayer) {
    int sign = 1;

    // generates a random sign for better weight distribution
    if (Math.random() >= 0.5D) {
      sign = -1;
    }

    return Math.random() * (1 / Math.sqrt(nodesInLayer) * sign);
  }
}
