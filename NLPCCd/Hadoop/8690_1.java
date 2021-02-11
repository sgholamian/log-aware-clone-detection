//,temp,sample_6736.java,2,8,temp,sample_4105.java,2,7
//,3
public class xxx {
private static DynamoDB createDynamoDB(Configuration conf, String s3Region) throws IOException {
Preconditions.checkNotNull(conf);
final Class<? extends DynamoDBClientFactory> cls = conf.getClass( S3GUARD_DDB_CLIENT_FACTORY_IMPL, S3GUARD_DDB_CLIENT_FACTORY_IMPL_DEFAULT, DynamoDBClientFactory.class);


log.info("creating dynamodb client with region");
}

};