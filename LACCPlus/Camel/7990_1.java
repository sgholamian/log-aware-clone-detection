//,temp,IgniteCacheContinuousQueryConsumer.java,126,134,temp,VertxComponent.java,203,211
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        cursor.close();

        LOG.info("Stopped Ignite Cache Continuous Query consumer for cache {} with query: {}.", cache.getName(),
                endpoint.getQuery());
    }

};