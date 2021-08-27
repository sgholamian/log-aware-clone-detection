//,temp,FakeStorageRpc.java,241,245,temp,FakeStorageRpc.java,113,118
//,3
public class xxx {
    @Override
    public Bucket create(Bucket bucket, Map<Option, ?> options) {
        LOG.info("create_bucket: " + bucket.getName());
        buckets.put(bucket.getName(), bucket);
        return bucket;
    }

};