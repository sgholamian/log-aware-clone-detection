//,temp,FakeStorageRpc.java,241,245,temp,FakeStorageRpc.java,113,118
//,3
public class xxx {
    @Override
    public Bucket get(Bucket bucket, Map<Option, ?> options) {
        LOG.info("get_Bucket: {}", bucket.getName());
        return buckets.get(bucket.getName());
    }

};