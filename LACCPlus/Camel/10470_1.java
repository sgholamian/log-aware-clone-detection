//,temp,RestSegmentDecorator.java,58,79,temp,RestSpanDecorator.java,69,90
//,2
public class xxx {
    protected static String getPath(String uri) {
        // Obtain the 'path' part of the URI format: rest://method:path[:uriTemplate]?[options]
        String path = null;
        int index = uri.indexOf(':');
        if (index != -1) {
            index = uri.indexOf(':', index + 1);
            if (index != -1) {
                path = uri.substring(index + 1);
                index = path.indexOf('?');
                if (index != -1) {
                    path = path.substring(0, index);
                }
                path = path.replace(":", "");
                try {
                    path = URLDecoder.decode(path, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOG.debug("Failed to decode URL path '" + path + "', ignoring exception", e);
                }
            }
        }
        return path;
    }

};