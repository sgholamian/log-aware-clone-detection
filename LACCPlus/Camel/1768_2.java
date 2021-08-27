//,temp,Lambda2Producer.java,537,567,temp,Lambda2Producer.java,372,404
//,3
public class xxx {
    private void updateFunction(LambdaClient lambdaClient, Exchange exchange) throws Exception {
        UpdateFunctionCodeRequest request = null;
        UpdateFunctionCodeResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(UpdateFunctionCodeRequest.class);
        } else {
            UpdateFunctionCodeRequest.Builder builder = UpdateFunctionCodeRequest.builder();
            builder.functionName(getEndpoint().getFunction());

            if (ObjectHelper.isEmpty(exchange.getIn().getBody())
                    && (ObjectHelper.isEmpty(exchange.getIn().getHeader(Lambda2Constants.S3_BUCKET))
                            && ObjectHelper.isEmpty(exchange.getIn().getHeader(Lambda2Constants.S3_KEY)))) {
                throw new IllegalArgumentException("At least S3 bucket/S3 key or zip file must be specified");
            }

            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.PUBLISH))) {
                Boolean publish = exchange.getIn().getHeader(Lambda2Constants.PUBLISH, Boolean.class);
                builder.publish(publish);
            }

            request = builder.build();
        }
        try {
            result = lambdaClient.updateFunctionCode(request);

        } catch (AwsServiceException ase) {
            LOG.trace("updateFunction command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }

        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};