package hu.futureofmedia.task.contactsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;

    private String phoneNumber;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @NotNull
    private Date created = new Date();
    @NotNull
    private Date modified = new Date();
    @GeneratedValue
    @JsonIgnore
    private final UUID uuid = UUID.randomUUID();
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    private String note;

    public Contact() {
    }
    public Contact(Long id, String firstName,
                   String lastName, String email,
                   String phoneNumber, Status status,
                   Date created, Date modified,
                   Company company, String note) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.created = created;
        this.modified = modified;
        this.company = company;
        this.note = note;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNote() {
        return note;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    public Company getCompany() {
        return company;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLastName() {
        return lastName;
    }
}
