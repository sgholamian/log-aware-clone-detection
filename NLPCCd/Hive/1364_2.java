//,temp,sample_4347.java,2,11,temp,sample_4348.java,2,14
//,3
public class xxx {
private void checkEndPoint(HiveEndPoint endPoint, IMetaStoreClient msClient) throws InvalidTable, ConnectionError {
Table t;
try {
t = msClient.getTable(endPoint.database, endPoint.table);
} catch (Exception e) {
throw new InvalidTable(endPoint.database, endPoint.table, e);
}
if (!AcidUtils.isAcidTable(t)) {


log.info("hiveendpoint must use an acid table");
}
}

};