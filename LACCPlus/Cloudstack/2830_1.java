//,temp,NfsSecondaryStorageResource.java,3195,3320,temp,DownloadManagerImpl.java,365,484
//,3
public class xxx {
    public String postUpload(String uuid, String filename, long processTimeout) {
        UploadEntity uploadEntity = uploadEntityStateMap.get(uuid);
        int installTimeoutPerGig = 180 * 60 * 1000;

        String resourcePath = uploadEntity.getInstallPathPrefix();
        String finalResourcePath = uploadEntity.getTmpltPath(); // template download
        UploadEntity.ResourceType resourceType = uploadEntity.getResourceType();

        String fileSavedTempLocation = uploadEntity.getInstallPathPrefix() + "/" + filename;

        String uploadedFileExtension = FilenameUtils.getExtension(filename);
        String userSelectedFormat = uploadEntity.getFormat().toString();
        if (uploadedFileExtension.equals("zip") || uploadedFileExtension.equals("bz2") || uploadedFileExtension.equals("gz")) {
            userSelectedFormat += "." + uploadedFileExtension;
        }
        String formatError = ImageStoreUtil.checkTemplateFormat(fileSavedTempLocation, userSelectedFormat);
        if (StringUtils.isNotBlank(formatError)) {
            String errorString = "File type mismatch between uploaded file and selected format. Selected file format: " + userSelectedFormat + ". Received: " + formatError;
            s_logger.error(errorString);
            return errorString;
        }

        int imgSizeGigs = getSizeInGB(_storage.getSize(fileSavedTempLocation));
        int maxSize = uploadEntity.getMaxSizeInGB();
        if (imgSizeGigs > maxSize) {
            String errorMessage = "Maximum file upload size exceeded. Physical file size: " + imgSizeGigs + "GB. Maximum allowed size: " + maxSize + "GB.";
            s_logger.error(errorMessage);
            return errorMessage;
        }
        imgSizeGigs++; // add one just in case
        long timeout = (long)imgSizeGigs * installTimeoutPerGig;
        Script scr = new Script(getScriptLocation(resourceType), timeout, s_logger);
        scr.add("-s", Integer.toString(imgSizeGigs));
        scr.add("-S", Long.toString(UploadEntity.s_maxTemplateSize));
        if (uploadEntity.getDescription() != null && uploadEntity.getDescription().length() > 1) {
            scr.add("-d", uploadEntity.getDescription());
        }
        if (uploadEntity.isHvm()) {
            scr.add("-h");
        }
        String checkSum = uploadEntity.getChksum();
        if (StringUtils.isNotBlank(checkSum)) {
            scr.add("-c", checkSum);
        }

        // add options common to ISO and template
        String extension = uploadEntity.getFormat().getFileExtension();
        String templateName = "";
        if (extension.equals("iso")) {
            templateName = uploadEntity.getUuid().trim().replace(" ", "_");
        } else {
            try {
                templateName = UUID.nameUUIDFromBytes((uploadEntity.getFilename() + System.currentTimeMillis()).getBytes("UTF-8")).toString();
            } catch (UnsupportedEncodingException e) {
                templateName = uploadEntity.getUuid().trim().replace(" ", "_");
            }
        }

        // run script to mv the temporary template file to the final template
        // file
        String templateFilename = templateName + "." + extension;
        uploadEntity.setTemplatePath(finalResourcePath + "/" + templateFilename);
        scr.add("-n", templateFilename);

        scr.add("-t", resourcePath);
        scr.add("-f", fileSavedTempLocation); // this is the temporary
        // template file downloaded
        if (uploadEntity.getChksum() != null && uploadEntity.getChksum().length() > 1) {
            scr.add("-c", uploadEntity.getChksum());
        }
        scr.add("-u"); // cleanup
        String result;
        result = scr.execute();

        if (result != null) {
            return result;
        }

        // Set permissions for the downloaded template
        File downloadedTemplate = new File(resourcePath + "/" + templateFilename);
        _storage.setWorldReadableAndWriteable(downloadedTemplate);

        // Set permissions for template/volume.properties
        String propertiesFile = resourcePath;
        if (resourceType == UploadEntity.ResourceType.TEMPLATE) {
            propertiesFile += "/template.properties";
        } else {
            propertiesFile += "/volume.properties";
        }
        File templateProperties = new File(propertiesFile);
        _storage.setWorldReadableAndWriteable(templateProperties);

        TemplateLocation loc = new TemplateLocation(_storage, resourcePath);
        try {
            loc.create(uploadEntity.getEntityId(), true, uploadEntity.getFilename());
        } catch (IOException e) {
            s_logger.warn("Something is wrong with template location " + resourcePath, e);
            loc.purge();
            return "Unable to upload due to " + e.getMessage();
        }

        Map<String, Processor> processors = _dlMgr.getProcessors();
        for (Processor processor : processors.values()) {
            FormatInfo info = null;
            try {
                info = processor.process(resourcePath, null, templateName, processTimeout * 1000);
            } catch (InternalErrorException e) {
                s_logger.error("Template process exception ", e);
                return e.toString();
            }
            if (info != null) {
                loc.addFormat(info);
                uploadEntity.setVirtualSize(info.virtualSize);
                uploadEntity.setPhysicalSize(info.size);
                break;
            }
        }

        if (!loc.save()) {
            s_logger.warn("Cleaning up because we're unable to save the formats");
            loc.purge();
        }
        uploadEntity.setStatus(UploadEntity.Status.COMPLETED);
        uploadEntityStateMap.put(uploadEntity.getUuid(), uploadEntity);
        return null;
    }

};