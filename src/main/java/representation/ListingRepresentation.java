package representation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Bryan Fritz on 7/15/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListingRepresentation {

    private Long id;
    private String title;
    private String message;
    private Long userId;
    private String price;
    private String type;
    private String category;
    private List<Long> attachmentIds;

    public ListingRepresentation (Long id, String title, String message, Long userId, List<Long> attachmentIds,
            String type, String category) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.attachmentIds = attachmentIds;
        this.type = type;
        this.category = category;
    }

    public ListingRepresentation () {
    }

    public Long getUserId () {
        return userId;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
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

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public List<Long> getAttachmentIds () {
        return attachmentIds;
    }

    public void setAttachmentIds (List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }
}
