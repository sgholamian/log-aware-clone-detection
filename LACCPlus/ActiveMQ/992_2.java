//,temp,AMQ3274Test.java,604,631,temp,RequestReplyToTopicViaThreeNetworkHopsTest.java,716,746
//,3
public class xxx {
        protected void checkMessage(Message in_msg) throws Exception {
            int seq;

            LOG.debug("received message " + fmtMsgInfo(in_msg) + " from " + in_msg.getJMSDestination());

            //
            // Only check messages with a sequence number.
            //

            if (in_msg.propertyExists("SEQ")) {
                seq = in_msg.getIntProperty("SEQ");

                if ((haveFirstSeq) && (seq != (lastSeq + 1))) {
                    LOG.error("***ERROR*** incorrect sequence number; expected " + Integer.toString(lastSeq + 1) + " but have " + Integer.toString(seq));

                    testError = true;
                }

                lastSeq = seq;

                if (msgCount > expectedCount) {
                    LOG.error("*** have more messages than expected; have " + msgCount + "; expect " + expectedCount);

                    testError = true;
                }
            }

            if (in_msg.propertyExists("end-of-response")) {
                LOG.trace("received end-of-response message");
            }
        }

};