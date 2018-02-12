public class Visuals {


  public static void printNodeConnections(String node1Name, String node2Name, double weight) {
    StringBuilder strbldr = new StringBuilder();
    strbldr.append("|----|               |----|\n");

    if (weight < 0) {
      strbldr.append(String.format("| %s |<->%.5f <->| %s |\n", node1Name, weight, node2Name));
    } else {
      strbldr.append(String.format("| %s |<-> %.5f <->| %s |\n", node1Name, weight, node2Name));
    }

    strbldr.append("|----|               |----|\n");
    System.out.println(strbldr);
  }


  public static void printResult(Node[] nodes) {

    double[] outputs = new double[nodes.length];

    for (int i = 0; i < nodes.length; i++) {
      outputs[i] = nodes[i].getOutput();
    }

    System.out.println("Result: " + (ArrayExtends.indexOfBiggestValue(outputs)));
  }

  public static void progress(int current, String msg) {
    try {
      String anim= "|/-\\";
      String data = "\r" + msg + " " + anim.charAt(current % anim.length()) + " " + current;
      System.out.print(data);
    } catch (Exception ex) { }
  }

}
