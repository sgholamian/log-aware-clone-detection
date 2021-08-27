//,temp,DestinationView.java,269,296,temp,DestinationView.java,226,255
//,3
public class xxx {
    @Override
    public List<Object> browseMessages(String selector) throws InvalidSelectorException {
        Message[] messages = destination.browse();
        ArrayList<Object> answer = new ArrayList<Object>();

        NonCachedMessageEvaluationContext ctx = new NonCachedMessageEvaluationContext();
        ctx.setDestination(destination.getActiveMQDestination());
        BooleanExpression selectorExpression = selector == null ? null : SelectorParser.parse(selector);

        for (int i = 0; i < messages.length; i++) {
            try {
                Message message = messages[i];
                message.setReadOnlyBody(true);
                if (selectorExpression == null) {
                    answer.add(message);
                } else {
                    ctx.setMessageReference(message);
                    if (selectorExpression.matches(ctx)) {
                        answer.add(message);
                    }
                }

            } catch (Throwable e) {
                LOG.warn("exception browsing destination", e);
            }
        }
        return answer;
    }

};