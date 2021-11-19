package pl.piotrrokita.TravelOrganizer.model;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
public class Audit {

    private LocalDateTime createTime;
    private LocalDateTime lastEditTime;

    @PrePersist
    void prePersist() {
        createTime = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        lastEditTime = LocalDateTime.now();
    }
}
