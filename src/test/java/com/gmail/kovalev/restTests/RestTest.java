package com.gmail.kovalev.restTests;

import com.gmail.kovalev.config.Config;
import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.testData.FacultyDTOTestData;
import com.gmail.kovalev.testData.FacultyInfoDTOTestData;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RestTest {
    private final static String URL_MAIN = "http://localhost:8080/Faculty";

    @Disabled
    @Description("Проверяет, что отображается на странице именно столько элементов, сколько указано в properties.yml")
    @Test
    public void checkNumberElementsAtPage() {
        // given
        int expected = Integer.parseInt(Config.getInstance().config.get("application").get("page size"));

        // when
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        List<FacultyInfoDTO> actual = given()
                .when()
                .get("/all?page=1")
                .then().log().all()
                .extract().body().jsonPath().getList(".", FacultyInfoDTO.class);

        // then
        assertThat(actual)
                .hasSize(expected);
    }

    @Disabled
    @Description("Проверяет логику поля freePlaces .. не может быть отрицательным")
    @Test
    public void checkFreePlacesLogic() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        // when
        List<FacultyInfoDTO> actual = given()
                .when()
                .get("/all?page=1")
                .then().log().all()
                .extract().body().jsonPath().getList(".", FacultyInfoDTO.class);

        // then
        assertThat(actual)
                .allMatch(f -> f.freePlaces() > 0);
    }

    @Disabled
    @Description("Проверяет, что при неверно заданной странице, возвращает те же элементы что и для первой")
    @Test
    public void checkWrongPageNumber() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        List<FacultyInfoDTO> expected = given()
                .when()
                .get("/all?page=1")
                .then().log().all()
                .extract().body().jsonPath().getList(".", FacultyInfoDTO.class);

        // when
        List<FacultyInfoDTO> actual = given()
                .when()
                .get("/all?page=blaaaaa")
                .then().log().all()
                .extract().body().jsonPath().getList(".", FacultyInfoDTO.class);

        // then
        assertThat(actual)
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Disabled
    @Description("Проверяет правильность полей факультета по запросу Get")
    @Test
    public void checkGetById() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        UUID uuid = UUID.fromString("773dcbc0-d2fa-45b4-acf8-485b682adedd");
        FacultyInfoDTO expected = FacultyInfoDTOTestData.builder()
                .withId(uuid)
                .withName("Geography")
                .withTeacher("Ivanov Petr Sidorovich")
                .withEmail("geography@gmail.com")
                .withFreePlaces(13)
                .withPricePerDay(6.72)
                .build().buildFacultyInfoDTO();

        // when
        FacultyInfoDTO actual = given()
                .when()
                .get("/faculty?uuid=773dcbc0-d2fa-45b4-acf8-485b682adedd")
                .then().log().all()
                .extract().body().as(FacultyInfoDTO.class);

        // then
        assertThat(actual)
                .isNotNull()
                .isInstanceOf(FacultyInfoDTO.class)
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.id, expected.id())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.teacher, expected.teacher())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.email, expected.email())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.freePlaces, expected.freePlaces())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.pricePerDay, expected.pricePerDay());
    }

    @Disabled
    @Description("Проверяет что при ошибке выводится текст FacultyNotFoundException при неверном uuid по запросу Get. Статус 404")
    @Test
    public void checkGetByIdNotExists() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationNotFound404()
        );

        // when
        String actual = given()
                .when()
                .get("/faculty?uuid=873dcbc0-d2fa-45b4-acf8-485b682adedd")
                .then().log().all()
                .extract().body().asString();

        // then
        assertThat(actual)
                .isEqualTo("Faculty with id: 873dcbc0-d2fa-45b4-acf8-485b682adedd not found");
    }

    @Disabled
    @Description("Проверяет сообщение после отправки Post запроса")
    @Test
    public void checkPost() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        FacultyDTO sent = FacultyDTOTestData.builder()
                .build().buildFacultyDTO();

        // when
        String actual = given()
                .body(sent)
                .when()
                .post("/faculty")
                .then().log().all()
                .extract().body().asString();

        // then
        assertThat(actual)
                .contains("The faculty " + sent.name() + " has been saved in the database with UUID: ");
    }

    @Disabled
    @Description("Проверяет сообщение после отправки Put запроса")
    @Test
    public void checkPut() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        FacultyDTO sent = FacultyDTOTestData.builder()
                .build().buildFacultyDTO();

        // when
        String actual = given()
                .body(sent)
                .when()
                .put("/faculty?uuid=da1a2959-363b-477e-ab23-66ef983a7568")
                .then().log().all()
                .extract().body().asString();

        // then
        assertThat(actual)
                .contains("The faculty with UUID: da1a2959-363b-477e-ab23-66ef983a7568 has been updated in the database.");
    }

    @Disabled
    @Description("Проверяет сообщение после отправки Delete запроса")
    @Test
    public void checkDelete() {
        // given
        Specifications.installSpecification(
                Specifications.requestSpecification(URL_MAIN),
                Specifications.responseSpecificationOk200()
        );

        // when
        String actual = given()
                .when()
                .delete("/faculty?uuid=8d8cfc84-e77c-4722-b4d6-8e9fdc17c721")
                .then().log().all()
                .extract().body().asString();

        // then
        assertThat(actual)
                .containsAnyOf("The faculty with UUID: 8d8cfc84-e77c-4722-b4d6-8e9fdc17c721 has been deleted.",
                        "Faculty with id: 8d8cfc84-e77c-4722-b4d6-8e9fdc17c721 not found");
    }
}
