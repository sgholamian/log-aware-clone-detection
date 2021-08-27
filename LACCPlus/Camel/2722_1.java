//,temp,DebugJUnit5Test.java,39,47,temp,DebugNoLazyTypeConverterTest.java,39,47
//,2
public class xxx {
    @Override
    protected void debugBefore(
            Exchange exchange, Processor processor, ProcessorDefinition<?> definition, String id, String shortName) {
        // this method is invoked before we are about to enter the given
        // processor
        // from your Java editor you can just add a breakpoint in the code line
        // below
        LOG.info("Before " + definition + " with body " + exchange.getIn().getBody());
    }

};