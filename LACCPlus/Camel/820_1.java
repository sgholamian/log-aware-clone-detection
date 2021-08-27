//,temp,AsyncDockerProducer.java,201,214,temp,AsyncDockerProducer.java,166,180
//,3
public class xxx {
    private void runAsyncPush(Exchange exchange, Message message, DockerClient client) throws InterruptedException {
        try (PushImageCmd cmd = executePushImageRequest(client, message)) {
            Adapter<PushResponseItem> item = cmd.exec(new Adapter<PushResponseItem>() {
                @Override
                public void onNext(PushResponseItem item) {
                    super.onNext(item);

                    LOG.trace("push image callback {}", item);
                }
            });

            setResponse(exchange, item);
        }
    }

};