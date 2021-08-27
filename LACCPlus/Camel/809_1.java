//,temp,DebugJUnit4Test.java,35,42,temp,DebugTest.java,39,47
//,3
public class xxx {
    @Override
    protected void debugBefore(
            Exchange exchange, Processor processor,
            ProcessorDefinition<?> definition, String id, String shortName) {
        // this method is invoked before we are about to enter the given processor
        // from your Java editor you can just add a breakpoint in the code line below
        log.info("Before " + definition + " with body " + exchange.getIn().getBody());
    }

};