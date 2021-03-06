package representation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

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
    private String listingCreationDateString;

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

    public String getListingCreationDateString() {
        return listingCreationDateString;
    }

    public void setListingCreationDateString(String listingCreationDateString) {
        this.listingCreationDateString = listingCreationDateString;
    }
}
