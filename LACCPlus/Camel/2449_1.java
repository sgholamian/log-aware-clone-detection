//,temp,VertxPlatformHttpConsumer.java,276,305,temp,DefaultHttpBinding.java,308,339
//,3
public class xxx {
    private void populateAttachments(Set<FileUpload> uploads, Message message) {
        for (FileUpload upload : uploads) {
            final String name = upload.name();
            final String fileName = upload.fileName();

            LOGGER.trace("HTTP attachment {} = {}", name, fileName);

            // is the file name accepted
            boolean accepted = true;

            if (fileNameExtWhitelist != null) {
                String ext = FileUtil.onlyExt(fileName);
                if (ext != null) {
                    ext = ext.toLowerCase(Locale.US);
                    if (!fileNameExtWhitelist.equals("*") && !fileNameExtWhitelist.contains(ext)) {
                        accepted = false;
                    }
                }
            }
            if (accepted) {
                final File localFile = new File(upload.uploadedFileName());
                final AttachmentMessage attachmentMessage = message.getExchange().getMessage(AttachmentMessage.class);
                attachmentMessage.addAttachment(fileName, new DataHandler(new CamelFileDataSource(localFile, fileName)));
            } else {
                LOGGER.debug(
                        "Cannot add file as attachment: {} because the file is not accepted according to fileNameExtWhitelist: {}",
                        fileName, fileNameExtWhitelist);
            }
        }
    }

};