package hu.futureofmedia.task.contactsapi.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import hu.futureofmedia.task.contactsapi.entities.dto.ContactDto;
import hu.futureofmedia.task.contactsapi.exception.InvalidPhoneNumberException;
import hu.futureofmedia.task.contactsapi.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository repository;

    private PhoneNumberUtil phoneNumberUtil;

    public ContactService(ContactRepository repository, PhoneNumberUtil phoneNumberUtil) {
        this.repository = repository;
        this.phoneNumberUtil = phoneNumberUtil;
    }

    public Contact saveContact(Contact contact) {
        List<Contact> temp = repository.findAll();
        if (temp.contains(contact)) {
            throw new RuntimeException("This contact is already exists or bad saving parameters");
        }
        validatePhoneNumber(contact);
        return repository.save(contact);
    }

    private void validatePhoneNumber(Contact contact) {
        try {
            var phoneNumber = phoneNumberUtil.parse(contact.getPhoneNumber(), "HU");
            if (!phoneNumberUtil.isValidNumber(phoneNumber)) {
                throw new InvalidPhoneNumberException(contact.getPhoneNumber());
            }
        } catch (NumberParseException e) {
            System.out.println("Something bad happened while checking the phonenumber");
            throw new InvalidPhoneNumberException(contact.getPhoneNumber(), e);
        }
    }

    public List<ContactDto> getContactsPerPage(int page) {
        List<ContactDto> response = new ArrayList<>();
        try {
            List<Contact> allContact = repository.findAll();
            for (Contact contact : allContact) {
                allContact.removeIf(contact1 -> contact.getStatus() == Status.DELETED);
            }
            List<Contact> allContactOrdered = allContact.stream()
                    .sorted(Comparator.comparing(Contact::getFirstName)) // sort by price
                    .collect(Collectors.toList());
            for (int i = (page - 1) * 10; i < ((page - 1) * 10) + 10; i++) {
                if (i + 1 > allContactOrdered.size()) {
                    return response;
                }
                Contact temp = allContactOrdered.get(i);
                response.add(new ContactDto(temp.getFirstName() + "" + temp.getLastName(),
                        temp.getCompany().getName(),
                        temp.getEmail(),
                        temp.getPhoneNumber()));
            }
        } catch (RuntimeException e) {
            System.out.println("no displayable contacts");
            throw new RuntimeException(e);
        }
        return response;
    }

    public Contact getContact(Long id) {
        return repository.findById(id).get();
    }

    public void updateContact(Contact contact, Long id) {
        Contact contactUpdate = repository.findById(id).get();
        contactUpdate.setFirstName(contact.getFirstName());
        contactUpdate.setLastName(contact.getLastName());
        contactUpdate.setCompany(contact.getCompany());
        contactUpdate.setEmail(contact.getEmail());
        contactUpdate.setPhoneNumber(contact.getPhoneNumber());
        contactUpdate.setNote(contact.getNote());
        contactUpdate.setModified(new Date());
        validatePhoneNumber(contactUpdate);
        repository.save(contactUpdate);
    }

    public void deleteContact(Long id) {
        Contact contact = repository.findById(id).get();
        contact.setStatus(Status.DELETED);
        contact.setModified(new Date());
        repository.save(contact);
    }
}


