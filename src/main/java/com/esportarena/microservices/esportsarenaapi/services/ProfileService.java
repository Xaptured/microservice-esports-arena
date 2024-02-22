package com.esportarena.microservices.esportsarenaapi.services;

import com.esportarena.microservices.esportsarenaapi.clients.TheJackFolioDBClient;
import com.esportarena.microservices.esportsarenaapi.exceptions.DataBaseOperationException;
import com.esportarena.microservices.esportsarenaapi.exceptions.MapperException;
import com.esportarena.microservices.esportsarenaapi.exceptions.ValidationException;
import com.esportarena.microservices.esportsarenaapi.models.Document;
import com.esportarena.microservices.esportsarenaapi.models.Partner;
import com.esportarena.microservices.esportsarenaapi.models.ProfileDetail;
import com.esportarena.microservices.esportsarenaapi.servicehelpers.ProfileServiceHelper;
import com.esportarena.microservices.esportsarenaapi.utilities.StringConstants;
import io.micrometer.common.util.StringUtils;
import jakarta.mail.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class ProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);
    @Autowired
    private ProfileServiceHelper validation;
    @Autowired
    private TheJackFolioDBClient dbClient;

    public ProfileDetail saveOrUpdateProfileDetails(ProfileDetail detail) throws ValidationException, DataBaseOperationException, MapperException {
        validation.checkProfileDetailsFromUI(detail);
        ResponseEntity<ProfileDetail> response = dbClient.saveOrUpdateProfileDetails(detail);
        ProfileDetail responseBody = response.getBody();
        validation.checkProfileDetailsFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public ProfileDetail getProfileDetails(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getProfileDetails for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<ProfileDetail> response = dbClient.getProfileDetails(email);
            ProfileDetail responseBody = response.getBody();
            validation.checkProfileDetailsFromDB(responseBody);
            if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                throw new DataBaseOperationException(responseBody.getMessage());
            }
            else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                throw new MapperException(responseBody.getMessage());
            }
            return responseBody;
        }
    }

    public Boolean isProfilePresent(String email) throws ValidationException {
        Boolean responseBody = false;
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getProfileDetails for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<Boolean> response = dbClient.isProfilePresent(email);
            if(response.getStatusCode().is2xxSuccessful()) {
                responseBody = response.getBody();
            } else {
                throw new ValidationException(StringConstants.FALLBACK_MESSAGE);
            }
        }
        return responseBody;
    }

    public boolean isProfileComplete(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isBlank(email) || StringUtils.isEmpty(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getProfileDetails for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        } else {
            ResponseEntity<ProfileDetail> response = dbClient.getProfileDetails(email);
            ProfileDetail responseBody = response.getBody();
            validation.checkProfileDetailsFromDB(responseBody);
            if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
                throw new DataBaseOperationException(responseBody.getMessage());
            }
            else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
                throw new MapperException(responseBody.getMessage());
            } else  {
                return validation.isProfileComplete(responseBody);
            }
        }
    }

    public Partner saveOrUpdatePartner(Partner partner) throws ValidationException, DataBaseOperationException, MapperException, FileNotFoundException {
        validation.checkPartnerFromUI(partner);
        ResponseEntity<Partner> response = dbClient.saveOrUpdatePartner(partner);
        Partner responseBody = response.getBody();
        validation.checkPartnerFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.FILE_NOT_PRESENT)){
            throw new FileNotFoundException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Partner findPartner(String email) throws ValidationException, DataBaseOperationException, MapperException {
        if(StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            LOGGER.error("Validation failed in ProfileService.class : findPartner for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        ResponseEntity<Partner> response = dbClient.findPartner(email);
        Partner responseBody = response.getBody();
        validation.checkPartnerFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        }
        return responseBody;
    }

    public Partner saveDocument(MultipartFile image, MultipartFile doc, String email) throws ValidationException, DataBaseOperationException, MapperException, IOException {
        validation.checkPartnerDocsFromUI(image, doc, email);
        ResponseEntity<Partner> response = dbClient.saveDocuments(image, doc, email);
        Partner responseBody = response.getBody();
        validation.checkPartnerFromDB(responseBody);
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        }
        else if(responseBody.getMessage().equals(StringConstants.MAPPING_ERROR)){
            throw new MapperException(responseBody.getMessage());
        } else if(!responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED) || responseBody.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
            throw new IOException();
        }
        return responseBody;
    }

    public Document getLogo(String email) throws ValidationException, DataBaseOperationException, IOException {
        if(StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getLogo for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        ResponseEntity<Document> response = dbClient.findLogo(email);
        Document responseBody = response.getBody();
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        } else if(!responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED) || responseBody.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
            throw new IOException();
        }
        return responseBody;
    }

    public Document getDocument(String email) throws ValidationException, DataBaseOperationException, IOException {
        if(StringUtils.isEmpty(email) || StringUtils.isBlank(email)) {
            LOGGER.error("Validation failed in ProfileService.class : getLogo for object: null");
            throw new ValidationException(StringConstants.VALIDATION_ERROR);
        }
        ResponseEntity<Document> response = dbClient.findDoc(email);
        Document responseBody = response.getBody();
        if(responseBody.getMessage().equals(StringConstants.DATABASE_ERROR)){
            throw new DataBaseOperationException(responseBody.getMessage());
        } else if(!responseBody.getMessage().equals(StringConstants.REQUEST_PROCESSED) || responseBody.getMessage().equals(StringConstants.EMAIL_NOT_PRESENT)) {
            throw new IOException();
        }
        return responseBody;
    }
}
