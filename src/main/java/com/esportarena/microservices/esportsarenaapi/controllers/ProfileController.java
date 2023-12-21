package com.esportarena.microservices.esportsarenaapi.controllers;

import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.services.ProfileService;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profile", description = "Profile management APIs")
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
    private boolean isRetryEnabled = false;
    @Autowired
    private ProfileService service;

    @Operation(
            summary = "Save profile",
            description = "Save profile and gives the same profile response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-profile")
    public ResponseEntity<ProfileDetail> saveOrUpdateProfileDetails(@RequestBody ProfileDetail details) {
        ProfileDetail response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveOrUpdateProfileDetails(details);
            // TODO: Send email to client that successfully saved profile
        } catch (ValidationException | MapperException | DataBaseOperationException exception){
            if(details == null){
                details = new ProfileDetail();
            }
            details.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get profile",
            description = "Get profile and gives the same profile response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-profile/{email}")
    public ResponseEntity<ProfileDetail> getProfileDetails(@PathVariable String email) {
        ProfileDetail response = null;
        try{
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.getProfileDetails(email);
        } catch (ValidationException | MapperException | DataBaseOperationException exception) {
            response = new ProfileDetail();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
