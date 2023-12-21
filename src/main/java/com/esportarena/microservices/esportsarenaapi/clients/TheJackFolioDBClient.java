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

    @PostMapping("/events/save-team")
    public ResponseEntity<Team> saveOrUpdateTeam(@RequestBody Team team, @RequestParam boolean isCreate, @RequestParam boolean isUpdate);

    @GetMapping("/events/get-team/{name}")
    public ResponseEntity<Team> getTeam(@PathVariable String name);

    @PostMapping("/events/save-event")
    public ResponseEntity<Event> saveOrUpdateEvent(@RequestBody Event event, @RequestParam boolean isCreate, @RequestParam boolean isUpdate);

    @GetMapping("/events/get-event/{name}")
    public ResponseEntity<Event> getEvent(@PathVariable String name);

    @GetMapping("/events/get-upcoming-events/{email}")
    public ResponseEntity<List<Event>> findUpcomingEvents(@PathVariable String email);

    @PostMapping("/events/update-team-status")
    public ResponseEntity<String> updateTeamStatus(@RequestParam String teamName, @RequestParam String teamStatus);

    @PostMapping("/events/save-leaderboard")
    public ResponseEntity<Leaderboard> saveLeaderboard(@RequestBody Leaderboard leaderboard);

    @RequestMapping(path = "/events/save-documents/{eventId}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Leaderboard> saveLeaderboardDocument(@RequestPart MultipartFile doc, @PathVariable Integer eventId);

    @GetMapping("/events/create-sheet/{eventId}")
    public ResponseEntity<byte[]> generateExcel(@PathVariable Integer eventId);

    @GetMapping("/events/get-leaderboard/{email}")
    public ResponseEntity<Leaderboard> findLeaderBoard(@RequestParam Integer eventId, @PathVariable String email);

    @GetMapping("/events/get-document/{eventId}")
    public ResponseEntity<Document> findDoc(@PathVariable Integer eventId);
}