package model;

/**
 * Created by Bryan Fritz on 7/15/2017.
 */
public class Listing {
    private String listingText;
    private String wNumber;
    private int listingId;

    public Listing(String listingText, String wNumber, int listingId){
        setListingText(listingText);
        setwNumber(wNumber);
        setListingId(listingId);

    }
    public String getwNumber() {
        return wNumber;
    }

    public void setwNumber(String wNumber) {
        this.wNumber = wNumber;
    }

    public String getListingText() {
        return listingText;
    }

    public void setListingText(String listingText) {
        this.listingText = listingText;
    }


    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

}
