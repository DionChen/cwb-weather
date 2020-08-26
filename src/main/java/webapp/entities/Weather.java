package webapp.entities;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name ="cwbdata")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="location")
    private String location;
    @Column(name="maxtemp")
    private Integer maxtemp;
    @Column(name="mintemp")
    private Integer mintemp;
    @Column(name="pop")
    private Integer pop;
    @Column(name="starttime")
    private Timestamp starttime;
    @Column(name="endtime")
    private Timestamp endtime;
    @Column(name="wx")
    private String wx;
    
    public Weather() {
        super();
    }
    public Weather(String location,Integer maxtemp,Integer mintemp,Integer pop,Timestamp starttime,Timestamp endtime,String wx) {
        super();
        this.location = location;
        this.maxtemp = maxtemp;
        this.mintemp = mintemp;
        this.pop    = pop;
        this.starttime = starttime;
        this.endtime = endtime;
        this.wx = wx;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id=id;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getMaxtemp() {
        return maxtemp;
    }
    public void setMaxtemp(Integer maxtemp) {
        this.maxtemp =maxtemp;
    }
    public Integer getMintemp() {
        return mintemp;
    }
    public void setMintemp(Integer mintemp) {
        this.mintemp = mintemp;
    }
    public Integer getPop() {
        return pop;
    }
    public void setPop(Integer pop) {
        this.pop =pop;
    }
    public Timestamp getStarttime() {
        return starttime;
    }
    public void setStartTime(Timestamp starttime) {
        this.starttime = starttime;
    }
    public Timestamp getEndTime() {
        return endtime;
    }
    public void setEndTime(Timestamp endtime) {
        this.endtime = endtime;
    }
    public String getWx() {
        return wx;
    }
    public void setWx(String wx) {
        this.wx = wx;
    }
}