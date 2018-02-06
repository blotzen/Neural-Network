public class Visuals {



  public static void printConnection(String node1Name, String node2Name, double weight) {
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



}
