//,temp,Athena2Producer.java,144,153,temp,Athena2QueryHelper.java,383,398
//,3
public class xxx {
    private Athena2OutputType determineOutputType(Exchange exchange) {
        Athena2OutputType outputType = exchange.getIn().getHeader(Athena2Constants.OUTPUT_TYPE, Athena2OutputType.class);

        if (ObjectHelper.isEmpty(outputType)) {
            outputType = getConfiguration().getOutputType();
            LOG.trace("AWS Athena output type is missing, using default one [{}]", outputType);
        }

        return outputType;
    }

};