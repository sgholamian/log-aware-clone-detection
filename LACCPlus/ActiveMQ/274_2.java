//,temp,RetryMessage.java,41,52,temp,CopyMessage.java,42,53
//,3
public class xxx {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (messageId != null) {
            QueueViewMBean queueView = getQueueView();
            if (queueView != null) {
            	log.info(getJMSDestination() + "(" + messageId + ")" + " copy to " + destination);
                queueView.copyMessageTo(messageId, destination);
            } else {
            	log.warn("No queue named: " + getPhysicalDestinationName());
            }
        }
        return redirectToDestinationView();
    }

};