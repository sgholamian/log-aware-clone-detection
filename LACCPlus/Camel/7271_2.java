//,temp,VertxPlatformHttpServer.java,242,278,temp,VertxPlatformHttpServer.java,202,240
//,3
public class xxx {
    protected void stopServer() {
        if (this.server == null) {
            return;
        }

        try {
            CompletableFuture.runAsync(
                    () -> {
                        CountDownLatch latch = new CountDownLatch(1);

                        // remove the platform-http component
                        context.removeComponent(PlatformHttpConstants.PLATFORM_HTTP_COMPONENT_NAME);

                        server.close(result -> {
                            try {
                                if (result.failed()) {
                                    LOGGER.warn("Failed to close Vert.x HttpServer reason: {}",
                                            result.cause().getMessage());

                                    throw new RuntimeException(result.cause());
                                }

                                LOGGER.info("Vert.x HttpServer stopped");
                            } finally {
                                latch.countDown();
                            }
                        });

                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    executor).toCompletableFuture().join();
        } finally {
            this.server = null;
        }
    }

};