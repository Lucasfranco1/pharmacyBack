package com.pharmacy.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.pharmacy.demo.dto.MedicineDTO;
import com.pharmacy.demo.entity.MedicineEntity;
import com.pharmacy.demo.service.MedicineService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = MedicineController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MedicineControllerTest {

    @Autowired
     MockMvc mockMvc;

    @MockBean
    MedicineService medicineService;


    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    MedicineEntity medicine;
    MedicineDTO medicineDTO;


    @BeforeEach
    void setUp() {
        medicine = MedicineEntity.builder()
                .id(1L)
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();

        medicineDTO = MedicineDTO.builder()
                .id("1")
                .nameMedicine("paracetamol")
                .factoryLaboratory("pfizer")
                .manufacturingDate("21/2/2023")
                .dueDate("23/2/2024")
                .unitValue("32")
                .stockQuantity("32")
                .build();
    }

    @Test
    void medicineController_Created() throws Exception {
        given(medicineService.savedMedicine(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0) ));

        ResultActions response = mockMvc.perform(post("/medicine")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(medicineDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameMedicine", CoreMatchers.is(medicineDTO.getNameMedicine())));

    }

    @Test
    void medicineController_Update() throws Exception {
        Long id = 1L;
        when(medicineService.updateMedicine(id, medicineDTO)).thenReturn(medicineDTO);

        ResultActions response = mockMvc.perform(put("/medicine/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(medicineDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nameMedicine", CoreMatchers.is(medicineDTO.getNameMedicine())));


    }
    @Test
    void deleteController_returnNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(medicineService).deleteMedicineById(1L);

        ResultActions response = mockMvc.perform(delete("/medicine/delete/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());

    }@Test
    void detailIdController_ReturnOk() throws Exception {
        Long id = 1L;
        when(medicineService.getOneMedicine(id)).thenReturn(Optional.of(medicine));

        ResultActions response = mockMvc.perform(get("/medicine/detail/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(medicineDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    void listByNameMedicine_Ok() throws Exception {
        String name = "name";
        when(medicineService.findByNameMedicine(name)).thenReturn(Arrays.asList(medicineDTO));

        ResultActions response = mockMvc.perform(post("/medicine")
                .contentType(MediaType.APPLICATION_JSON)
                .param(name, "p"));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    void listByNameMedicine_BadRequest() throws Exception {
        String name = "na";
        when(medicineService.findByNameMedicine(name)).thenReturn(Arrays.asList(medicineDTO));

        ResultActions response = mockMvc.perform(post("/medicine")
                .contentType(MediaType.APPLICATION_JSON)
                .param(name, "p"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    @Test
    void pageControllerAscFalseOk() throws Exception {
        Page<MedicineEntity> medicines  = Mockito.mock(Page.class);
        when(medicineService.pages(Mockito.mock(Pageable.class))).thenReturn(medicines);

        ResultActions response = mockMvc.perform(get("/medicine/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10")
                .param("order", "name")
                .param("asc", "false"));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    void pageControllerAscTrueOk() throws Exception {
        Page<MedicineEntity> medicines  = Mockito.mock(Page.class);
        when(medicineService.pages(Mockito.mock(Pageable.class))).thenReturn(medicines);

        ResultActions response = mockMvc.perform(get("/medicine/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10")
                .param("order", "name")
                .param("asc", "true"));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
