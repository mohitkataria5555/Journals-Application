package com.mk.journalApp.service;

import com.mk.journalApp.Repository.JournalEntryRepository;
import com.mk.journalApp.entity.JournalEntry;
import com.mk.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry");
        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);

    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId() == id);
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }

}
