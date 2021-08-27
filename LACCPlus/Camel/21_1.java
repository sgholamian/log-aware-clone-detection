//,temp,Athena2Producer.java,407,416,temp,Athena2Producer.java,382,391
//,2
public class xxx {
    private String determineWorkGroup(final Exchange exchange) {
        String workGroup = exchange.getIn().getHeader(Athena2Constants.WORK_GROUP, String.class);

        if (ObjectHelper.isEmpty(workGroup)) {
            workGroup = getConfiguration().getWorkGroup();
            LOG.trace("AWS Athena work group is missing, using default one [{}]", workGroup);
        }

        return workGroup;
    }

};