//,temp,AMQ5486Test.java,128,132,temp,VirtualTopicWildcardTest.java,126,130
//,3
public class xxx {
                @Override
                public boolean isSatisified() throws Exception {
                    LOG.info("One: " + messageList1.getMessages().size() + ", Two:" + messageList2.getMessages().size());
                    return messageList1.getMessages().size() + messageList2.getMessages().size() == 2 * total;
                }

};