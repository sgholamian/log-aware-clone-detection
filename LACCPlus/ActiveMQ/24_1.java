//,temp,PreallocationJournalTest.java,116,120,temp,NonBlockingConsumerRedeliveryTest.java,99,103
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info ("file size:" + journalLog + ", chan.size " + channel.size() + ", jfileSize.length: " + journalLog.length());
                return Journal.DEFAULT_MAX_FILE_LENGTH == channel.size();
            }

};