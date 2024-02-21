package com.hackaboss.pruebatec4;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void CUANDO_se_llama_a_flights_con_GET_ENTONCES_el_estado_es_200() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/agency/flights")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                    .content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    public void CUANDO_se_llama_a_flights_con_POST_ENTONCES_el_estado_es_200() throws Exception{

        String jsonContent = "{ \"origin\": \"Sevilla\", \"destination\": \"Oviedo\", \"date\": \"2024/08/22\", \"price\": 800.00, \"typeSeat\": \"Bussiness\", \"totalSeat\": 26, \"bookedSeat\": 0 }";

        mockMvc.perform(MockMvcRequestBuilders.post("/agency/flights/new")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }


}
