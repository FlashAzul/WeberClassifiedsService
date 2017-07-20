package model;

import java.util.List;

/**
 * Created by Bryan Fritz on 7/15/2017.
 */
public class Listing {

    private Long id;
    private String title;
    private String message;
    private User user;
    private String price;
    private String type;
    private String category;
    private List<Long> attachmentIds;

    public Listing (Long id, String title, String message, User user, String type, String category, List<Long>
            attachmentIds) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.user = user;
        this.type = type;
        this.category = category;
        this.attachmentIds = attachmentIds;
    }

    public Listing () {
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

    public String getPrice () {
        return price;
    }

    public void setPrice (String price) {
        this.price = price;
    }

    public List<Long> getAttachmentIds () {
        return attachmentIds;
    }

    public void setAttachmentIds (List<Long> attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
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

    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }
}
