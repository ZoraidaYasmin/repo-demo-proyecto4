package com.project4.app.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project4.app.entity.VirtualWallet;
import com.project4.app.repository.VirtualWalletRepository;
import com.project4.app.service.VirtualWalletService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VirtualWalletServiceImpl implements VirtualWalletService {

	@Autowired
	VirtualWalletRepository vwRepository;
	
	@Override
	public Flux<VirtualWallet> findAll() {
		return vwRepository.findAll();
	}

	@Override
	public Mono<VirtualWallet> create(VirtualWallet virtualWallet) {
		return vwRepository.save(virtualWallet);
	}

	@Override
	public Mono<VirtualWallet> findById(String id) {
		return vwRepository.findById(id);
	}

	@Override
	public Mono<VirtualWallet> update(VirtualWallet virtualWallet, String id) {
        return vwRepository.findById(id)
                .map( x -> {
                    x.setAmount(x.getAmount());
                    x.setCardNumber(x.getCardNumber());
                    x.setCellphone(x.getCellphone());
                    x.setDni(x.getDni());
                    x.setOperation(x.getOperation());
                    return x;
                }).flatMap(vwRepository::save);
	}

	@Override
	public Mono<Void> delete(String id) {
		return vwRepository.deleteById(id);
	}
	
}
