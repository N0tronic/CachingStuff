package de.cache.client1.controller;

import de.cache.client1.domain.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private Map<Long, Person> personMap = new HashMap<>();

    @Cacheable(value = "persons", key = "#personId")
    @GetMapping(value = "/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        Person person = personMap.get(personId);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No object found with personId " + personId);
        }
        return person;
    }

    @CacheEvict(value = "persons", key = "#personId")
    @DeleteMapping(value = "{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personMap.remove(personId);
    }

    @CacheEvict(value = "persons", allEntries = true)
    @DeleteMapping
    public void deleteAllPersons() {
        personMap.clear();
    }

    @CachePut(value = "persons", key = "#person.personId")
    @PostMapping
    public Person insertPerson(@RequestBody Person person) {
        personMap.put(person.getPersonId(), person);
        return personMap.get(person.getPersonId());
    }
}
