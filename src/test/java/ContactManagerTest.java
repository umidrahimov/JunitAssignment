import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ContactManagerTest {

    private ContactManager contactManager;

    @BeforeAll
    public static void setupAll() {
        System.out.println("Unit tests are started...");
    }

    @BeforeEach
    public void setup() {
        System.out.println("Instantiating Contact Manager");
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Create contact")
    public void addContact() {
        contactManager.addContact("Umid", "Rahimov", "0512298326");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Set first name")
    public void setFirstName() {
        Contact contact = new Contact("Null", "Rahimov", "0512298326");
        contact.setFirstName("Umid");
        assertEquals("Umid", contact.getFirstName());
    }

    @Test
    @DisplayName("Set last name")
    public void setLastName() {
        Contact contact = new Contact("Umid", "Null", "0512298326");
        contact.setLastName("Rahimov");
        assertEquals("Rahimov", contact.getLastName());
    }

    @Test
    @DisplayName("Set phone number")
    public void setPhoneNumber() {
        Contact contact = new Contact("Umid", "Rahimov", "Null");
        contact.setPhoneNumber("0512298326");
        assertEquals("0512298326", contact.getPhoneNumber());
    }

    @Test
    @DisplayName("Validate first name")
    public void validateFirstName() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("", "Rahimov", "0512298326");
            contact.validateFirstName();
        });
    }

    @Test
    @DisplayName("Validate last name")
    public void validateLastName() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("Umid", "", "0512298326");
            contact.validateLastName();
        });
    }

    @Test
    @DisplayName("Validate phone number")
    public void validatePhoneNumber() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("Umid", "Rahimov", "");
            contact.validatePhoneNumber();
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("Umid", "Rahimov", "051229832");
            contact.validatePhoneNumber();
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("Umid", "Rahimov", "051229832B");
            contact.validatePhoneNumber();
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            Contact contact = new Contact("Umid", "Rahimov", "5512298326");
            contact.validatePhoneNumber();
        });
    }

    @Test
    @DisplayName("Don't create contact if first name is Null")
    public void addContactWithNullFirstName() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Rahimov", "0512298326");
        });
    }

    @Test
    @DisplayName("Throw exception if contact already exists")
    public void checkIfContactAlreadyExists() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("Umid", "Rahimov", "0512298326");
            contactManager.addContact("Umid", "Rahimov", "0512298326");
        });
    }

    @Test
    @DisplayName("Don't create contact if last name is Null")
    public void addContactWithNullLastName() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("Umid", null, "0512298326");
        });
    }

    @Test
    @DisplayName("Don't create contact if phone number is Null")
    public void addContactWithNullPhoneNumber() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("Umid", "Rahimov", null);
        });
    }

    @Test
    @DisplayName("Phone Number should start with 0")
    public void validatePhoneNumberFormat() {
        contactManager.addContact("Umid", "Rahimov", "0512298326");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @Nested
    class ParameterizedTests {
        @DisplayName("Phone Number should match the required Format")
        @ParameterizedTest
        @ValueSource(strings = { "0512298326", "0556329037", "0773111141" })
        public void validatePhoneNumberFormatWithValueSource(String phoneNumber) {
            contactManager.addContact("Umid", "Rahimov", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }


        @DisplayName("Phone Number should match the required Format in CSV file")
        @ParameterizedTest
        @CsvFileSource(resources = "/source.csv")
        public void validatePhoneNumberFormatWithCSVFile(String phoneNumber) {
            contactManager.addContact("Umid", "Rahimov", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    @AfterEach
    public void completeTest() {
        System.out.println("Unit test is completed");
    }

    @AfterAll
    public static void completeAllTests() {
        System.out.println("All unit tests are completed");

    }
}
