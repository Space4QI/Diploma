package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizer")
@Tag(name = "Organizer", description = "Actions available to organizers")
public class OrganizerController {

    @PostMapping("/create-event")
    @Operation(
            summary = "Create an event",
            description = "Allows an organizer to create a new event",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event created successfully",
                            content = @Content(mediaType = "text/plain"))
            }
    )
    public String createEvent() {
        return "Организатор создал мероприятие";
    }

    @PutMapping("/update-event")
    @Operation(
            summary = "Update an event",
            description = "Allows an organizer to update an existing event",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Event updated successfully",
                            content = @Content(mediaType = "text/plain"))
            }
    )
    public String updateEvent() {
        return "Организатор обновил мероприятие";
    }

    @PostMapping("/join-team")
    @Operation(
            summary = "Join a team",
            description = "Allows an organizer to join a team",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Team joined successfully",
                            content = @Content(mediaType = "text/plain"))
            }
    )
    public String joinTeam() {
        return "Организатор вступил в команду";
    }
}
