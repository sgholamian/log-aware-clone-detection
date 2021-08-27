//,temp,sample_6296.java,2,10,temp,sample_8138.java,2,11
//,3
public class xxx {
public void remove(Table table, byte[] row) {
Delete delete = new Delete(row);
try {
table.delete(delete);
} catch (IOException e) {


log.info("failed to delete row from table this exception will be ignored");
}
}

};