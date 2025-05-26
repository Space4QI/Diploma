package org.example.Dto;

import java.time.LocalDate;

public class EventCountByDateDTO {
    private LocalDate date;
    private Long count;

    public EventCountByDateDTO(java.sql.Date sqlDate, Long count) {
        this.date = (sqlDate != null) ? sqlDate.toLocalDate() : null;
        this.count = count;
    }

    public EventCountByDateDTO() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
