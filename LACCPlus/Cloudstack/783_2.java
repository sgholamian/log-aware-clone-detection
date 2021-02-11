//,temp,NiciraNvpTag.java,55,62,temp,NiciraNvpTag.java,33,41
//,3
public class xxx {
    public NiciraNvpTag(String scope, String tag) {
        this.scope = scope;
        if (tag.length() > 40) {
            s_logger.warn("tag \"" + tag + "\" too long, truncating to 40 characters");
            this.tag = tag.substring(0, TAG_MAX_LEN);
        } else {
            this.tag = tag;
        }
    }

};