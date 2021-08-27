//,temp,sample_5481.java,2,16,temp,sample_3229.java,2,12
//,3
public class xxx {
public void dummy_method(){
if (ObjectHelper.isEmpty(volumeId)) {
String name = exchange.getIn().getHeader(DigitalOceanHeaders.NAME, String.class);
String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
if (ObjectHelper.isEmpty(name) && ObjectHelper.isEmpty(region)) {
throw new IllegalArgumentException(DigitalOceanHeaders.ID + " or " + DigitalOceanHeaders.NAME + " and " + DigitalOceanHeaders.REGION + " must be specified");
}
delete = getEndpoint().getDigitalOceanClient().deleteVolume(name, region);
} else {
delete = getEndpoint().getDigitalOceanClient().deleteVolume(volumeId);
}


log.info("delete volume");
}

};