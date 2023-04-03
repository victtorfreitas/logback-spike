package br.com.spikelogbackintercept.adapters.out.repository;

import br.com.spikelogbackintercept.adapters.out.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    // TODO: 10/03/2023  List<Payment> findAllByActiveIsTrue();
}
