package representation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Bryan Fritz on 7/15/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingSummaryRepresentation {

    private Long id;
    private String title;
    private String messageSummary;
    private String price;
    private String type;
    private UserRepresentation userRepresentation;
    private String category;
    private Long attachmentId;

    public ListingSummaryRepresentation (Long id, String title, String messageSummary, UserRepresentation
            userRepresentation, Long attachmentId, String type, String category, String price) {
        this.id = id;
        this.title = title;
        this.messageSummary = messageSummary;
        this.userRepresentation = userRepresentation;
        this.attachmentId = attachmentId;
        this.price = price;
        this.type = type;
        this.category = category;
    }

    public ListingSummaryRepresentation () {
    }

    public UserRepresentation getUserRepresentation () {
        return userRepresentation;
    }

    public void setUserRepresentation (UserRepresentation userRepresentation) {
        this.userRepresentation = userRepresentation;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getCategory () {
        return category;
    }

    public void setCategory (String category) {
        this.category = category;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getPrice () {
        return price;
    }

    public void setPrice (String price) {
        this.price = price;
    }

    public void setAttachmentId (Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentId () {
        return attachmentId;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getMessageSummary () {
        return messageSummary;
    }

    public void setMessageSummary (String messageSummary) {
        this.messageSummary = messageSummary;
    }

}
