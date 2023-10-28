package edu.hw3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Provides utility methods for parsing and sorting contacts.
 */
public class Task5 {

    /**
     * Parses and sorts an array of contacts.
     *
     * @param contacts An array of string contacts.
     * @param order    The sort order: "ASC" or "DESC".
     * @return A sorted list of Contact objects.
     */
    public static List<Contact> parseContacts(String[] contacts, String order) {
        if (contacts == null || contacts.length == 0) {
            return new ArrayList<>();
        }

        List<Contact> contactList = new ArrayList<>();
        for (String contact : contacts) {
            contactList.add(new Contact(contact));
        }

        if ("DESC".equals(order)) {
            contactList.sort(Comparator.reverseOrder());
        } else {
            contactList.sort(Comparator.naturalOrder());
        }

        return contactList;
    }

    private Task5() {}

    /**
     * Represents a Contact with a name and optional surname.
     */
    public static class Contact implements Comparable<Contact> {

        public final String name;
        public final String surname;

        /**
         * Constructs a Contact from a full name.
         *
         * @param fullName A string containing the name and optional surname.
         */
        public Contact(String fullName) {
            String[] parts = fullName.split(" ");
            this.name = parts[0];
            this.surname = (parts.length > 1) ? parts[1] : null;
        }

        /**
         * Returns the surname if it exists, otherwise the name.
         *
         * @return A string to be used for comparison.
         */
        private String toCompare() {
            return this.surname != null ? this.surname : this.name;
        }

        @Override
        public int compareTo(Contact other) {
            return toCompare().compareTo(other.toCompare());
        }

        @Override
        public String toString() {
            return name + (surname == null ? "" : " " + surname);
        }

        @Override public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Contact contact)) {
                return false;
            }

            return Objects.equals(name, contact.name) && Objects.equals(surname, contact.surname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, surname);
        }
    }
}
