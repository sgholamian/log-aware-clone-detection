//,temp,VertXThreadPoolFactory.java,170,178,temp,VertXThreadPoolFactory.java,136,146
//,3
public class xxx {
        @Override
        public Future<?> submit(Runnable task) {
            LOG.trace("submit: {}", task);
            final CompletableFuture<?> answer = new CompletableFuture<>();
            // used by vertx
            vertx.executeBlocking(future -> {
                task.run();
                future.complete();
            }, res -> answer.complete(null));
            return answer;
        }

};