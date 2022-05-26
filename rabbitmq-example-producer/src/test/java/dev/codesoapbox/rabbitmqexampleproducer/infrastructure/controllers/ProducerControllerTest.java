package dev.codesoapbox.rabbitmqexampleproducer.infrastructure.controllers;

import dev.codesoapbox.rabbitmqexampleproducer.infrastructure.TaskScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskScheduler taskScheduler;

    @Test
    void shouldEnableScheduling() throws Exception {
        mockMvc.perform(get("/enable"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(taskScheduler).setEnabled(true);
    }

    @Test
    void shouldDisableScheduling() throws Exception {
        mockMvc.perform(get("/disable"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(taskScheduler).setEnabled(false);
    }

    @Test
    void shouldEnableSchedulingWithTimeout() throws Exception {
        long startTime = System.currentTimeMillis();

        mockMvc.perform(get("/enable-timed?time-ms=500"))
                .andDo(print())
                .andExpect(status().isOk());

        long endTime = System.currentTimeMillis();

        var elapsedTime = endTime - startTime;

        assertTrue(elapsedTime >= 500);

        InOrder inOrder = inOrder(taskScheduler);
        inOrder.verify(taskScheduler).setEnabled(true);
        inOrder.verify(taskScheduler).setEnabled(false);
    }
}