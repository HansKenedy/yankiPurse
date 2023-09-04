package com.proyecto.yankiPurse.controller;


import ch.qos.logback.core.net.server.Client;
import com.proyecto.yankiPurse.domain.Purse;
import com.proyecto.yankiPurse.service.PurseService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
@RestController
@RequestMapping("/v1/purse")
public class PurseController {
    @Autowired
    private PurseService purseService;

    @GetMapping
    public DeferredResult<ResponseEntity<List<?>>> findAll() {
        DeferredResult<ResponseEntity<List<?>>> deferredResult = new DeferredResult<>();
        purseService.findAll().toList()
                .subscribe(purseView -> deferredResult.setResult(ResponseEntity.accepted().body(purseView)), deferredResult::setErrorResult);
        return deferredResult;
    }

    @GetMapping("/{cellPhone}")
    public DeferredResult<ResponseEntity<List<?>>> findById(@PathVariable String cellPhone) {
        DeferredResult<ResponseEntity<List<?>>> deferredResult = new DeferredResult<>();
        purseService.findByCellPhone(cellPhone).toList()
                .subscribe(purseView -> deferredResult.setResult(ResponseEntity.accepted().body(purseView)), deferredResult::setErrorResult);
        return deferredResult;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Purse purse) {
        Purse savedPurse= purseService.save(purse);
        return ResponseEntity.ok().body(savedPurse);
    }

    @PutMapping(
            value = "/{purseId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<Purse>> updatePurse(@PathVariable(value = "purseId") String purseId,
                                                    @RequestBody Purse purseUp) {
        return purseService.updatePurse(toUpdatePurseRequest(purseId, purseUp))
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(Purse.builder().build()));
    }

    private Purse toUpdatePurseRequest(String purseId, Purse purseUp) {
        Purse purse = new Purse();
        BeanUtils.copyProperties(purseUp, purse);
        purse.setId(purseId);
        return purse;
    }
}
