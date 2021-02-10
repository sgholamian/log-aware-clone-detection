//,temp,DancingLinks.java,243,258,temp,DancingLinks.java,221,237
//,3
public class xxx {
  private void coverColumn(ColumnHeader<ColumnName> col) {
    LOG.debug("cover " + col.head.name);
    // remove the column
    col.right.left = col.left;
    col.left.right = col.right;
    Node<ColumnName> row = col.down;
    while (row != col) {
      Node<ColumnName> node = row.right;
      while (node != row) {
        node.down.up = node.up;
        node.up.down = node.down;
        node.head.size -= 1;
        node = node.right;
      }
      row = row.down;
    }
  }

};