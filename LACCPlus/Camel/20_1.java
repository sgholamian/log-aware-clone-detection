//,temp,Athena2Producer.java,370,380,temp,Athena2Producer.java,310,319
//,2
public class xxx {
    private EncryptionOption determineEncryptionOption(final Exchange exchange) {
        EncryptionOption encryptionOption
                = exchange.getIn().getHeader(Athena2Constants.ENCRYPTION_OPTION, EncryptionOption.class);

        if (ObjectHelper.isEmpty(encryptionOption)) {
            encryptionOption = getConfiguration().getEncryptionOption();
            LOG.trace("AWS Athena encryption option is missing, using default one [{}]", encryptionOption);
        }

        return encryptionOption;
    }

};