package model;

public class Attachment {
    private String description;
    private byte[] attachmentBytes;

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public byte[] getAttachmentBytes () {
        return attachmentBytes;
    }

    public void setAttachmentBytes (byte[] attachmentBytes) {
        this.attachmentBytes = attachmentBytes;
    }
}
