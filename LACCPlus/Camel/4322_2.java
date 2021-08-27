//,temp,TarIterator.java,120,148,temp,ZipIterator.java,104,127
//,3
public class xxx {
    private Message getNextElement() {
        if (zipInputStream == null) {
            return null;
        }

        try {
            ZipEntry current = getNextEntry();

            if (current != null) {
                LOGGER.debug("read zipEntry {}", current.getName());
                Message answer = new DefaultMessage(exchange.getContext());
                answer.getHeaders().putAll(exchange.getIn().getHeaders());
                answer.setHeader("zipFileName", current.getName());
                answer.setHeader(Exchange.FILE_NAME, current.getName());
                answer.setBody(new ZipInputStreamWrapper(zipInputStream));
                return answer;
            } else {
                LOGGER.trace("close zipInputStream");
                return null;
            }
        } catch (IOException exception) {
            throw new RuntimeCamelException(exception);
        }
    }

};