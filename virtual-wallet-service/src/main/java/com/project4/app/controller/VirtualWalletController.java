package com.project4.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project4.app.entity.VirtualWallet;
import com.project4.app.service.VirtualWalletService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/virtualWallet")
public class VirtualWalletController {
	
	@Autowired
    private VirtualWalletService vwService;

    @GetMapping("/findAll")
    public Flux<VirtualWallet> getDeposits(){
        return vwService.findAll();
    }

    @GetMapping("/find/{id}")
    public Mono<VirtualWallet> getDeposit(@PathVariable String id){
        return vwService.findById(id);
    }

    @PostMapping("/create")
    public Mono<VirtualWallet> createDeposit(@RequestBody VirtualWallet vw){
        return vwService.create(vw);
    }

    @PutMapping("/update/{id}")
    public Mono<VirtualWallet> updateDeposit(@RequestBody VirtualWallet vw, @PathVariable String id){
        return vwService.update(vw,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteDeposit(@PathVariable String id){
                return vwService.delete(id);
    }

}
