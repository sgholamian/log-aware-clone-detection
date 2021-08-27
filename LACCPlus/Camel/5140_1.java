//,temp,Hl7Util.java,210,299,temp,Hl7AcknowledgementGenerator.java,79,159
//,3
public class xxx {
    public static void generateAcknowledgementPayload(
            MllpSocketBuffer mllpSocketBuffer, byte[] hl7MessageBytes, String acknowledgementCode, String msa3)
            throws MllpAcknowledgementGenerationException {
        if (hl7MessageBytes == null) {
            throw new MllpAcknowledgementGenerationException("Null HL7 message received for parsing operation");
        }

        List<Integer> fieldSeparatorIndexes = findFieldSeparatorIndicesInSegment(hl7MessageBytes, 0);

        if (fieldSeparatorIndexes.isEmpty()) {
            throw new MllpAcknowledgementGenerationException(
                    "Failed to find the end of the MSH Segment while attempting to generate response", hl7MessageBytes);
        }

        if (fieldSeparatorIndexes.size() < 8) {
            String exceptionMessage = String.format(
                    "Insufficient number of fields found in MSH to generate a response - 10 are required but %d were found",
                    fieldSeparatorIndexes.size() - 1);

            throw new MllpAcknowledgementGenerationException(exceptionMessage, hl7MessageBytes);
        }

        final byte fieldSeparator = hl7MessageBytes[3];

        // Start building the MLLP Envelope
        mllpSocketBuffer.openMllpEnvelope();

        // Build the MSH Segment
        mllpSocketBuffer.write(hl7MessageBytes, 0, fieldSeparatorIndexes.get(1)); // through MSH-2 (without trailing field separator)
        writeFieldToBuffer(3, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes); // MSH-5
        writeFieldToBuffer(4, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes); // MSH-6
        writeFieldToBuffer(1, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes); // MSH-3
        writeFieldToBuffer(2, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes); // MSH-4

        // MSH-7
        mllpSocketBuffer.write(fieldSeparator);
        mllpSocketBuffer.write(TIMESTAMP_FORMAT.format(new Date()).getBytes());

        // Don't copy MSH-8
        mllpSocketBuffer.write(fieldSeparator);

        // Need to generate the correct MSH-9
        mllpSocketBuffer.write(fieldSeparator);
        mllpSocketBuffer.write("ACK".getBytes()); // MSH-9.1
        int msh92start = -1;
        for (int j = fieldSeparatorIndexes.get(7) + 1; j < fieldSeparatorIndexes.get(8); ++j) {
            final byte componentSeparator = hl7MessageBytes[4];
            if (componentSeparator == hl7MessageBytes[j]) {
                msh92start = j;
                break;
            }
        }

        // MSH-9.2
        if (-1 == msh92start) {
            LOG.warn("Didn't find component separator for MSH-9.2 - sending ACK in MSH-9");
        } else {
            mllpSocketBuffer.write(hl7MessageBytes, msh92start, fieldSeparatorIndexes.get(8) - msh92start);
        }

        // MSH-10 - use the original control ID, but add an "A" as a suffix
        writeFieldToBuffer(8, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes);
        if (fieldSeparatorIndexes.get(9) - fieldSeparatorIndexes.get(8) > 1) {
            mllpSocketBuffer.write('A');
        }

        // MSH-10 through the end of the MSH
        mllpSocketBuffer.write(hl7MessageBytes, fieldSeparatorIndexes.get(9),
                fieldSeparatorIndexes.get(fieldSeparatorIndexes.size() - 1) - fieldSeparatorIndexes.get(9));

        mllpSocketBuffer.write(MllpProtocolConstants.SEGMENT_DELIMITER);

        // Build the MSA Segment
        mllpSocketBuffer.write("MSA".getBytes(), 0, 3);
        mllpSocketBuffer.write(fieldSeparator);
        mllpSocketBuffer.write(acknowledgementCode.getBytes(), 0, 2);
        writeFieldToBuffer(8, mllpSocketBuffer, hl7MessageBytes, fieldSeparatorIndexes); // MSH-10 => MSA-2

        if (msa3 != null && !msa3.isEmpty()) {
            mllpSocketBuffer.write(fieldSeparator);
            mllpSocketBuffer.write(msa3.getBytes());
        }

        mllpSocketBuffer.write(MllpProtocolConstants.SEGMENT_DELIMITER);

        // Close the MLLP Envelope
        mllpSocketBuffer.write(MllpProtocolConstants.PAYLOAD_TERMINATOR);

        return;
    }

};