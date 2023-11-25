package edu.hw7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A synchronized in-memory person database implementation that allows adding, deleting,
 * and searching for persons by various attributes such as name, address, and phone number.
 * Ensures consistency across different attributes, meaning a person can only be found if they are
 * available to be searched by all their attributes.
 */
public class Task3 implements PersonDatabase {
    private final Map<Integer, Person> personsById = new HashMap<>();
    private final Map<String, List<Person>> personsByName = new HashMap<>();
    private final Map<String, List<Person>> personsByAddress = new HashMap<>();
    private final Map<String, List<Person>> personsByPhone = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        personsById.put(person.id(), person);
        personsByName.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
        personsByAddress.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
        personsByPhone.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = personsById.remove(id);
        if (person != null) {
            personsByName.get(person.name()).remove(person);
            personsByAddress.get(person.address()).remove(person);
            personsByPhone.get(person.phoneNumber()).remove(person);
        }
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return personsByName.getOrDefault(name, Collections.emptyList());
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return personsByAddress.getOrDefault(address, Collections.emptyList());
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return personsByPhone.getOrDefault(phone, Collections.emptyList());
    }
}
