//,temp,sample_3023.java,2,12,temp,sample_3278.java,2,14
//,3
public class xxx {
protected void closeHTable() {
try {
if (table != null) {
table.close();
}
} catch (IOException e) {


log.info("error closing table");
}
}

};