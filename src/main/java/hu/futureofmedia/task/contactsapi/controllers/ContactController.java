package hu.futureofmedia.task.contactsapi.controllers;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.dto.ContactDto;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import hu.futureofmedia.task.contactsapi.exception.InvalidPhoneNumberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/{pageNumber}")
    public List<ContactDto> getContactsList(@PathVariable int pageNumber) {
        return contactService.getContactsPerPage(pageNumber);
    }

    @GetMapping("/get/{id}")
    public Contact getSingleContact(@PathVariable Long id) {
        return contactService.getContact(id);
    }

    @PostMapping("/create")
    public Contact createContact(@RequestBody @Valid Contact contact) {
        return contactService.saveContact(contact);
    }

    @PutMapping("/update/{id}")
    public void updateContact(@PathVariable Long id, @RequestBody @Valid Contact contact) {
        contactService.updateContact(contact, id);
    }

    @RequestMapping("/delete/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<Object> handlePhoneNumberException(InvalidPhoneNumberException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
