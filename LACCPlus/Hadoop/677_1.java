//,temp,DancingLinks.java,243,258,temp,DancingLinks.java,221,237
//,3
public class xxx {
  private void uncoverColumn(ColumnHeader<ColumnName> col) {
    LOG.debug("uncover " + col.head.name);
    Node<ColumnName> row = col.up;
    while (row != col) {
      Node<ColumnName> node = row.left;
      while (node != row) {
        node.head.size += 1;
        node.down.up = node;
        node.up.down = node;
        node = node.left;
      }
      row = row.up;
    }
    col.right.left = col;
    col.left.right = col;
  }

};