//,temp,AsyncDockerProducer.java,216,231,temp,AsyncDockerProducer.java,147,158
//,3
public class xxx {
    private void runAsyncPull(Message message, DockerClient client, Exchange exchange) throws InterruptedException {
        try (PullImageCmd cmd = executePullImageRequest(client, message)) {

            PullImageResultCallback item = cmd.exec(new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    super.onNext(item);

                    LOG.trace("pull image callback {}", item);

                }
            });

            setResponse(exchange, item);
        }
    }

};