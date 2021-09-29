package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.controllers.ContactController;
import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
public class ContactsUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService service;

    @Test
    public void shouldReturnCorrectCountContactsPerPage() throws Exception {
        int page = 3;
        when(service.getContactsPerPage(3)).thenReturn(new ArrayList<>(6));
        mockMvc.perform(get("/contacts/" + page)).andExpect(status().is(200));
    }

    @Test
    public void findByIdReturnsContact() throws Exception {
        Contact contact = new Contact();
        contact.setId(1L);
        mockMvc.perform(get("/contacts/get/1")).andExpect(status().is(200));
        when(service.getContact(1L)).thenReturn(contact);
    }

    @Test
    public void saveContactValidated() throws Exception {
        Company company = new Company();
        company.setId(6L);
        Contact contact = new Contact();
        contact.setId(99L);
        contact.setCompany(company);
        contact.setCreated(new Date());
        contact.setEmail("heyjoe@gmail.com");
        contact.setFirstName("Andrew");
        contact.setLastName("Earl");
        contact.setModified(new Date());
        contact.setPhoneNumber("+444444");
        contact.setStatus(Status.DELETED);
        mockMvc.perform(post("/contacts/create")).andExpect(status().is(400));
        when(service.saveContact(contact)).thenThrow(RuntimeException.class);
    }

}
