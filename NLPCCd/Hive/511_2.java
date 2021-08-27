//,temp,sample_4586.java,2,11,temp,sample_4627.java,2,10
//,3
public class xxx {
public String get_metastore_db_uuid() throws TException {
try {
return getMS().getMetastoreDbUuid();
} catch (MetaException e) {


log.info("exception thrown while querying metastore db uuid");
}
}

};