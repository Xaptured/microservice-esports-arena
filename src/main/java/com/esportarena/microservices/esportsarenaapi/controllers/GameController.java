package com.esportarena.microservices.esportsarenaapi.controllers;

import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Game;
import com.esportarena.microservices.esportsarenaapi.services.GameService;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Game", description = "Game management APIs")
@RestController
@RequestMapping("/games")
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService service;

    @Operation(
            summary = "Save or Update game",
            description = "Save or Update game and gives the same game response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-or-update-game")
    public ResponseEntity<Game> saveOrUpdateGame(@RequestBody Game game) {
        try {
            if(game == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            service.saveOrUpdateGame(game);
            game.setMessage(StringConstants.REQUEST_PROCESSED);
        } catch (MapperException | DataBaseOperationException | ValidationException exception) {
            game.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(game);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(game);
    }

    @Operation(
            summary = "Get all active games",
            description = "Get all active games with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-active-games")
    public ResponseEntity<List<Game>> findAllActiveGames() {
        List<Game> activeGames = null;
        try {
            activeGames = service.findAllActiveGames();
            if(activeGames == null) {
                Game game = new Game();
                game.setMessage(StringConstants.NO_ACTIVE_GAMES);
                activeGames = new ArrayList<>();
                activeGames.add(game);
            }
        } catch (MapperException | DataBaseOperationException exception) {
            activeGames = new ArrayList<>();
            Game game = new Game();
            game.setMessage(exception.getMessage());
            activeGames.add(game);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(activeGames);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(activeGames);
    }
}
