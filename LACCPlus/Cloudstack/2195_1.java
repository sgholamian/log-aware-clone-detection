//,temp,Xen.java,412,422,temp,Xen.java,400,410
//,3
public class xxx {
        public Boolean removeDisk(String image) {
            for (String disk : vmDisks) {
                if (disk.contains(image)) {
                    vmDisks.remove(disk);
                    vmParams.put("disk", vmDisks);
                    return true;
                }
            }
            LOGGER.debug("No disk found corresponding to image: " + image);
            return false;
        }

};