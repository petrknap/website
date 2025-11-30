package cz.petrknap.website.backups;

import io.github.petrknap.persistence.zoneddatetime.UtcDateTimeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.ZonedDateTime;

@Entity
@Table(name = "backups")
public class Metadata {
    @Id
    private String identifier;
    @Convert(converter = UtcDateTimeConverter.class)
    private ZonedDateTime refreshedAt;
    @Column(nullable = false)
    @Min(1)
    private Integer freshForHours;

    protected Metadata() {
    }

    public Metadata(String identifier, Integer freshForHours) {
        this.identifier = identifier;
        setFreshForHours(freshForHours);
    }

    public String getId() {
        return getIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }

    public ZonedDateTime getRefreshedAt() {
        return refreshedAt;
    }

    public void setFreshForHours(Integer freshForHours) {
        this.freshForHours = freshForHours;
    }

    public Integer getFreshForHours() {
        return freshForHours;
    }

    public void refresh() {
        refreshedAt = ZonedDateTime.now();
    }

    public boolean isFresh() {
        return refreshedAt != null && ZonedDateTime.now().minusHours(freshForHours).isBefore(refreshedAt);
    }
}
