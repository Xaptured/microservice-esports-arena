package com.esportarena.microservices.esportsarenaapi.servicehelpers;

import com.esportarena.microservices.esportsarenaapi.enums.TeamStatus;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Event;
import com.esportarena.microservices.esportsarenaapi.models.Leaderboard;
import com.esportarena.microservices.esportsarenaapi.models.Team;
import com.esportarena.microservices.esportsarenaapi.models.Viewer;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EventServiceHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceHelper.class);

    public void checkTeamFromUI(Team team) throws ValidationException {
        if(team == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkTeamFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        checkUITeam(team);
    }

    private void checkUITeam(Team team) throws ValidationException {
        if(Strings.isNotEmpty(team.getName()) && Strings.isNotBlank(team.getName())){
            LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
        } else {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkUITeam for object: {}", team);
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }

    public void checkTeamFromDB(Team team) throws ValidationException {
        if(team == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkTeamFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
        }
    }

    public void checkStatusFromUI(String teamName, String status) throws ValidationException {
        if(StringUtils.isEmpty(teamName) || StringUtils.isBlank(teamName) || StringUtils.isBlank(status) || StringUtils.isEmpty(status)) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkStatusFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
        }
    }

    public void checkEventFromUI(Event event) throws ValidationException {
        if(event == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkEventFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        checkUIEvent(event);
    }

    private void checkUIEvent(Event event) throws ValidationException {
        if(Strings.isNotEmpty(event.getName()) && Strings.isNotBlank(event.getName())){
            LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
        } else {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkUIEvent for object: {}", event);
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
    }

    public void checkEventFromDB(Event event) throws ValidationException {
        if(event == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkEventFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
        }
    }

    public void checkUpComingEventsFromDB(List<Event> events) throws ValidationException {
        if(events.isEmpty()) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkUpComingEventsFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        checkDBUpcomingEvents(events);
    }

    private void checkDBUpcomingEvents(List<Event> events) throws ValidationException {
        for(Event event : events) {
            if(StringUtils.isBlank(event.getName()) || StringUtils.isEmpty(event.getName())) {
                LOGGER.error("Validation failed in EventServiceHelper.class : checkDBUpcomingEvents for object: null");
                throw new ValidationException(StringConstants.VALIDATION_ERROR);
            }
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public void checkLeaderboardFromUI(Leaderboard leaderboard) throws ValidationException {
        if(leaderboard == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkLeaderboardFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }

    public void checkLeaderboardFromDB(Leaderboard leaderboard) throws ValidationException {
        if(leaderboard == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkLeaderboardFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public void checkFileFromUI(MultipartFile file, Integer eventId) throws ValidationException {
        if(file.isEmpty() || eventId == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkFileFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }

    public void checkEventIdAndEmailFromUI(Integer eventId, String email) throws ValidationException {
        if(eventId == null || StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkEventIdAndEmailFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }

    public void checkEventIdAndEmailFromDB(Leaderboard leaderboard) throws ValidationException {
        if(leaderboard.getMessage().equals(StringConstants.ID_NOT_PRESENT)) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkEventIdAndEmailFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public void checkViewerFromUI(Viewer viewer) throws ValidationException {
        if(StringUtils.isBlank(viewer.getEmail()) || StringUtils.isEmpty(viewer.getEmail()) || viewer.getEventId() == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkViewerFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }

    public void checkViewerFromDB(Viewer viewer) throws ValidationException {
        if(StringUtils.isBlank(viewer.getMessage()) || StringUtils.isEmpty(viewer.getMessage())) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkViewerFromDB for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_DB);
    }

    public void checkEmailAndIdForViewerFromUI(String email, Integer eventId) throws ValidationException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email) || eventId == null) {
            LOGGER.error("Validation failed in EventServiceHelper.class : checkEmailAndIdForViewerFromUI for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        LOGGER.info(StringConstants.VALIDATION_PASSED_UI);
    }
}
