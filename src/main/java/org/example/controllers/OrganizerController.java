package org.example.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizer")
//@PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
public class OrganizerController {

    @PostMapping("/create-event")
    public String createEvent() {
        return "Организатор создал мероприятие";
    }

    @PutMapping("/update-event")
    public String updateEvent() {
        return "Организатор обновил мероприятие";
    }

    @PostMapping("/join-team")
    public String joinTeam() {
        return "Организатор вступил в команду";
    }
}