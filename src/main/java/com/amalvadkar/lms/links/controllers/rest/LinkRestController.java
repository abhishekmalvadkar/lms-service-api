package com.amalvadkar.lms.links.controllers.rest;


import com.amalvadkar.lms.common.constants.RequestHeaderConstant;
import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.links.models.request.CreateLinkRequest;
import com.amalvadkar.lms.links.models.request.DeleteLinkRequest;
import com.amalvadkar.lms.links.models.request.FetchLinkRequest;
import com.amalvadkar.lms.links.models.request.ViewLinkRequest;
import com.amalvadkar.lms.links.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lms/links")
@RequiredArgsConstructor
public class LinkRestController {

    public static final String ENDPOINT_CREATE_LINK = "/create-link";
    public static final String ENDPOINT_DELETE_LINK = "/delete-link";
    public static final String ENDPOINT_FETCH_LINKS = "/fetch-links";
    public static final String ENDPOINT_VIEW_LINK = "/view-link";

    private final LinkService linkService;

    @PostMapping(ENDPOINT_CREATE_LINK)
    public ResponseEntity<CustomResponse> createLink(@RequestBody CreateLinkRequest createLinkRequest,
                                                     @RequestHeader(RequestHeaderConstant.REQUEST_HEADER_USER_ID) String loggedInUserId) {
       return ResponseEntity.ok(this.linkService.createLink(createLinkRequest, loggedInUserId));
    }

    @PostMapping(ENDPOINT_DELETE_LINK)
    public ResponseEntity<CustomResponse> deleteLink(@RequestBody DeleteLinkRequest deleteLinkRequest,
                                                     @RequestHeader(RequestHeaderConstant.REQUEST_HEADER_USER_ID) String loggedInUserId) {
        return ResponseEntity.ok(this.linkService.deleteLink(deleteLinkRequest, loggedInUserId));
    }

    @PostMapping(ENDPOINT_FETCH_LINKS)
    public ResponseEntity<CustomResponse> fetchLinks(@RequestBody FetchLinkRequest fetchLinkRequest,
                                                     @RequestHeader(RequestHeaderConstant.REQUEST_HEADER_USER_ID) String loggedInUserId) {
        return ResponseEntity.ok(this.linkService.fetchLinks(fetchLinkRequest, loggedInUserId));
    }


    @PostMapping(ENDPOINT_VIEW_LINK)
    public ResponseEntity<CustomResponse> viewLink(@RequestBody ViewLinkRequest viewLinkRequest,
                                                   @RequestHeader(RequestHeaderConstant.REQUEST_HEADER_USER_ID) String loggedInUserId) {
        return ResponseEntity.ok(this.linkService.viewLink(viewLinkRequest, loggedInUserId));
    }


}
