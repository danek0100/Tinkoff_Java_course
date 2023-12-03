package edu.hw7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    private Task3 database;

    @BeforeEach
    void setUp() {
        database = new Task3();
    }

    @Test
    @DisplayName("Add and Find Person by Name")
    void testAddAndFindByName() {
        Person person = new Person(1, "John Doe", "1234 Main St", "555-1234");
        database.add(person);

        List<Person> found = database.findByName("John Doe");
        assertThat(found).isNotEmpty()
            .contains(person);
    }

    @Test
    @DisplayName("Add and Find Person by Address")
    void testAddAndFindByAddress() {
        Person person = new Person(2, "Jane Doe", "5678 Market St", "555-5678");
        database.add(person);

        List<Person> found = database.findByAddress("5678 Market St");
        assertThat(found).isNotEmpty()
            .contains(person);
    }

    @Test
    @DisplayName("Add and Find Person by Phone")
    void testAddAndFindByPhone() {
        Person person = new Person(3, "Alice Smith", "1234 Elm St", "555-0000");
        database.add(person);

        List<Person> found = database.findByPhone("555-0000");
        assertThat(found).isNotEmpty()
            .contains(person);
    }

    @Test
    @DisplayName("Delete Person and Verify Removal")
    void testDeletePerson() {
        Person person = new Person(4, "Bob Johnson", "7890 Pine St", "555-7890");
        database.add(person);
        database.delete(4);

        assertThat(database.findByName("Bob Johnson")).isEmpty();
        assertThat(database.findByAddress("7890 Pine St")).isEmpty();
        assertThat(database.findByPhone("555-7890")).isEmpty();
    }
}
