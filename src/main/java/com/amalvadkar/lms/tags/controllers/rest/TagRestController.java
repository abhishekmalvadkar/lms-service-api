package com.amalvadkar.lms.tags.controllers.rest;

import com.amalvadkar.lms.common.models.response.CustomResponse;
import com.amalvadkar.lms.tags.models.request.CreateTagRequest;
import com.amalvadkar.lms.tags.models.request.DeleteTagRequest;
import com.amalvadkar.lms.tags.models.request.FetchTagsRequest;
import com.amalvadkar.lms.tags.models.request.UpdateTagRequest;
import com.amalvadkar.lms.tags.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.amalvadkar.lms.common.constants.RequestHeaderConstant.REQUEST_HEADER_USER_ID;

@RestController
@RequestMapping("/api/lms/tags")
@RequiredArgsConstructor
public class TagRestController {

    private static final String ENDPOINT_CREATE_TAG = "/create-tag";
    private static final String ENDPOINT_UPDATE_TAG = "/update-tag";
    private static final String ENDPOINT_DELETE_TAG = "/delete-tag";
    private static final String ENDPOINT_FETCH_TAGS = "/fetch-tags";

    private final TagService tagService;

    @PostMapping(ENDPOINT_CREATE_TAG)
    public ResponseEntity<CustomResponse> createTag(@RequestBody CreateTagRequest createTagRequest,
                                                    @RequestHeader(REQUEST_HEADER_USER_ID) String loggedInUserId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(createTagRequest, loggedInUserId));
    }

    @PostMapping(ENDPOINT_DELETE_TAG)
    public ResponseEntity<CustomResponse> deleteTag(@RequestBody DeleteTagRequest deleteTagRequest,
                                                     @RequestHeader(REQUEST_HEADER_USER_ID) String loggedInUserId){
      return ResponseEntity.ok(this.tagService.deleteTag(deleteTagRequest, loggedInUserId));
    }

    @PostMapping(ENDPOINT_FETCH_TAGS)
    public ResponseEntity<CustomResponse> fetchTags(@RequestBody FetchTagsRequest fetchTagsRequest,
                                                    @RequestHeader(REQUEST_HEADER_USER_ID) String loggedInUserId){
        return ResponseEntity.ok(this.tagService.fetchTags(fetchTagsRequest, loggedInUserId));
    }
    @PatchMapping(ENDPOINT_UPDATE_TAG)
    public ResponseEntity<CustomResponse> updateTag(@RequestBody UpdateTagRequest updateTagRequest,
                                                    @RequestHeader(REQUEST_HEADER_USER_ID) String loggedInUserId){
        return ResponseEntity.ok(this.tagService.updateTag(updateTagRequest, loggedInUserId));
    }

}
