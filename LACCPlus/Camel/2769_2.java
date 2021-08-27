//,temp,RecipientListWithInterceptorTest.java,51,61,temp,DynamicRouterWithInterceptorTest.java,53,63
//,2
public class xxx {
                    @Override
                    public boolean process(final Exchange exchange, final AsyncCallback callback) {
                        LOGGER.info("I'm doing someting");
                        return super.process(exchange, new AsyncCallback() {
                            public void done(final boolean doneSync) {
                                LOGGER.info("I'm done");
                                doneCount++;
                                callback.done(doneSync);
                            }
                        });
                    }

};