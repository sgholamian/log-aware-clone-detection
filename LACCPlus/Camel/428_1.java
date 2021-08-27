//,temp,FileIdempotentRepositoryReadLockStrategy.java,60,64,temp,FileIdempotentChangedRepositoryReadLockStrategy.java,69,75
//,3
public class xxx {
    @Override
    public void prepareOnStartup(GenericFileOperations<File> operations, GenericFileEndpoint<File> endpoint) throws Exception {
        this.endpoint = endpoint;
        LOG.info("Using FileIdempotentRepositoryReadLockStrategy: {} on endpoint: {}", idempotentRepository, endpoint);
    }

};