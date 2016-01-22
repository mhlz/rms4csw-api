package st.ilu.rms4csw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import st.ilu.rms4csw.TestContext;
import st.ilu.rms4csw.model.support.ComplaintTemplate;
import st.ilu.rms4csw.repository.support.ComplaintTemplateRepository;

import javax.annotation.Resource;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Mischa Holz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestContext.class)
@WebAppConfiguration
public class ComplaintTemplateControllerTest {

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private ComplaintTemplate one;

    private ComplaintTemplate two;

    private MockMvc mockMvc;

    @Autowired
    private ComplaintTemplateRepository complaintTemplateRepository;

    @Before
    public void setUp() throws Exception {
        complaintTemplateRepository.deleteAllInBatch();

        one = new ComplaintTemplate();
        one.setText("one");

        two = new ComplaintTemplate();
        two.setText("two");

        complaintTemplateRepository.save(Arrays.asList(one, two));

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        complaintTemplateRepository.deleteAllInBatch();
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/complainttemplates").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(one.getId())))
                .andExpect(jsonPath("$[1].id", is(two.getId())));
    }

    @Test
    public void testFindOne() throws Exception {
        mockMvc.perform(get("/api/v1/complainttemplates/" + one.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(one.getId())));
    }

    @Test
    public void testPostComplaintTemplate() throws Exception {
        ComplaintTemplate three = new ComplaintTemplate();
        three.setText("three");


        String location = mockMvc.perform(post("/api/v1/complainttemplates")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(three))
        ).andExpect(jsonPath("$.id", is(three.getId())))
                .andExpect(header().string("Location", Matchers.notNullValue()))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        mockMvc.perform(get(location).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(three.getId())))
                .andExpect(jsonPath("$.text", is(three.getText())));
    }

    @Test
    public void testDeleteRoomReservation() throws Exception {
        mockMvc.perform(delete("/api/v1/complainttemplates/" + one.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/complainttemplates/" + one.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPutComplaintTemplate() throws Exception {
        two.setText("bla");

        mockMvc.perform(put("/api/v1/complainttemplates/" + two.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(two)))
                .andExpect(jsonPath("$.id", is(two.getId())))
                .andExpect(jsonPath("$.text", is(two.getText())));
    }

    @Test
    public void testPatchComplaintTemplate() throws Exception {
        ComplaintTemplate template = new ComplaintTemplate();
        template.setText("bla");
        template.setId(null);

        mockMvc.perform(patch("/api/v1/complainttemplates/" + one.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(template)))
                .andExpect(jsonPath("$.id", is(one.getId())))
                .andExpect(jsonPath("$.text", is(template.getText())));
    }
}
