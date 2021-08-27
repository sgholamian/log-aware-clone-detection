//,temp,sample_302.java,2,14,temp,sample_450.java,2,14
//,3
public class xxx {
private MFunction convertToMFunction(Function func) throws InvalidObjectException {
if (func == null) {
return null;
}
MDatabase mdb = null;
try {
mdb = getMDatabase(func.getDbName());
} catch (NoSuchObjectException e) {


log.info("database does not exist");
}
}

};