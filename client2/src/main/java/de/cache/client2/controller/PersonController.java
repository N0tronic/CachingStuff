package de.cache.client2.controller;

import de.cache.client2.domain.Person;
import de.cache.client2.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @Cacheable(value = "persons", key = "#personId")
    @GetMapping(value = "/{personId}")
    public Person getPerson(@PathVariable Long personId) {
        return personRepository
                .findById(personId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No object found with personId " + personId));
    }

    @CacheEvict(value = "persons", key = "#personId")
    @DeleteMapping(value = "{personId}")
    public void deletePerson(@PathVariable Long personId) {
        personRepository.deleteById(personId);
    }

    @CacheEvict(value = "persons", allEntries = true)
    @DeleteMapping
    public void deleteAllPersons() {
        personRepository.deleteAll();
    }

    @CachePut(value = "persons", key = "#person.personId")
    @PostMapping
    public Person insertPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }
}
