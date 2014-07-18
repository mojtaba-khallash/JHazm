package JHazm.Utility;

/**
 *
 * @author Mojtaba Khallash
 */
public class Document {
    private String ID;
    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }

    
    private String Number;
    public String getNumber() { return Number; }
    public void setNumber(String Number) { this.Number = Number; }

    
    private String OriginalFile;
    public String getOriginalFile() { return OriginalFile; }
    public void setOriginalFile(String OriginalFile) { this.OriginalFile = OriginalFile; }

    
    private String Issue;
    public String getIssue() { return Issue; }
    public void setIssue(String Issue) { this.Issue = Issue; }

    
    private String WesternDate;
    public String getWesternDate() { return WesternDate; }
    public void setWesternDate(String WesternDate) { this.WesternDate = WesternDate; }

    
    private String PersianDate;
    public String getPersianDate() { return PersianDate; }
    public void setPersianDate(String PersianDate) { this.PersianDate = PersianDate; }

    
    private String EnglishCategory;
    public String getEnglishCategory() { return EnglishCategory; }
    public void setEnglishCategory(String EnglishCategory) { this.EnglishCategory = EnglishCategory; }

    
    private String PersianCategory;
    public String getPersianCategory() { return PersianCategory; }
    public void setPersianCategory(String PersianCategory) { this.PersianCategory = PersianCategory; }

    
    private String Title;
    public String getTitle() { return Title; }
    public void setTitle(String Title) { this.Title = Title; }

    
    private String Body;
    public String getBody() { return Body; }
    public void setBody(String Body) { this.Body = Body; }
    
    @Override
    public String toString() {
        return String.format("Document:\n\tID:\t%s\n\tNumber:\t%s\n\tOriginalFile:\t%s\n\tIssue:\t%s\n\tWesternDate:\t%s\n\tPersianDate:\t%s\n\tEnglishCategory:\t%s\n\tPersianCategory:\t%s\n\tTitle:\t%s\n\tBody:\t%s", 
                ID,
                Number,
                OriginalFile,
                Issue,
                WesternDate,
                PersianDate,
                EnglishCategory,
                PersianCategory,
                Title,
                Body);
    }
}