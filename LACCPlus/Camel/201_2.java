//,temp,BeanValidatorInputValidateTest.java,50,59,temp,BeanValidatorOutputValidateTest.java,54,63
//,2
public class xxx {
        @Override
        public void validate(Message message, DataType type) throws ValidationException {
            Object body = message.getBody();
            LOG.info("Validating : [{}]", body);
            if (body instanceof String && body.equals("valid")) {
                LOG.info("OK");
            } else {
                throw new ValidationException(message.getExchange(), "Wrong content");
            }
        }

};