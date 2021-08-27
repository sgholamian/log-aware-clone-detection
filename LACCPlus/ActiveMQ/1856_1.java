//,temp,WireformatNegociationTest.java,107,113,temp,WireformatNegociationTest.java,70,76
//,2
public class xxx {
                        public void onException(IOException error) {
                            if (!ignoreAsycError.get()) {
                                LOG.info("Server transport error: ", error);
                                asyncError.set(error);
                                negociationCounter.countDown();
                            }
                        }

};