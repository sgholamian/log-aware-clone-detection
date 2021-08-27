//,temp,ConnectionStateTracker.java,351,361,temp,ConnectionStateTracker.java,335,344
//,3
public class xxx {
    protected void restoreTempDestinations(Transport transport, ConnectionState connectionState)
        throws IOException {
        // Restore the connection's temp destinations.
        for (Iterator iter2 = connectionState.getTempDestinations().iterator(); iter2.hasNext();) {
            DestinationInfo info = (DestinationInfo)iter2.next();
            transport.oneway(info);
            if (LOG.isDebugEnabled()) {
                LOG.debug("tempDest: " + info.getDestination());
            }
        }
    }

};