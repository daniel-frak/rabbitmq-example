package dev.codesoapbox.rabbitmqexampleaudit.infrastructure.controllers;

import dev.codesoapbox.rabbitmqexampleaudit.domain.EventCounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventCounterService eventCounterService;

    @Test
    void shouldGetAuditInfo() throws Exception {
        when(eventCounterService.getProducedTasks())
                .thenReturn(5);
        when(eventCounterService.getProcessedTasks())
                .thenReturn(4);
        when(eventCounterService.getCertifiedTasks())
                .thenReturn(3);
        when(eventCounterService.getDiscardedTasks())
                .thenReturn(2);

        var expectedJson = """
                {
                    "producedTasks": 5,
                    "processedTasks": 4,
                    "certifiedTasks": 3,
                    "discardedTasks": 2
                }
                """;

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}