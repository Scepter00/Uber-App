package africa.semicolon.uberdeluxe.controller;

import africa.semicolon.uberdeluxe.data.dto.request.RegisterPassengerRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Slf4j
class PassengerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testRegisterPassenger() throws Exception {
       RegisterPassengerRequest request = new RegisterPassengerRequest();
       request.setName("Dean");
       request.setEmail("dean4luv@yahoo.com");
       request.setPassword("dean_baby1234");

       String jsonRequest = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/api/v1/passenger")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.CREATED.value()))
                    .andDo(print());

    }


}