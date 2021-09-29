package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.entities.Company;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.entities.dto.ContactDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ContactsIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        this.baseUrl = "http://localhost:" + port;
    }
    @Test
    public void addContactShouldSaveEntityToDatabase() {
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
        contact.setPhoneNumber("+36209998897");
        contact.setStatus(Status.ACTIVE);
        Contact result = testRestTemplate.postForObject(baseUrl + "/contacts/create", contact, Contact.class);
        assertEquals(contact.getFirstName(), result.getFirstName());
    }
    @Test
    public void getListOfContactsShouldReturnTenPerPage() {
        int pageNumber = 1;
        List<ContactDto> contactDto = List.of(testRestTemplate.getForObject(baseUrl + "/contacts/" + pageNumber, ContactDto[].class));
        assertEquals(10, contactDto.size());
    }

    @Test
    public void getContactShouldReturnedById() {
        Contact contact = testRestTemplate.getForObject(baseUrl + "/contacts/get/111", Contact.class);
        assertEquals(contact.getId(), 111);
    }

    @Test
    public void updateContactShouldSaveToDatabase() {
        Contact contact = testRestTemplate.getForObject(baseUrl + "/contacts/get/111", Contact.class);
        contact.setLastName("Bonaparte");
        testRestTemplate.put(baseUrl + "/contacts/update/111", contact);
        contact = testRestTemplate.getForObject(baseUrl + "/contacts/get/111", Contact.class);
        assertEquals("Bonaparte",contact.getLastName());
    }

    @Test
    public void deleteContactShouldUpdateStatus(){
        testRestTemplate.delete(baseUrl + "/contacts/delete/111");
        Contact contact = testRestTemplate.getForObject(baseUrl + "/contacts/get/111", Contact.class);
        assertEquals(Status.DELETED,contact.getStatus());

    }
}