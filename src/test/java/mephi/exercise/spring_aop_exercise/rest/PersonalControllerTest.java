package mephi.exercise.spring_aop_exercise.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import mephi.exercise.spring_aop_exercise.model.Person;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static mephi.exercise.spring_aop_exercise.rest.PersonalController.USER_ROLE_HEADER;

@SpringBootTest
@AutoConfigureMockMvc
class PersonalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Получение объекта по идентификатору, действие разрешено")
    @ParameterizedTest
    @ValueSource(strings = {"USER", "ADMIN", "USER ADMIN"})
    public void testGetByIdAllowed(String userRole) throws Exception {
        long id = new Date().getTime();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/"+id+"/get")
                        .header(USER_ROLE_HEADER, userRole))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(String.valueOf(id)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Иван"));
    }

    @DisplayName("Получение объекта по идентификатору, действие запрещено")
    @ParameterizedTest
    @ValueSource(strings = {"", "NONAME", "ALIEN"})
    public void testGetByIdDenied(String userRole) throws Exception {
        long id = new Date().getTime();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/"+id+"/get")
                        .header(USER_ROLE_HEADER, userRole))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @DisplayName("Сохранение объекта, действие разрешено")
    @ParameterizedTest
    @ValueSource(strings = {"USER", "ADMIN", "USER ADMIN"})
    public void testSaveAllowed(String userRole) throws Exception {
        long id0 = new Date().getTime();
        byte[] bookByte = mockMvc.perform(MockMvcRequestBuilders.post("/api/person/save")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header(USER_ROLE_HEADER, userRole)
                        .content("{\"id\":\""+id0+"\",\"name\":\"Сидор\",\"surname\":\"Сидоров\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();
        long id1 = getIdFromAnswer(bookByte);

        Assertions.assertEquals(id0, id1);
    }

    @DisplayName("Сохранение объекта, действие запрещено")
    @ParameterizedTest
    @ValueSource(strings = {"", "NONAME", "ALIEN"})
    public void testSaveDenied(String userRole) throws Exception {
        long id0 = new Date().getTime();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/person/save")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header(USER_ROLE_HEADER, userRole)
                        .content("{\"id\":\""+id0+"\",\"name\":\"Сидор\",\"surname\":\"Сидоров\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @DisplayName("Удаление объекта, действие разрешено")
    @ParameterizedTest
    @ValueSource(strings = {"ADMIN", "USER ADMIN"})
    public void testDeleteAllowed(String userRole) throws Exception {
        long id0 = new Date().getTime();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/"+id0+"/delete")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header(USER_ROLE_HEADER, userRole)
                        .content("{\"id\":\""+id0+"\",\"name\":\"Сидор\",\"surname\":\"Сидоров\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @DisplayName("Удаление объекта, действие запрещено")
    @ParameterizedTest
    @ValueSource(strings = {"USER", "", "NONAME", "ALIEN"})
    public void testDeleteDenied(String userRole) throws Exception {
        long id0 = new Date().getTime();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/"+id0+"/delete")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header(USER_ROLE_HEADER, userRole)
                        .content("{\"id\":\""+id0+"\",\"name\":\"Сидор\",\"surname\":\"Сидоров\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    private Long getIdFromAnswer(byte[] answer) throws IOException {
        String str = new String(answer, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try(JsonParser parser = objectMapper.createParser(str)) {
            Person person = parser.readValueAs(Person.class);
            return person.getId();
        }
    }

}