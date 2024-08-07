package com.esportarena.microservices.esportsarenaapi.clients;

import com.esportarena.microservices.esportsarenaapi.models.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "DATABASE-SERVICE")
public interface TheJackFolioDBClient {

    @PostMapping("/clients/save-profile")
    public ResponseEntity<ProfileDetail> saveOrUpdateProfileDetails(@RequestBody ProfileDetail details);

    @GetMapping("/clients/get-profile/{email}")
    public ResponseEntity<ProfileDetail> getProfileDetails(@PathVariable String email);

    @GetMapping("/clients/is-profile-present/{email}")
    public ResponseEntity<Boolean> isProfilePresent(@PathVariable String email);

    @PostMapping("/events/save-team")
    public ResponseEntity<Team> saveOrUpdateTeam(@RequestBody Team team, @RequestParam boolean isCreate, @RequestParam boolean isUpdate);

    @GetMapping("/events/get-team/{name}")
    public ResponseEntity<Team> getTeam(@PathVariable String name);

    @PostMapping("/events/save-event")
    public ResponseEntity<Event> saveOrUpdateEvent(@RequestBody Event event, @RequestParam boolean isCreate, @RequestParam boolean isUpdate);

    @GetMapping("/events/get-event/{name}")
    public ResponseEntity<Event> getEvent(@PathVariable String name);

    @GetMapping("/events/get-event-id/{name}")
    public ResponseEntity<Integer> getEventId(@PathVariable String name);

    @GetMapping("/events/is-registered")
    public ResponseEntity<Boolean> isRegisteredInEvent(@RequestParam Integer eventId, @RequestParam String eventName, @RequestParam String email);

    @GetMapping("/events/get-team-details-for-event")
    public ResponseEntity<List<ProfileDetail>> getTeamDetailsForEvent(@RequestParam Integer eventId, @RequestParam String eventName, @RequestParam String email);

    @GetMapping("/events/get-team-for-event")
    public ResponseEntity<Team> getTeamWithEventIDAndEmail(@RequestParam Integer eventId, @RequestParam String eventName, @RequestParam String email);

    @GetMapping("/events/get-remaining-players-per-slot")
    public ResponseEntity<Integer> remainingPlayersPerSlotCount(@RequestParam Integer eventId, @RequestParam String eventName, @RequestParam String email);

    @GetMapping("/events/get-teams-with-count")
    public ResponseEntity<List<TeamWithCount>> getTeamsWithCount(@RequestParam Integer eventId, @RequestParam String eventName);

    @GetMapping("/events/get-teams-with-id")
    public ResponseEntity<List<TeamWithID>> getTeamsWithIDs(@RequestParam Integer eventId);

    @GetMapping("/events/get-upcoming-events/{email}")
    public ResponseEntity<List<Event>> findUpcomingEvents(@PathVariable String email);

    @GetMapping("/events/get-upcoming-events-interested-games/{email}")
    public ResponseEntity<List<Event>> findActiveUpcomingEventsWrtInterestedGames(@PathVariable String email);

    @GetMapping("/events/get-upcoming-organizer-events/{email}")
    public ResponseEntity<List<Event>> findAllUpcomingOrganizerEvents(@PathVariable String email);

    @GetMapping("/events/get-completed-events-participant/{email}")
    public ResponseEntity<List<Event>> findAllLeaderboardCompleteParticipantEvents(@PathVariable String email);

    @GetMapping("/events/get-completed-events-organizer/{email}")
    public ResponseEntity<List<Event>> findAllLeaderboardCompleteOrganizerEvents(@PathVariable String email);

    @GetMapping("/events/get-only-active-events-organizer/{email}")
    public ResponseEntity<List<Event>> findOnlyActiveOrganizerEvents(@PathVariable String email);

    @PostMapping("/events/update-team-status")
    public ResponseEntity<String> updateTeamStatus(@RequestParam String teamName, @RequestParam String teamStatus);

    @PostMapping("/events/save-leaderboard")
    public ResponseEntity<Leaderboard> saveLeaderboard(@RequestBody Leaderboard leaderboard);

    @RequestMapping(path = "/events/save-documents/{eventId}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Leaderboard> saveLeaderboardDocument(@RequestPart MultipartFile doc, @PathVariable Integer eventId);

    @GetMapping("/events/is-leaderboard-complete/{eventId}")
    public ResponseEntity<Boolean> isLeaderboardComplete(@PathVariable Integer eventId);

    @GetMapping("/events/create-sheet/{eventId}")
    public ResponseEntity<byte[]> generateExcel(@PathVariable Integer eventId);

    @GetMapping("/events/get-teams-with-points/{eventId}")
    public ResponseEntity<List<TeamWithPoints>> findTeamsWithPoints(@PathVariable Integer eventId);

    @GetMapping("/events/get-leaderboard/{email}")
    public ResponseEntity<Leaderboard> findLeaderBoard(@RequestParam Integer eventId, @PathVariable String email);

    @GetMapping("/events/get-document/{eventId}")
    public ResponseEntity<Document> findDoc(@PathVariable Integer eventId);

    @PostMapping("/events/save-viewer")
    public ResponseEntity<Viewer> saveViewer(@RequestBody Viewer viewer);

    @GetMapping("/events/is-viewer")
    public ResponseEntity<Viewer> isViewer(@RequestParam String email, @RequestParam Integer eventId);

    @PostMapping("/clients/save-partner")
    public ResponseEntity<Partner> saveOrUpdatePartner(@RequestBody Partner partner);

    @GetMapping("/clients/get-partner/{email}")
    public ResponseEntity<Partner> findPartner(@PathVariable String email);

    @RequestMapping(path = "/clients/save-documents/{email}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Partner> saveDocuments(@RequestPart MultipartFile image, @RequestPart MultipartFile doc, @PathVariable String email);

    @GetMapping("/clients/get-logo/{email}")
    public ResponseEntity<Document> findLogo(@PathVariable String email);

    @GetMapping("/clients/get-document/{email}")
    public ResponseEntity<Document> findDoc(@PathVariable String email);

    @GetMapping("/events/get-today-events")
    public ResponseEntity<List<Event>> findEventsScheduledForToday();

    @PostMapping("/events/update-event-status")
    public ResponseEntity<String> updateEventStatus(@RequestParam String status, @RequestParam String eventName);

    @PostMapping("/games/save-or-update-game")
    public ResponseEntity<Game> saveOrUpdateGame(@RequestBody Game game);

    @GetMapping("/games/get-active-games")
    public ResponseEntity<List<Game>> findAllActiveGames();

    @GetMapping("/games/get-interested-games/{email}")
    public ResponseEntity<List<InterestedGame>> findAllInterestedGamesForUser(@PathVariable String email);
}