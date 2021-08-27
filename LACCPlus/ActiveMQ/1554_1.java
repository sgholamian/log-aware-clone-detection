//,temp,DeleteMessage.java,41,52,temp,MoveMessage.java,42,53
//,3
public class xxx {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (messageId != null) {
            QueueViewMBean queueView = getQueueView();
            if (queueView != null) {
                log.info("Removing message " + getJMSDestination() + "(" + messageId + ")");
                queueView.removeMessage(messageId);
            } else {
            	log.warn("No queue named: " + getPhysicalDestinationName());
            }
        }
        return redirectToDestinationView();
    }

};