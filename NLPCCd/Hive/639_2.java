//,temp,sample_4944.java,2,10,temp,sample_4840.java,2,10
//,3
public class xxx {
public static void closeHiveClientQuietly(IMetaStoreClient client) {
try {
if (client != null) client.close();
} catch (Exception e) {


log.info("error closing metastore client ignored the error");
}
}

};