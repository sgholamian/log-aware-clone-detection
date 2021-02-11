//,temp,sample_2496.java,2,14,temp,sample_5401.java,2,12
//,3
public class xxx {
protected void closeTable() {
try {
if (table != null) {
table.close();
}
} catch (IOException e) {


log.info("error closing table");
}
}

};