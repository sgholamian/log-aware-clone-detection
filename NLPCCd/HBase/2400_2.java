//,temp,sample_2988.java,2,18,temp,sample_1893.java,2,12
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