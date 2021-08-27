//,temp,IgniteCacheContinuousQueryConsumer.java,126,134,temp,VertxComponent.java,203,211
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        if (createdVertx && vertx != null) {
            LOG.info("Stopping Vertx {}", vertx);
            vertx.close();
        }
    }

};