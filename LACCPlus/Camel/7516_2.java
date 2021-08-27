//,temp,DataFormatTransformer.java,56,92,temp,ProcessorTransformer.java,55,88
//,3
public class xxx {
    @Override
    public void transform(Message message, DataType from, DataType to) throws Exception {
        Exchange exchange = message.getExchange();
        CamelContext context = exchange.getContext();
        if (from.isJavaType()) {
            Object input = message.getBody();
            Class<?> fromClass = context.getClassResolver().resolveClass(from.getName());
            if (!fromClass.isAssignableFrom(input.getClass())) {
                LOG.debug("Converting to: {}", fromClass.getName());
                input = context.getTypeConverter().mandatoryConvertTo(fromClass, input);
                message.setBody(input);
            }
        }

        LOG.debug("Sending to transform processor: {}", processor);
        // must create a copy in this way
        Exchange transformExchange = new DefaultExchange(exchange);
        transformExchange.setIn(message);
        transformExchange.adapt(ExtendedExchange.class).setProperties(exchange.getProperties());
        processor.process(transformExchange);
        Message answer = transformExchange.getMessage();

        if (to.isJavaType()) {
            Object answerBody = answer.getBody();
            Class<?> toClass = context.getClassResolver().resolveClass(to.getName());
            if (!toClass.isAssignableFrom(answerBody.getClass())) {
                LOG.debug("Converting to: {}", toClass.getName());
                answerBody = context.getTypeConverter().mandatoryConvertTo(toClass, answerBody);
                answer.setBody(answerBody);
            }
        }

        message.copyFrom(answer);
    }

};