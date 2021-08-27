//,temp,AhcProducer.java,122,132,temp,TelegramServiceRestBotAPIAdapter.java,457,466
//,3
public class xxx {
        @Override
        public State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                throws Exception {
            // write body parts to stream, which we will bind to the Camel Exchange in onComplete
            os.write(bodyPart.getBodyPartBytes());
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} onBodyPartReceived {} bytes", exchange.getExchangeId(), bodyPart.length());
            }
            contentLength += bodyPart.length();
            return State.CONTINUE;
        }

};