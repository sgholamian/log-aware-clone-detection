//,temp,Xen.java,412,422,temp,Xen.java,400,410
//,3
public class xxx {
        private Boolean addDiskToDisks(String image, String devName, String mode) {
            for (String disk : vmDisks) {
                if (disk.contains(image)) {
                    LOGGER.debug(vmName + " already has disk " +image+ ":" + devName + ":" + mode);
                    return true;
                }
            }
            vmDisks.add("file:" + image + "," + devName + "," + mode);
            vmParams.put("disk", vmDisks);
            return true;
        }

};