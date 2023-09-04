package com.proyecto.yankiPurse.service;

import com.proyecto.yankiPurse.domain.Purse;
import com.proyecto.yankiPurse.repository.PurseRepository;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PurseServiceImp implements PurseService{
    @Autowired
    private PurseRepository purseRepository;
    @Override
    public Observable<Purse> findAll() {
        return Observable.fromIterable(purseRepository.findAll()) ;
    }
    @Override
    public Observable<Purse> findByCellPhone(String cellPhone) {
        return Observable.fromIterable(purseRepository.findAll()).filter(purse -> purse.getCellPhone().equals(cellPhone));
    }

    @Override
    public Purse save(Purse purse) {
        return purseRepository.save(purse);
    }

    @Override
    public Completable updatePurse(Purse purse) {
        return updatePurseToRepository(purse);
    }

    private Completable updatePurseToRepository(Purse purse) {
        return Completable.create(completableSubscriber -> {
            Optional<Purse> optionalPurse = purseRepository.findById(purse.getId());
            if (!optionalPurse.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                Purse purseUpdate = optionalPurse.get();
                purseUpdate.setAmount(purse.getAmount());
                purseRepository.save(purseUpdate);
                completableSubscriber.onComplete();
            }
        });
    }
}
