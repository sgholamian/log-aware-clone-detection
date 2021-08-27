//,temp,DataFormatTransformer.java,56,92,temp,ProcessorTransformer.java,55,88
//,3
public class xxx {
    @Override
    public void transform(Message message, DataType from, DataType to) throws Exception {
        Exchange exchange = message.getExchange();
        CamelContext context = exchange.getContext();

        // Unmarshaling into Java Object
        if ((to == null || to.isJavaType()) && (from.equals(getFrom()) || from.getModel().equals(getModel()))) {
            LOG.debug("Unmarshaling with: {}", dataFormat);
            Object answer = dataFormat.unmarshal(exchange, message.getBody(InputStream.class));
            if (to != null && to.getName() != null) {
                Class<?> toClass = context.getClassResolver().resolveClass(to.getName());
                if (!toClass.isAssignableFrom(answer.getClass())) {
                    LOG.debug("Converting to: {}", toClass.getName());
                    answer = context.getTypeConverter().mandatoryConvertTo(toClass, answer);
                }
            }
            message.setBody(answer);

            // Marshaling from Java Object
        } else if ((from == null || from.isJavaType()) && (to.equals(getTo()) || to.getModel().equals(getModel()))) {
            Object input = message.getBody();
            if (from != null && from.getName() != null) {
                Class<?> fromClass = context.getClassResolver().resolveClass(from.getName());
                if (!fromClass.isAssignableFrom(input.getClass())) {
                    LOG.debug("Converting to: {}", fromClass.getName());
                    input = context.getTypeConverter().mandatoryConvertTo(fromClass, input);
                }
            }
            OutputStreamBuilder osb = OutputStreamBuilder.withExchange(exchange);
            LOG.debug("Marshaling with: {}", dataFormat);
            dataFormat.marshal(exchange, message.getBody(), osb);
            message.setBody(osb.build());

        } else {
            throw new IllegalArgumentException("Unsupported transformation: from='" + from + ", to='" + to + "'");
        }
    }

};