package com.amalvadkar.lms.links.controllers.rest;


import com.amalvadkar.lms.common.constants.RequestHeaderConstant;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.links.models.request.CreateLinkRequest;
import com.amalvadkar.lms.links.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lms/links")
@RequiredArgsConstructor
public class LinkRestController {

    public static final String ENDPOINT_CREATE_LINK = "/create-link";

    private final LinkService linkService;

    @PostMapping(ENDPOINT_CREATE_LINK)
    public ResponseEntity<CustomResponse> createLink(@RequestBody CreateLinkRequest createLinkRequest,
                                                     @RequestHeader(RequestHeaderConstant.REQUEST_HEADER_USER_ID) String loggedInUserId) {
       return ResponseEntity.ok(this.linkService.createLink(createLinkRequest, loggedInUserId));
    }

}
