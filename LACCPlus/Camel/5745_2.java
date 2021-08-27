//,temp,AsyncDockerProducer.java,216,231,temp,AsyncDockerProducer.java,147,158
//,3
public class xxx {
    private void runAsyncWithFrameResponse(Exchange exchange, AsyncDockerCmd<?, Frame> cmd) throws InterruptedException {
        Adapter<Frame> item = cmd.exec(new Adapter<Frame>() {
            @Override
            public void onNext(Frame item) {
                LOG.trace("running framed callback {}", item);
                super.onNext(item);
            }

        });

        setResponse(exchange, item);
    }

};