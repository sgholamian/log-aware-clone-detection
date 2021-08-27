//,temp,DelayProcessorSupport.java,72,81,temp,TelegramServiceRestBotAPIAdapter.java,402,409
//,3
public class xxx {
                @Override
                public void done(boolean doneSync) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Delayed task done for exchangeId: {}", exchange.getExchangeId());
                    }
                    // we must done the callback from this async callback as well, to ensure callback is done correctly
                    // must invoke done on callback with false, as that is what the original caller would
                    // expect as we returned false in the process method
                    callback.done(false);
                }

};