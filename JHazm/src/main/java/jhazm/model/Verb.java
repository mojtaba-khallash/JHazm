package jhazm.model;

/**
 * Created by Mojtaba Khallash on 30/10/2015.
 */
public class Verb {
    public String PastLightVerb;
    public String PresentLightVerb;
    public String Prefix;
    public String NonVerbalElement;
    public String Preposition;
    public String Valency;

    public Verb(String PastLightVerb, String PresentLightVerb, String Prefix,
                String NonVerbalElement, String Preposition, String Valency) {
        this.PastLightVerb = PastLightVerb;
        this.PresentLightVerb = PresentLightVerb;
        this.Prefix = Prefix;
        this.NonVerbalElement = NonVerbalElement;
        this.Preposition = Preposition;
        this.Valency = Valency;
    }
}
