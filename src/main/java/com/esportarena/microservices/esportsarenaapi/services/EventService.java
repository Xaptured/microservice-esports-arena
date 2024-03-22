package com.esportarena.microservices.esportsarenaapi.services;

import com.esportarena.microservices.esportsarenaapi.clients.TheJackFolioDBClient;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.*;
import com.esportarena.microservices.esportsarenaapi.servicehelpers.EventServiceHelper;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
    @Autowired
    private EventServiceHelper validation;
    @Autowired
    private TheJackFolioDBClient dbClient;

    public Team saveOrUpdateTeam(Team team, boolean isCreate, boolean isUpdate) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkTeamFromUI(team);
        ResponseEntity<Team> response = dbClient.saveOrUpdateTeam(team, isCreate, isUpdate);
        Team responseBody = response.getBody();
        validation.checkTeamFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Team getTeam(String name) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(name) || StringUtils.isEmpty(name)) {
            LOGGER.error("Validation failed in EventService.class : getTeam for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Team> response = dbClient.getTeam(name);
            Team responseBody = response.getBody();
            validation.checkTeamFromDB(responseBody);
            if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                throw new DataBaseOperationException(responseBody.getMessage());
            }
            else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                throw new MapperException(responseBody.getMessage());
            }
            return responseBody;
        }
    }

    public String updateTeamStatus(String teamName, String status) throws ValidationException, DataBaseOperationException {
        validation.checkStatusFromUI(teamName, status);
        ResponseEntity<String> response = dbClient.updateTeamStatus(teamName, status);
        String responseBody = response.getBody();
        if(StringUtils.isNotEmpty(responseBody) && StringUtils.isNotBlank(responseBody) && responseBody.equals(StringConstants.DATABASE_ERROR)) {
            throw new DataBaseOperationException(responseBody);
        } else if(StringUtils.isNotEmpty(responseBody) && StringUtils.isNotBlank(responseBody) && responseBody.equals(StringConstants.REQUEST_PROCESSED)) {
            return responseBody;
        } else {
            return null;
        }
    }

    public Leaderboard findLeaderboard(Integer eventId, String email) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkEventIdAndEmailFromUI(eventId, email);
        ResponseEntity<Leaderboard> response = dbClient.findLeaderBoard(eventId, email);
        Leaderboard responseBody = response.getBody();
        validation.checkEventIdAndEmailFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Document findLeaderboardDocument(Integer eventId) throws ValidationException, DataBaseOperationException, MapperException, IOException {
        if(eventId == null) {
            LOGGER.error("Validation failed in EventService.class : findLeaderboardDocument for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        ResponseEntity<Document> response = dbClient.findDoc(eventId);
        Document responseBody = response.getBody();
        if(responseBody.getMessage().equals(StringConstants.ID_NOT_PRESENT)) {
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        } else if(!responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED)) {
            throw new IOException(StringConstants.IO_ERROR);
        } else {
            return responseBody;
        }
    }

    public List<Event> findUpcomingEvents(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findUpcomingEvents for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findUpcomingEvents(email);
            List<Event> responseBody = response.getBody();
            validation.checkUpComingEventsFromDB(responseBody);
            if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                throw new DataBaseOperationException(responseBody.get(0).getMessage());
            } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                throw new MapperException(responseBody.get(0).getMessage());
            }
            return responseBody;
        }
    }

    public List<Event> findActiveUpcomingEventsWrtInterestedGames(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findActiveUpcomingEventsWrtInterestedGames for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findActiveUpcomingEventsWrtInterestedGames(email);
            List<Event> responseBody = response.getBody();
            validation.checkUpComingEventsWrtIntGamesFromDB(responseBody);
            if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                throw new DataBaseOperationException(responseBody.get(0).getMessage());
            } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                throw new MapperException(responseBody.get(0).getMessage());
            }
            return responseBody;
        }
    }

    public List<Event> findAllUpcomingOrganizerEvents(String email) throws ValidationException, DataBaseOperationException, MapperException {
        List<Event> responseBody = null;
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findAllUpcomingOrganizerEvents for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findAllUpcomingOrganizerEvents(email);
            if(response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
                validation.checkUpComingEventsWrtIntGamesFromDB(responseBody);
                return responseBody;
            } else {
                responseBody = response.getBody();
                if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                    throw new DataBaseOperationException(responseBody.get(0).getMessage());
                } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                    throw new MapperException(responseBody.get(0).getMessage());
                }
            }
        }
        return responseBody;
    }

    public List<Event> findAllLeaderboardCompleteOrganizerEvents(String email) throws ValidationException, DataBaseOperationException, MapperException {
        List<Event> responseBody = null;
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findAllUpcomingOrganizerEvents for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findAllLeaderboardCompleteOrganizerEvents(email);
            if(response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
                validation.checkUpComingEventsWrtIntGamesFromDB(responseBody);
                return responseBody;
            } else {
                responseBody = response.getBody();
                if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                    throw new DataBaseOperationException(responseBody.get(0).getMessage());
                } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                    throw new MapperException(responseBody.get(0).getMessage());
                }
            }
        }
        return responseBody;
    }

    public List<Event> findOnlyActiveOrganizerEvents(String email) throws ValidationException, DataBaseOperationException, MapperException {
        List<Event> responseBody = null;
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findAllUpcomingOrganizerEvents for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findOnlyActiveOrganizerEvents(email);
            if(response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
                validation.checkUpComingEventsWrtIntGamesFromDB(responseBody);
                return responseBody;
            } else {
                responseBody = response.getBody();
                if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                    throw new DataBaseOperationException(responseBody.get(0).getMessage());
                } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                    throw new MapperException(responseBody.get(0).getMessage());
                }
            }
        }
        return responseBody;
    }

    public List<Event> findAllLeaderboardCompleteParticipantEvents(String email) throws ValidationException, DataBaseOperationException, MapperException {
        List<Event> responseBody = null;
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in EventService.class : findAllUpcomingOrganizerEvents for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<Event>> response = dbClient.findAllLeaderboardCompleteParticipantEvents(email);
            if(response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
                validation.checkUpComingEventsWrtIntGamesFromDB(responseBody);
                return responseBody;
            } else {
                responseBody = response.getBody();
                if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.DATABASE_ERROR)) {
                    throw new DataBaseOperationException(responseBody.get(0).getMessage());
                } else if(responseBody.size() == 1 && StringUtils.isNotEmpty(responseBody.get(0).getMessage()) && StringUtils.isNotBlank(responseBody.get(0).getMessage()) && responseBody.get(0).getMessage().equals(StringConstants.MAPPING_ERROR)) {
                    throw new MapperException(responseBody.get(0).getMessage());
                }
            }
        }
        return responseBody;
    }

    public Event saveOrUpdateEvent(Event event, boolean isCreate, boolean isUpdate) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkEventFromUI(event);
        ResponseEntity<Event> response = dbClient.saveOrUpdateEvent(event, isCreate, isUpdate);
        Event responseBody = response.getBody();
        validation.checkEventFromDB(event);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Event getEvent(String name) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(name) || StringUtils.isEmpty(name)) {
            LOGGER.error("Validation failed in EventService.class : getEvent for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Event> response = dbClient.getEvent(name);
            Event responseBody = response.getBody();
            validation.checkEventFromDB(responseBody);
            if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                throw new DataBaseOperationException(responseBody.getMessage());
            }
            else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                throw new MapperException(responseBody.getMessage());
            }
            return responseBody;
        }
    }

    public Integer getEventId(String name) throws ValidationException {
        if(StringUtils.isBlank(name) || StringUtils.isEmpty(name)) {
            LOGGER.error("Validation failed in EventService.class : getEventId for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Integer> response = dbClient.getEventId(name);
            Integer responseBody = response.getBody();
            return responseBody;
        }
    }

    public Boolean isRegisteredInEvent(Integer eventId, String eventName, String email) throws ValidationException {
        if(eventId == null && (StringUtils.isBlank(eventName) || StringUtils.isEmpty(eventName))) {
            LOGGER.error("Validation failed in EventService.class : isRegisteredInEvent for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Boolean> response = dbClient.isRegisteredInEvent(eventId, eventName, email);
            Boolean responseBody = response.getBody();
            return responseBody;
        }
    }

    public List<ProfileDetail> getTeamDetailsForEvent(Integer eventId, String eventName, String email) throws ValidationException {
        validation.checkEventIdEmailEventNameFromUI(eventId, email, eventName);
        ResponseEntity<List<ProfileDetail>> response = dbClient.getTeamDetailsForEvent(eventId, eventName, email);
        if(response.getStatusCode().is2xxSuccessful()) {
            List<ProfileDetail> responseBody = response.getBody();
            return responseBody;
        } else {
            throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
        }
    }

    public Integer remainingPlayersPerSlotCount(Integer eventId, String eventName, String email) throws ValidationException {
        validation.checkEventIdEmailEventNameFromUI(eventId, email, eventName);
        ResponseEntity<Integer> response = dbClient.remainingPlayersPerSlotCount(eventId, eventName, email);
        if(response.getStatusCode().is2xxSuccessful()) {
            Integer responseBody = response.getBody();
            return responseBody;
        } else {
            throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
        }
    }

    public List<TeamWithCount> getTeamsWithCount(Integer eventId, String eventName) throws ValidationException {
        validation.checkEventIdAndEmailFromUI(eventId, eventName);
        ResponseEntity<List<TeamWithCount>> response = dbClient.getTeamsWithCount(eventId, eventName);
        if(response.getStatusCode().is2xxSuccessful()) {
            List<TeamWithCount> responseBody = response.getBody();
            return responseBody;
        } else {
            throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
        }
    }

    public Leaderboard saveLeaderboard(Leaderboard leaderboard) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkLeaderboardFromUI(leaderboard);
        ResponseEntity<Leaderboard> response = dbClient.saveLeaderboard(leaderboard);
        Leaderboard responseBody = response.getBody();
        validation.checkLeaderboardFromDB(leaderboard);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Leaderboard saveLeaderboardDocument(MultipartFile file, Integer eventId) throws ValidationException, DataBaseOperationException, MapperException, IOException {
        validation.checkFileFromUI(file, eventId);
        ResponseEntity<Leaderboard> response = dbClient.saveLeaderboardDocument(file, eventId);
        Leaderboard responseBody = response.getBody();
        validation.checkLeaderboardFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
            throw new ValidationException(responseBody.getMessage());
        } else if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        } else if(!responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED)) {
            throw new IOException(StringConstants.IO_ERROR);
        } else {
            return responseBody;
        }
    }

    public boolean isLeaderboardComplete(Integer eventId) throws ValidationException {
        if(eventId == null) {
            LOGGER.error("Validation failed in EventService.class : isLeaderboardComplete for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Boolean> response = dbClient.isLeaderboardComplete(eventId);
            if(response.getStatusCode().is2xxSuccessful()) {
                return Boolean.TRUE.equals(response.getBody());
            } else {
                throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
            }
        }
    }

    public byte[] generateExcelSheetForTeams(Integer eventId) {
        if(eventId != null) {
            ResponseEntity<byte[]> response = dbClient.generateExcel(eventId);
            return response.getBody();
        } else {
            return null;
        }
    }

    public List<TeamWithPoints> findTeamsWithPoints(Integer eventId) throws ValidationException {
        if(eventId == null) {
            LOGGER.error("Validation failed in EventService.class : isLeaderboardComplete for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<List<TeamWithPoints>> response  = dbClient.findTeamsWithPoints(eventId);
            if(response.getStatusCode().is2xxSuccessful()) {
                List<TeamWithPoints> responseBody = response.getBody();
                return responseBody;
            } else {
                throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
            }
        }
    }

    public Viewer saveViewer(Viewer viewer) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkViewerFromUI(viewer);
        ResponseEntity<Viewer> response = dbClient.saveViewer(viewer);
        Viewer responseBody = response.getBody();
        validation.checkViewerFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Viewer isViewer(String email, Integer eventId) throws ValidationException, DataBaseOperationException {
        validation.checkEventIdAndEmailFromUI(eventId, email);
        ResponseEntity<Viewer> response = dbClient.isViewer(email, eventId);
        Viewer responseBody = response.getBody();
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        return responseBody;
    }
}
