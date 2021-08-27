//,temp,AsyncCompletionServiceTest.java,191,200,temp,AsyncCompletionServiceTest.java,180,189
//,3
public class xxx {
    Consumer<Consumer<Object>> result(Object r, int delay) {
        return result -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                LOG.info("The test execution was interrupted", e);
            }
            result.accept(r);
        };
    }

};