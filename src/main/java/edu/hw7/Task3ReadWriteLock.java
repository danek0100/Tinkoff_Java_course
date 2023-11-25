package edu.hw7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A synchronized with ReadWriteLock person database implementation that allows adding, deleting,
 * and searching for persons by various attributes such as name, address, and phone number.
 * Ensures consistency across different attributes, meaning a person can only be found if they are
 * available to be searched by all their attributes.
 */
public class Task3ReadWriteLock implements PersonDatabase {
    private final Map<Integer, Person> personsById = new HashMap<>();
    private final Map<String, List<Person>> personsByName = new HashMap<>();
    private final Map<String, List<Person>> personsByAddress = new HashMap<>();
    private final Map<String, List<Person>> personsByPhone = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personsById.put(person.id(), person);
            personsByName.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
            personsByAddress.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
            personsByPhone.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = personsById.remove(id);
            if (person != null) {
                personsByName.get(person.name()).remove(person);
                personsByAddress.get(person.address()).remove(person);
                personsByPhone.get(person.phoneNumber()).remove(person);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return personsByName.getOrDefault(name, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return personsByAddress.getOrDefault(address, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return personsByPhone.getOrDefault(phone, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }
}
