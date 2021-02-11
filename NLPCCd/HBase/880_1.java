//,temp,sample_3023.java,2,12,temp,sample_641.java,2,12
//,2
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