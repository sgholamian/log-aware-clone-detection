//,temp,sample_302.java,2,14,temp,sample_450.java,2,14
//,3
public class xxx {
private MTable convertToMTable(Table tbl) throws InvalidObjectException, MetaException {
if (tbl == null) {
return null;
}
MDatabase mdb = null;
try {
mdb = getMDatabase(tbl.getDbName());
} catch (NoSuchObjectException e) {


log.info("could not convert to mtable");
}
}

};