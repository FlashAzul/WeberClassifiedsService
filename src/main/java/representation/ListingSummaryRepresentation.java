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
    private UserRepresentation user;
    private String category;

    public ListingSummaryRepresentation (Long id, String title, String messageSummary, UserRepresentation user,
            String type, String category, String price) {
        this.id = id;
        this.title = title;
        this.messageSummary = messageSummary;
        this.user = user;
        this.price = price;
        this.type = type;
        this.category = category;
    }

    public ListingSummaryRepresentation () {
    }

    public UserRepresentation getUser () {
        return user;
    }

    public void setUser (UserRepresentation user) {
        this.user = user;
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

    public String getMessageSummary () {
        return messageSummary;
    }

    public void setMessageSummary (String messageSummary) {
        this.messageSummary = messageSummary;
    }

}
