package com.esportarena.microservices.esportsarenaapi.controllers;

import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Document;
import com.esportarena.microservices.esportsarenaapi.models.Event;
import com.esportarena.microservices.esportsarenaapi.models.Leaderboard;
import com.esportarena.microservices.esportsarenaapi.models.Team;
import com.esportarena.microservices.esportsarenaapi.services.EventService;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Event", description = "Event management APIs")
@RestController
@RequestMapping("/events")
public class EventController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
    private boolean isRetryEnabled = false;
    @Autowired
    private EventService service;

    @Operation(
            summary = "Save OR Update teams",
            description = "Save or Update teams and gives the same team response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-team")
    public ResponseEntity<Team> saveOrUpdateTeam(@RequestBody Team team, @RequestParam boolean isCreate, @RequestParam boolean isUpdate) {
        Team response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveOrUpdateTeam(team, isCreate, isUpdate);
            // TODO: Send email to participant that successfully joined/created a team. Wait for approval
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            if(team == null){
                team = new Team();
            }
            team.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(team);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get team",
            description = "Get team with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-team/{name}")
    public ResponseEntity<Team> getTeam(@PathVariable String name) {
        Team response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.getTeam(name);
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Team();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Update team status",
            description = "Update team status with a message which defines whether the request is successful or not."
    )
    @PostMapping("/update-team-status")
    public ResponseEntity<String> updateTeamStatus(@RequestParam String teamName, @RequestParam String teamStatus) {
        String response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.updateTeamStatus(teamName, teamStatus);
        } catch (ValidationException | DataBaseOperationException exception){
            response = exception.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Find upcoming events",
            description = "Find upcoming events with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-upcoming-events/{email}")
    public ResponseEntity<List<Event>> findUpcomingEvents(@PathVariable String email) {
        List<Event> response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.findUpcomingEvents(email);
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new ArrayList<>();
            Event event = new Event();
            event.setMessage(exception.getMessage());
            response.add(event);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Get leaderboard",
            description = "Get leaderboard with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-leaderboard/{email}")
    public ResponseEntity<Leaderboard> findLeaderBoard(@RequestParam Integer eventId, @PathVariable String email) {
        Leaderboard response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.findLeaderboard(eventId, email);
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Leaderboard();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Get document",
            description = "Get document and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-document/{eventId}")
    public ResponseEntity<Document> findDoc(@PathVariable Integer eventId) {
        Document response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.findLeaderboardDocument(eventId);
        } catch (ValidationException | MapperException | DataBaseOperationException | IOException exception){
            response = new Document();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Save OR Update events",
            description = "Save or Update events and gives the same event response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-event")
    public ResponseEntity<Event> saveOrUpdateEvent(@RequestBody Event event, @RequestParam boolean isCreate, @RequestParam boolean isUpdate) {
        Event response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveOrUpdateEvent(event, isCreate, isUpdate);
            // TODO: Send email to organizer that successfully created an event. Wait for approval
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            if(event == null){
                event = new Event();
            }
            event.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(event);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get event",
            description = "Get event with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-event/{name}")
    public ResponseEntity<Event> getEvent(@PathVariable String name) {
        Event response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.getEvent(name);
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Event();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Save leaderboard",
            description = "Save leaderboard with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-leaderboard")
    public ResponseEntity<Leaderboard> saveLeaderboard(@RequestBody Leaderboard leaderboard) {
        Leaderboard response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveLeaderboard(leaderboard);
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Leaderboard();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Save documents",
            description = "Save documents and gives the same documents response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-documents/{eventId}")
    public ResponseEntity<Leaderboard> saveLeaderboardDocument(@RequestParam MultipartFile doc, @PathVariable Integer eventId) {
        Leaderboard response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveLeaderboardDocument(doc, eventId);
        } catch (ValidationException | MapperException | DataBaseOperationException | IOException exception){
            response = new Leaderboard();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get team's document",
            description = "Get team's document and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/create-sheet/{eventId}")
    public ResponseEntity<byte[]> generateExcel(@PathVariable Integer eventId) {
        byte[] response = null;
        if(isRetryEnabled){
            LOGGER.info(StringConstants.RETRY_MESSAGE);
        }
        if(!isRetryEnabled){
            isRetryEnabled = true;
        }
        response = service.generateExcelSheetForTeams(eventId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
