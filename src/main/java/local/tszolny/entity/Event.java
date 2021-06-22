package local.tszolny.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Events")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    @Column(name = "id")
    private String id;
    @Transient
    private String state;
    @Transient
    private long timestamp;
    @Column(name = "type")
    private String type;
    @Column(name = "host")
    private String host;
    @Column(name = "duration")
    private long duration;
    @Column(name = "alert")
    private boolean alert;

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    @Override
    public String toString() {
        return "Event{" +
                "recordId=" + recordId +
                ", id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", duration=" + duration +
                ", alert=" + alert +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return timestamp == event.timestamp &&
                duration == event.duration &&
                alert == event.alert &&
                Objects.equals(id, event.id) &&
                Objects.equals(state, event.state) &&
                Objects.equals(type, event.type) &&
                Objects.equals(host, event.host);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, state, timestamp, type, host, duration, alert);
    }
}
