//,temp,FilePendingMessageCursor.java,257,295,temp,FilePendingMessageCursor.java,209,250
//,3
public class xxx {
    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long maxWaitTime) throws Exception {
        if (!node.isExpired()) {
            try {
                regionDestination = (Destination) node.getMessage().getRegionDestination();
                if (isDiskListEmpty()) {
                    if (hasSpace() || this.store == null) {
                        memoryList.addMessageLast(node);
                        node.incrementReferenceCount();
                        setCacheEnabled(true);
                        return true;
                    }
                }
                if (!hasSpace()) {
                    if (isDiskListEmpty()) {
                        expireOldMessages();
                        if (hasSpace()) {
                            memoryList.addMessageLast(node);
                            node.incrementReferenceCount();
                            return true;
                        } else {
                            flushToDisk();
                        }
                    }
                }
                if (systemUsage.getTempUsage().waitForSpace(maxWaitTime)) {
                    ByteSequence bs = getByteSequence(node.getMessage());
                    getDiskList().addLast(node.getMessageId().toString(), bs);
                    return true;
                }
                return false;

            } catch (Exception e) {
                LOG.error("Caught an Exception adding a message: {} first to FilePendingMessageCursor ", node, e);
                throw new RuntimeException(e);
            }
        } else {
            discardExpiredMessage(node);
        }
        //message expired
        return true;
    }

};