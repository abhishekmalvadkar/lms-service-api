package com.amalvadkar.lms.tags.controllers.rest;

import com.amalvadkar.lms.common.AbstractIT;
import com.amalvadkar.lms.tags.entities.TagEntity;
import com.amalvadkar.lms.tags.repositories.TagRepo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Sql("/scripts/tags/link-tag-test-data.sql")
class TagRestControllerTest extends AbstractIT {

    @Autowired
    TagRepo tagRepo;

    @Test
    void should_create_new_tag() {
        String requestPayload = """
                {
                    "tagName" : "Spring Data Jpa"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/create-tag")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Created Successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(201);

        String newCreatedTagId = response.path("data.tagId");
        TagEntity newCreatedTagEntity = tagRepo.findById(newCreatedTagId).orElseThrow();

        assertThat(newCreatedTagEntity.getId()).isEqualTo(newCreatedTagId);
        assertThat(newCreatedTagEntity.getName()).isEqualTo("spring-data-jpa");
        assertThat(newCreatedTagEntity.getDeleteFlag()).isFalse();
        assertThat(newCreatedTagEntity.getCreatedBy().getId()).isEqualTo("01JHCWNZ8TJT54N2XW130WDS8K");
        assertThat(newCreatedTagEntity.getUpdatedBy().getId()).isEqualTo("01JHCWNZ8TJT54N2XW130WDS8K");
        assertThat(newCreatedTagEntity.getCreatedOn()).isNotNull();
        assertThat(newCreatedTagEntity.getUpdatedOn()).isNotNull();

    }

    @Test
    void should_send_error_message_that_tag_already_exists_if_same_name_tag_created() {
        String requestPayload = """
                {
                    "tagName" : "Spring Boot"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2Z4EV7XNVKSG6X83N4")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/create-tag")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isFalse();

        String message = response.path("message");
        assertThat(message).isEqualTo("Tag already exists");

        int code = response.path("code");
        assertThat(code).isEqualTo(409);

        Object data = response.path("data");
        assertThat(data).isNull();
    }

    @Test
    void should_soft_delete_tag() {
        String requestPayload = """
                {
                    "tagId" : "01JJEBSXC40CSH697GCJ4MQRYP"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2ZW49TYVHFX2QN8KZC")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/delete-tag")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Deleted successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(204);

        Object data = response.path("data");
        assertThat(data).isNull();

        Optional<TagEntity> tagOpt = tagRepo.findTagWithUser("01JJEBSXC40CSH697GCJ4MQRYP");

        assertThat(tagOpt.get().getDeleteFlag()).isTrue();
        assertThat(tagOpt.get().getCreatedBy())
                .isNotEqualTo(tagOpt.get().getUpdatedBy());
        assertThat(tagOpt.get().getUpdatedBy().getId()).isEqualTo("01JJERFS2ZW49TYVHFX2QN8KZC");
    }

    @Test
    void should_fetch_tags_with_searchText() {
        String requestPayload = """
                {
                    "searchText" :"spr",
                    "pageNo":1
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2Z4EV7XNVKSG6X83N4")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/fetch-tags")
                .then()
                .extract()
                .response();


        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Fetched successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        Integer totalElement = response.path("data.totalElements");
        assertThat(totalElement).isEqualTo(2);


        List<Object> tags = response.path("data.content");
        assertThat(tags).isNotEmpty();
        String tagId = response.path("data.content[0].tagId");
        assertThat(tagId).isEqualTo("01JJH4PD957CFG2XZEHH6PFGV3");

        String tagName = response.path("data.content[0].tagName");
        assertThat(tagName).isEqualTo("spring-cloud");
    }

    @Test
    void should_fetch_tags_with_search_text_and_space_between_word() {
        String requestPayload = """
                {
                    "searchText":"spring boot" ,
                    "pageNo":1
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2Z4EV7XNVKSG6X83N4")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/fetch-tags")
                .then()
                .extract()
                .response();


        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Fetched successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        Integer totalElement = response.path("data.totalElements");
        assertThat(totalElement).isEqualTo(1);


        List<Object> tags = response.path("data.content");
        assertThat(tags).isNotEmpty();

        String tagName = response.path("data.content[0].tagName");
        assertThat(tagName).isEqualTo("spring-boot");


    }

    @Test
    void should_fetch_tags() {
        String requestPayload = """
                {
                    "pageNo":1
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2Z4EV7XNVKSG6X83N4")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("/api/lms/tags/fetch-tags")
                .then()
                .extract()
                .response();


        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Fetched successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        Integer totalElement = response.path("data.totalElements");
        assertThat(totalElement).isEqualTo(5);

        List<Object> tags = response.path("data.content");
        assertThat(tags).isNotEmpty();
        String tagId = response.path("data.content[0].tagId");
        assertThat(tagId).isEqualTo("01JJH0YBKJXK6MNK9G5440XG4J");

        String tagName = response.path("data.content[0].tagName");
        assertThat(tagName).isEqualTo("maven");
    }

    @Test
    void should_update_tag(){
        String requestPayload = """
                {
                    "headerConfigId":"01JHCX6M68QF420CWFCCR4KTNZ",
                    "value":"spring batch",
                    "tagId":"01JJEBSXC40CSH697GCJ4MQRYP"
                }
                """;
        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JJERFS2Z4EV7XNVKSG6X83N4")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .patch("/api/lms/tags/update-tag")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Updated successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        Optional<TagEntity> tagEntity = tagRepo.findById("01JJEBSXC40CSH697GCJ4MQRYP");

        assertThat(tagEntity.get().getName()).isEqualTo("spring-batch");

    }
}