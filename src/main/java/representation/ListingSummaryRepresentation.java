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
    private UserRepresentation user;
    private Long attachmentId;

    public ListingSummaryRepresentation (Long id, String title, String messageSummary, UserRepresentation user, Long
            attachmentId) {
        this.id = id;
        this.title = title;
        this.messageSummary = messageSummary;
        this.user = user;
        this.attachmentId = attachmentId;
    }

    public ListingSummaryRepresentation () {
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

    public UserRepresentation getUser () {
        return user;
    }

    public void setUser (UserRepresentation user) {
        this.user = user;
    }

}
