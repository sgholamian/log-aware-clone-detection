//,temp,DestinationView.java,269,296,temp,DestinationView.java,226,255
//,3
public class xxx {
    @Override
    public CompositeData[] browse(String selector) throws OpenDataException, InvalidSelectorException {
        Message[] messages = destination.browse();
        ArrayList<CompositeData> c = new ArrayList<CompositeData>();

        NonCachedMessageEvaluationContext ctx = new NonCachedMessageEvaluationContext();
        ctx.setDestination(destination.getActiveMQDestination());
        BooleanExpression selectorExpression = selector == null ? null : SelectorParser.parse(selector);

        for (int i = 0; i < messages.length; i++) {
            try {

                if (selectorExpression == null) {
                    c.add(OpenTypeSupport.convert(messages[i]));
                } else {
                    ctx.setMessageReference(messages[i]);
                    if (selectorExpression.matches(ctx)) {
                        c.add(OpenTypeSupport.convert(messages[i]));
                    }
                }

            } catch (Throwable e) {
                LOG.warn("exception browsing destination", e);
            }
        }

        CompositeData rc[] = new CompositeData[c.size()];
        c.toArray(rc);
        return rc;
    }

};