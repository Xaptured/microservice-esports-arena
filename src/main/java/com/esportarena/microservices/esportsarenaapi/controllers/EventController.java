package com.esportarena.microservices.esportsarenaapi.controllers;

import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.*;
import com.esportarena.microservices.esportsarenaapi.services.EventService;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @Retry(name = "save-or-update-team-db-retry", fallbackMethod = "saveOrUpdateTeamDbRetry")
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

    public ResponseEntity<Team> saveOrUpdateTeamDbRetry(Team team, boolean isCreate, boolean isUpdate, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Team teamResponse = new Team();
        teamResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(teamResponse);
    }

    @Operation(
            summary = "Get team",
            description = "Get team with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-team/{name}")
    @Retry(name = "get-team-db-retry", fallbackMethod = "getTeamDbRetry")
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

    public ResponseEntity<Team> getTeamDbRetry(String name, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Team teamResponse = new Team();
        teamResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(teamResponse);
    }

    @Operation(
            summary = "Update team status",
            description = "Update team status with a message which defines whether the request is successful or not."
    )
    @PostMapping("/update-team-status")
    @Retry(name = "update-team-status-db-retry", fallbackMethod = "updateTeamStatusDbRetry")
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
            // TODO: Update team with the current status as soon as its updated
        } catch (ValidationException | DataBaseOperationException exception){
            response = exception.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<String> updateTeamStatusDbRetry(String teamName, String teamStatus, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        return ResponseEntity.status(HttpStatus.OK).body(StringConstants.FALLBACK_MESSAGE);
    }

    @Operation(
            summary = "Find upcoming events",
            description = "Find upcoming events with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-upcoming-events/{email}")
    @Retry(name = "find-upcoming-events-db-retry", fallbackMethod = "findUpcomingEventsDbRetry")
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

    public ResponseEntity<List<Event>> findUpcomingEventsDbRetry(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Event event = new Event();
        event.setMessage(StringConstants.FALLBACK_MESSAGE);
        List<Event> eventsResponse = new ArrayList<>();
        eventsResponse.add(event);
        return ResponseEntity.status(HttpStatus.OK).body(eventsResponse);
    }

    @Operation(
            summary = "Find upcoming active events with respect to interested games",
            description = "Find upcoming events with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-upcoming-events-interested-games/{email}")
    @Retry(name = "find-active-upcoming-events-wrt-interested-games-db-retry", fallbackMethod = "findActiveUpcomingEventsWrtInterestedGamesDbRetry")
    public ResponseEntity<List<Event>> findActiveUpcomingEventsWrtInterestedGames(@PathVariable String email) {
        List<Event> eventResults = null;
        try {
            if(StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            eventResults = service.findActiveUpcomingEventsWrtInterestedGames(email);
        } catch (DataBaseOperationException | MapperException | ValidationException exception) {
            eventResults = new ArrayList<>();
            Event event = new Event();
            event.setMessage(exception.getMessage());
            eventResults.add(event);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(eventResults);
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventResults);
    }

    public ResponseEntity<List<Event>> findActiveUpcomingEventsWrtInterestedGamesDbRetry(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Event event = new Event();
        event.setMessage(StringConstants.FALLBACK_MESSAGE);
        List<Event> eventsResponse = new ArrayList<>();
        eventsResponse.add(event);
        return ResponseEntity.status(HttpStatus.OK).body(eventsResponse);
    }

    @Operation(
            summary = "Get leaderboard",
            description = "Get leaderboard with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-leaderboard/{email}")
    @Retry(name = "find-leaderboard-db-retry", fallbackMethod = "findLeaderBoardDbRetry")
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

    public ResponseEntity<Leaderboard> findLeaderBoardDbRetry(Integer eventId, String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Leaderboard leaderboardResponse = new Leaderboard();
        leaderboardResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboardResponse);
    }

    @Operation(
            summary = "Get document",
            description = "Get document and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-document/{eventId}")
    @Retry(name = "find-leaderboard-doc-db-retry", fallbackMethod = "findDocDbRetry")
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

    public ResponseEntity<Document> findDocDbRetry(Integer eventId, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Document documentResponse = new Document();
        documentResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponse);
    }

    @Operation(
            summary = "Save OR Update events",
            description = "Save or Update events and gives the same event response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-event")
    @Retry(name = "save-or-update-event-db-retry", fallbackMethod = "saveOrUpdateEventDbRetry")
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

    public ResponseEntity<Event> saveOrUpdateEventDbRetry(Event event, boolean isCreate, boolean isUpdate, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Event eventResponse = new Event();
        eventResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(eventResponse);
    }

    @Operation(
            summary = "Get event",
            description = "Get event with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-event/{name}")
    @Retry(name = "get-event-db-retry", fallbackMethod = "getEventDbRetry")
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

    public ResponseEntity<Event> getEventDbRetry(String name, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Event eventResponse = new Event();
        eventResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(eventResponse);
    }

    @Operation(
            summary = "Save leaderboard",
            description = "Save leaderboard with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-leaderboard")
    @Retry(name = "save-leaderboard-db-retry", fallbackMethod = "saveLeaderboardDbRetry")
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
            // TODO: send email to organizer as leaderboard data has been saved for a particular event
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Leaderboard();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Leaderboard> saveLeaderboardDbRetry(Leaderboard leaderboard, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Leaderboard leaderboardResponse = new Leaderboard();
        leaderboardResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboardResponse);
    }

    @Operation(
            summary = "Save documents",
            description = "Save documents and gives the same documents response with a message which defines whether the request is successful or not."
    )
    @RequestMapping(path = "/save-documents/{eventId}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Retry(name = "save-leaderboard-document-db-retry", fallbackMethod = "saveLeaderboardDocumentDbRetry")
    public ResponseEntity<Leaderboard> saveLeaderboardDocument(@RequestPart MultipartFile doc, @PathVariable Integer eventId) {
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

    public ResponseEntity<Leaderboard> saveLeaderboardDocumentDbRetry(MultipartFile doc, Integer eventId, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Leaderboard leaderboardResponse = new Leaderboard();
        leaderboardResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(leaderboardResponse);
    }

    @Operation(
            summary = "Get team's document",
            description = "Get team's document and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/create-sheet/{eventId}")
    @Retry(name = "generate-excel-db-retry", fallbackMethod = "generateExcelDbRetry")
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

    public ResponseEntity<byte[]> generateExcelDbRetry(Integer eventId, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(
            summary = "Save viewer",
            description = "Save viewer with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-viewer")
    @Retry(name = "save-viewer-db-retry", fallbackMethod = "saveViewerDbRetry")
    public ResponseEntity<Viewer> saveViewer(@RequestBody Viewer viewer) {
        Viewer response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveViewer(viewer);
            // TODO: send email to viewer that he/she marked as viewer for the particular event
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            response = new Viewer();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Viewer> saveViewerDbRetry(Viewer viewer, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Viewer viewerResponse = new Viewer();
        viewerResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(viewerResponse);
    }

    @Operation(
            summary = "Is a viewer or not",
            description = "Is a viewer or not."
    )
    @GetMapping("/is-viewer")
    @Retry(name = "is-viewer-db-retry", fallbackMethod = "isViewerDbRetry")
    public ResponseEntity<Viewer> isViewer(@RequestParam String email, @RequestParam Integer eventId) {
        Viewer response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.isViewer(email, eventId);
        } catch (ValidationException | DataBaseOperationException exception){
            response = new Viewer();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Viewer> isViewerDbRetry(String email, Integer eventId, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Viewer viewerResponse = new Viewer();
        viewerResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(viewerResponse);
    }
}
