//,temp,KerberosAuthenticator.java,225,239,temp,DynamoDBMetadataStore.java,248,259
//,3
public class xxx {
  private static DynamoDB createDynamoDB(Configuration conf, String s3Region)
      throws IOException {
    Preconditions.checkNotNull(conf);
    final Class<? extends DynamoDBClientFactory> cls = conf.getClass(
        S3GUARD_DDB_CLIENT_FACTORY_IMPL,
        S3GUARD_DDB_CLIENT_FACTORY_IMPL_DEFAULT,
        DynamoDBClientFactory.class);
    LOG.debug("Creating DynamoDB client {} with S3 region {}", cls, s3Region);
    final AmazonDynamoDB dynamoDBClient = ReflectionUtils.newInstance(cls, conf)
        .createDynamoDBClient(s3Region);
    return new DynamoDB(dynamoDBClient);
  }

};