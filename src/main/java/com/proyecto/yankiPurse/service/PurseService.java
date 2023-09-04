package com.proyecto.yankiPurse.service;

import com.proyecto.yankiPurse.domain.Purse;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public interface PurseService {
    Observable<?> findAll();
    Observable<Purse> findByCellPhone(String cellPhone);

    Purse save(Purse purse);

    Completable updatePurse(Purse purse);
}
