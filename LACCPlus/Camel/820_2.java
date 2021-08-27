//,temp,AsyncDockerProducer.java,201,214,temp,AsyncDockerProducer.java,166,180
//,3
public class xxx {
    private void runAsyncWaitContainer(Exchange exchange, Message message, DockerClient client) throws InterruptedException {
        try (WaitContainerCmd cmd = executeWaitContainerRequest(client, message)) {
            WaitContainerResultCallback item = cmd.exec(new WaitContainerResultCallback() {
                @Override
                public void onNext(WaitResponse item) {
                    super.onNext(item);

                    LOG.trace("wait container callback {}", item);
                }

            });

            setResponse(exchange, item);
        }
    }

};