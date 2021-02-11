//,temp,ObserverRequestProcessor.java,67,120,temp,ReadOnlyRequestProcessor.java,63,118
//,3
public class xxx {
    public void run() {
        try {
            while (!finished) {
                Request request = queuedRequests.take();

                // log request
                long traceMask = ZooTrace.CLIENT_REQUEST_TRACE_MASK;
                if (request.type == OpCode.ping) {
                    traceMask = ZooTrace.CLIENT_PING_TRACE_MASK;
                }
                if (LOG.isTraceEnabled()) {
                    ZooTrace.logRequest(LOG, traceMask, 'R', request, "");
                }
                if (Request.requestOfDeath == request) {
                    break;
                }

                // filter read requests
                switch (request.type) {
                case OpCode.sync:
                case OpCode.create:
                case OpCode.create2:
                case OpCode.createTTL:
                case OpCode.createContainer:
                case OpCode.delete:
                case OpCode.deleteContainer:
                case OpCode.setData:
                case OpCode.reconfig:
                case OpCode.setACL:
                case OpCode.multi:
                case OpCode.check:
                    ReplyHeader hdr = new ReplyHeader(request.cxid, zks.getZKDatabase()
                            .getDataTreeLastProcessedZxid(), Code.NOTREADONLY.intValue());
                    try {
                        request.cnxn.sendResponse(hdr, null, null);
                    } catch (IOException e) {
                        LOG.error("IO exception while sending response", e);
                    }
                    continue;
                }

                // proceed to the next processor
                if (nextProcessor != null) {
                    nextProcessor.processRequest(request);
                }
            }
        } catch (RequestProcessorException e) {
            if (e.getCause() instanceof XidRolloverException) {
                LOG.info(e.getCause().getMessage());
            }
            handleException(this.getName(), e);
        } catch (Exception e) {
            handleException(this.getName(), e);
        }
        LOG.info("ReadOnlyRequestProcessor exited loop!");
    }

};