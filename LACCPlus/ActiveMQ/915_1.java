//,temp,FilePendingMessageCursor.java,257,295,temp,FilePendingMessageCursor.java,209,250
//,3
public class xxx {
    @Override
    public synchronized void addMessageFirst(MessageReference node) {
        if (!node.isExpired()) {
            try {
                regionDestination = (Destination) node.getMessage().getRegionDestination();
                if (isDiskListEmpty()) {
                    if (hasSpace()) {
                        memoryList.addMessageFirst(node);
                        node.incrementReferenceCount();
                        setCacheEnabled(true);
                        return;
                    }
                }
                if (!hasSpace()) {
                    if (isDiskListEmpty()) {
                        expireOldMessages();
                        if (hasSpace()) {
                            memoryList.addMessageFirst(node);
                            node.incrementReferenceCount();
                            return;
                        } else {
                            flushToDisk();
                        }
                    }
                }
                systemUsage.getTempUsage().waitForSpace();
                node.decrementReferenceCount();
                ByteSequence bs = getByteSequence(node.getMessage());
                Object locator = getDiskList().addFirst(node.getMessageId().toString(), bs);
                node.getMessageId().setPlistLocator(locator);

            } catch (Exception e) {
                LOG.error("Caught an Exception adding a message: {} first to FilePendingMessageCursor ", node, e);
                throw new RuntimeException(e);
            }
        } else {
            discardExpiredMessage(node);
        }
    }

};