//,temp,VertxPlatformHttpConsumer.java,276,305,temp,DefaultHttpBinding.java,308,339
//,3
public class xxx {
    protected void populateAttachments(HttpServletRequest request, HttpMessage message) {
        // check if there is multipart files, if so will put it into DataHandler
        Enumeration<?> names = request.getAttributeNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            Object object = request.getAttribute(name);
            LOG.trace("HTTP attachment {} = {}", name, object);
            if (object instanceof File) {
                String fileName = request.getParameter(name);
                // is the file name accepted
                boolean accepted = true;
                if (fileNameExtWhitelist != null) {
                    String ext = FileUtil.onlyExt(fileName);
                    if (ext != null) {
                        ext = ext.toLowerCase(Locale.US);
                        fileNameExtWhitelist = fileNameExtWhitelist.toLowerCase(Locale.US);
                        if (!fileNameExtWhitelist.equals("*") && !fileNameExtWhitelist.contains(ext)) {
                            accepted = false;
                        }
                    }
                }
                if (accepted) {
                    AttachmentMessage am = message.getExchange().getMessage(AttachmentMessage.class);
                    am.addAttachment(fileName, new DataHandler(new CamelFileDataSource((File) object, fileName)));
                } else {
                    LOG.debug(
                            "Cannot add file as attachment: {} because the file is not accepted according to fileNameExtWhitelist: {}",
                            fileName, fileNameExtWhitelist);
                }
            }
        }
    }

};