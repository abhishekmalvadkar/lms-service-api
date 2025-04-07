package com.amalvadkar.lms.links.controllers.rest;

import com.amalvadkar.lms.common.AbstractIT;
import com.amalvadkar.lms.links.entities.LinkEntity;
import com.amalvadkar.lms.links.repositories.LinkRepo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("/scripts/tags/link-tag-test-data.sql")
class LinkRestControllerTest extends AbstractIT {

    @Autowired
    LinkRepo linkRepo;

    @Test
    void should_create_new_link() {

        String requestPayload = """
                {
                    "title":"Spring Boot Guide",
                    "url":"https://spring.io/guides",
                    "tagIds":["01JJEBSXC40CSH697GCJ4MQRYP","01JJH0YBKJWSER0434BCF1QKAQ"]
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("api/lms/links/create-link")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Created Successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(201);

        String newCreatedLinkId = response.path("data.linkId");

        LinkEntity createdLink = linkRepo.findLinkWithTagsWithUser(newCreatedLinkId,
                "01JHCWNZ8TJT54N2XW130WDS8K").orElseThrow();
        assertThat(createdLink.getTitle()).isEqualTo("Spring Boot Guide");
        assertThat(createdLink.getTags()).hasSize(2);
        assertThat(createdLink.getViewCount()).isZero()

        ;

    }

    @Test
    void send_link_already_exists_error_message_if_link_url_already_exists() {

        String requestPayload = """
                {
                    "title":"MySQL Learning",
                    "url":"https://abhishekmalvadkar.netlify.app/tags/mysql/",
                    "tagIds":["01JJEBSXC40CSH697GCJ4MQRYP","01JJH0YBKJWSER0434BCF1QKAQ"]
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayload)
                .when()
                .post("api/lms/links/create-link")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isFalse();

        String message = response.path("message");
        assertThat(message).isEqualTo("Link already exists");

        int code = response.path("code");
        assertThat(code).isEqualTo(409);

        Object data = response.path("data");
        assertThat(data).isNull();
    }

    public void should_delete_link_by_id() {
        String requestPayLoad =
                """
                             {
                               linkId = "01JK2EXA0HTDGHG78YMSSB35Z2"
                             }
                        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayLoad)
                .when()
                .post("api/lms/links/delete-link")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isFalse();

        String message = response.path("message");
        assertThat(message).isEqualTo("Deleted successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(204);

        LinkEntity link = linkRepo.findById("01JK2EXA0HTDGHG78YMSSB35Z2").get();

        assertThat(link.getDeleteFlag()).isTrue();
        assertThat(link.getUpdatedOn()).isAfter(link.getCreatedOn());

    }

    @Test
    public void fetch_links_by_searchText() {
        String requestPayLoad =
                """
                             {
                               "pagNo": 1,
                               "searchText" : "Spring"
                             }
                        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayLoad)
                .when()
                .post("api/lms/links/fetch-links")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Fetched successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        List<Object> content = response.path("data.content");
        assertThat(content).hasSize(2);

        String linkId = response.path("data.content[0].linkId");
        assertThat(linkId).isEqualTo("01JK3S9WCAY4X5D04VHKAZDWC7");

        String tagName = response.path("data.content[0].tags[0].tagName");
        assertThat(tagName).isEqualTo("spring-boot");

        Integer totalElement = response.path("data.totalElements");
        assertThat(totalElement).isEqualTo(3);
    }

    @Test
    public void fetch_links_by_tag() {
        String requestPayLoad =
                """
                             {
                               "pagNo": 1,
                               "tagId" : "01JJEBSXC40CSH697GCJ4MQRYP"
                             }
                        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayLoad)
                .when()
                .post("api/lms/links/fetch-links")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Fetched successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        List<Object> content = response.path("data.content");
        assertThat(content).hasSize(2);

        String linkId = response.path("data.content[0].linkId");
        assertThat(linkId).isEqualTo("01JK3S9WCAY4X5D04VHKAZDWC7");

        String tagName = response.path("data.content[0].tags[0].tagName");
        assertThat(tagName).isEqualTo("spring-boot");

        Integer totalElement = response.path("data.totalElements");
        assertThat(totalElement).isEqualTo(3);
    }

    @Test
    public void view_link_should_update_view_count_and__return_link_url() {
        String requestPayLoad =
                        """
                             {
                               "linkId":"01JK2EXA0HTDGHG78YMSSB35Z2"
                             }
                        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("X-User-Id", "01JHCWNZ8TJT54N2XW130WDS8K")
                .header("X-Role-Id", "01JHCWEFS3D4YMWYGRAMX8FZT1")
                .header("X-Device", "web")
                .body(requestPayLoad)
                .when()
                .post("api/lms/links/view-link")
                .then()
                .extract()
                .response();

        boolean success = response.path("success");
        assertThat(success).isTrue();

        String message = response.path("message");
        assertThat(message).isEqualTo("Viewed  successfully");

        int code = response.path("code");
        assertThat(code).isEqualTo(200);

        String url = response.path("data");
        assertThat(url).isEqualTo("https://abhishekmalvadkar.netlify.app/tags/mysql/");

        LinkEntity updatedLinkEntity = linkRepo.findById("01JK2EXA0HTDGHG78YMSSB35Z2").orElseThrow();
        assertThat(updatedLinkEntity.getViewCount()).isEqualTo(1L);

    }
}

