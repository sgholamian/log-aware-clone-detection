//,temp,CdiEventProducer.java,39,48,temp,TagConsumerTest.java,54,59
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            RepositoryTag tag = (RepositoryTag) in.getBody();
            log.debug("Got TAG  [" + tag.getName() + "]");
        }

};