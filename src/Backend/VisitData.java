package Backend;

import java.util.Date;


/**
 * Created by Tom on 4/8/15.
 */
public class VisitData{
    private Date visitDate;
    private Date visitTime;
    private String reason;
    private boolean followUp;
    private String email;
    private String specialist;
    private String location;
    private String firstName;
    private String lastName;

    public VisitData(Date visitDate, Date visitTime, String reason, boolean followUp, String email, String specialist, String location, String firstName, String lastName){
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.reason = reason;
        this.followUp = followUp;
        this.email = email;
        this.specialist = specialist;
        this.location = location;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Date getVisitDate(){
        return visitDate;
    }

    public Date getVisitTime(){
        return visitTime;
    }

    public String getReason(){
        return reason;
    }

    public boolean isFollowUp(){
        return followUp;
    }

    public String getEmail(){
        return email;
    }

    public String getSpecialist(){
        return specialist;
    }

    public String getLocation(){
        return location;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
}
