//,temp,ConnectionStateTracker.java,351,361,temp,ConnectionStateTracker.java,335,344
//,3
public class xxx {
    protected void restoreProducers(Transport transport, SessionState sessionState) throws IOException {
        // Restore the session's producers
        for (Iterator iter3 = sessionState.getProducerStates().iterator(); iter3.hasNext();) {
            ProducerState producerState = (ProducerState)iter3.next();
            if (LOG.isDebugEnabled()) {
                LOG.debug("producer: " + producerState.getInfo().getProducerId());
            }
            transport.oneway(producerState.getInfo());
        }
    }

};