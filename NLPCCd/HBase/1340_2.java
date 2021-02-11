//,temp,sample_5577.java,2,18,temp,sample_230.java,2,12
//,3
public class xxx {
protected void closeHTable() {
if (table != null) {
try {
table.close();
} catch (Exception e) {


log.info("error in closing the table");
}
}
}

};