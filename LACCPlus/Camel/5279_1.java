//,temp,VertXThreadPoolFactory.java,170,178,temp,VertXThreadPoolFactory.java,136,146
//,3
public class xxx {
        @Override
        public void execute(Runnable command) {
            LOG.trace("execute: {}", command);
            // used by vertx
            vertx.executeBlocking(future -> {
                command.run();
                future.complete();
            }, null);
        }

};