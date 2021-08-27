//,temp,TarIterator.java,120,148,temp,ZipIterator.java,104,127
//,3
public class xxx {
    private Message getNextElement() {
        if (tarInputStream == null) {
            return null;
        }

        try {
            TarArchiveEntry current = getNextEntry();

            if (current != null) {
                LOGGER.debug("Reading tarEntry {}", current.getName());
                Message answer = new DefaultMessage(exchange.getContext());
                answer.getHeaders().putAll(exchange.getIn().getHeaders());
                answer.setHeader(TARFILE_ENTRY_NAME_HEADER, current.getName());
                answer.setHeader(Exchange.FILE_NAME, current.getName());
                if (current.getSize() > 0) {
                    answer.setBody(new TarElementInputStreamWrapper(tarInputStream));
                } else {
                    // Workaround for the case when the entry is zero bytes big
                    answer.setBody(new ByteArrayInputStream(new byte[0]));
                }
                return answer;
            } else {
                LOGGER.trace("Closed tarInputStream");
                return null;
            }
        } catch (IOException exception) {
            throw new RuntimeCamelException(exception);
        }
    }

};