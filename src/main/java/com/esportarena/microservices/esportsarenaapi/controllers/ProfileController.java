package com.esportarena.microservices.esportsarenaapi.controllers;

import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Document;
import com.esportarena.microservices.esportsarenaapi.models.Partner;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.models.Team;
import com.esportarena.microservices.esportsarenaapi.services.ProfileService;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.github.resilience4j.retry.annotation.Retry;
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

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    @Retry(name = "save-or-update-profile-details-db-retry", fallbackMethod = "saveOrUpdateProfileDetailsDbRetry")
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

    public ResponseEntity<ProfileDetail> saveOrUpdateProfileDetailsDbRetry(ProfileDetail details, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        ProfileDetail detailResponse = new ProfileDetail();
        detailResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(detailResponse);
    }

    @Operation(
            summary = "Get profile",
            description = "Get profile and gives the same profile response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-profile/{email}")
    @Retry(name = "get-profile-details-db-retry", fallbackMethod = "getProfileDetailsDbRetry")
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

    public ResponseEntity<ProfileDetail> getProfileDetailsDbRetry(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        ProfileDetail detailResponse = new ProfileDetail();
        detailResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(detailResponse);
    }

    @Operation(
            summary = "Save partner",
            description = "Save partner and gives the same partner response with a message which defines whether the request is successful or not."
    )
    @PostMapping("/save-partner")
    @Retry(name = "save-or-update-partner-db-retry", fallbackMethod = "saveOrUpdatePartnerDbRetry")
    public ResponseEntity<Partner> saveOrUpdatePartner(@RequestBody Partner partner) {
        Partner response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveOrUpdatePartner(partner);
        } catch (ValidationException | MapperException | DataBaseOperationException | FileNotFoundException exception) {
            response = new Partner();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Partner> saveOrUpdatePartnerDbRetry(Partner partner, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Partner partnerResponse = new Partner();
        partnerResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(partnerResponse);
    }

    @Operation(
            summary = "Get partner",
            description = "Get partner and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-partner/{email}")
    @Retry(name = "find-partner-db-retry", fallbackMethod = "findPartnerDbRetry")
    public ResponseEntity<Partner> findPartner(@PathVariable String email) {
        Partner response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.findPartner(email);
            if(response.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ValidationException | MapperException | DataBaseOperationException exception) {
            response = new Partner();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Partner> findPartner(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Partner partnerResponse = new Partner();
        partnerResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(partnerResponse);
    }

    @Operation(
            summary = "Save documents",
            description = "Save documents and gives the same documents response with a message which defines whether the request is successful or not."
    )
    @RequestMapping(path = "/save-documents/{email}", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Retry(name = "save-documents-db-retry", fallbackMethod = "saveDocumentsDbRetry")
    public ResponseEntity<Partner> saveDocuments(@RequestPart MultipartFile image, @RequestPart MultipartFile doc, @PathVariable String email) {
        Partner response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.saveDocument(image, doc, email);
            if(response.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (ValidationException | DataBaseOperationException | MapperException | IOException exception) {
            response = new Partner();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Partner> saveDocumentsDbRetry(MultipartFile image, MultipartFile doc, String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Partner partnerResponse = new Partner();
        partnerResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(partnerResponse);
    }

    @Operation(
            summary = "Get logo",
            description = "Get logo and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-logo/{email}")
    @Retry(name = "find-logo-db-retry", fallbackMethod = "findLogoDbRetry")
    public ResponseEntity<Document> findLogo(@PathVariable String email) {
        Document response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.getLogo(email);
            if(response.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (DataBaseOperationException | IOException | ValidationException exception) {
            response = new Document();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Document> findLogoDbRetry(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Document documentResponse = new Document();
        documentResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponse);
    }

    @Operation(
            summary = "Get document",
            description = "Get document and gives the response with a message which defines whether the request is successful or not."
    )
    @GetMapping("/get-document/{email}")
    @Retry(name = "find-doc-db-retry", fallbackMethod = "findDocDbRetry")
    public ResponseEntity<Document> findDoc(@PathVariable String email) {
        Document response = null;
        try {
            if(isRetryEnabled){
                LOGGER.info(StringConstants.RETRY_MESSAGE);
            }
            if(!isRetryEnabled){
                isRetryEnabled = true;
            }
            response = service.getDocument(email);
            if(response.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (DataBaseOperationException | IOException | ValidationException exception) {
            response = new Document();
            response.setMessage(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        isRetryEnabled = false;
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Document> findDocDbRetry(String email, Exception exception) {
        isRetryEnabled = false;
        LOGGER.info(StringConstants.FALLBACK_MESSAGE, exception);
        Document documentResponse = new Document();
        documentResponse.setMessage(StringConstants.FALLBACK_MESSAGE);
        return ResponseEntity.status(HttpStatus.OK).body(documentResponse);
    }
}
