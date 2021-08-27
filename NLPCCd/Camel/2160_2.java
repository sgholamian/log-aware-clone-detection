//,temp,sample_261.java,2,16,temp,sample_6407.java,2,16
//,2
public class xxx {
public void dummy_method(){
Integer dropletId = exchange.getIn().getHeader(DigitalOceanHeaders.DROPLET_ID, Integer.class);
String region = exchange.getIn().getHeader(DigitalOceanHeaders.REGION, String.class);
FloatingIP ip;
if (ObjectHelper.isNotEmpty(dropletId)) {
ip = getEndpoint().getDigitalOceanClient().createFloatingIP(dropletId);
} else if (ObjectHelper.isNotEmpty(region)) {
ip = getEndpoint().getDigitalOceanClient().createFloatingIP(region);
} else {
throw new IllegalArgumentException(DigitalOceanHeaders.DROPLET_ID + " or " + DigitalOceanHeaders.REGION + " must be specified");
}


log.info("floatingip");
}

};