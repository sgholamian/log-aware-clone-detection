//,temp,XQueryBuilder.java,230,240,temp,XQueryBuilder.java,220,228
//,3
public class xxx {
    public Node evaluateAsDOM(Exchange exchange) throws Exception {
        LOG.debug("evaluateAsDOM: {} for exchange: {}", expression, exchange);

        DOMResult result = new DOMResult();
        DynamicQueryContext context = createDynamicContext(exchange);
        XQueryExpression expression = getExpression();
        expression.pull(context, result, properties);
        return result.getNode();
    }

};