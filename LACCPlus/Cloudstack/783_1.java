//,temp,NiciraNvpTag.java,55,62,temp,NiciraNvpTag.java,33,41
//,3
public class xxx {
    public void setTag(String tag) {
        if (tag.length() > 40) {
            s_logger.warn("tag \"" + tag + "\" too long, truncating to 40 characters");
            this.tag = tag.substring(0, 40);
        } else {
            this.tag = tag;
        }
    }

};