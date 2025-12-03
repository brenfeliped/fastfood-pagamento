package com.fastfood.infrastructure.persistence;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final JpaPagamentoRepository jpaPagamentoRepository;



    public PagamentoRepositoryImpl(JpaPagamentoRepository jpaPagamentoRepository) {
        this.jpaPagamentoRepository = jpaPagamentoRepository;
    }


    @Override
    public Pagamento save(Pagamento pagamento){

        JpaPagamentoEntity  jpaPagamentoEntity = jpaPagamentoRepository.save(JpaPagamentoEntity.fromDomain(pagamento));

        return jpaPagamentoEntity.toDomain();
    }

    @Override
    public Pagamento findById(UUID id) {
        if(jpaPagamentoRepository.findById(id).isPresent()) {
            JpaPagamentoEntity entity = jpaPagamentoRepository.findById(id).get();
            return entity.toDomain();
        }
        return null;
    }

    @Override
    public List<Pagamento> findAll() {
        List<JpaPagamentoEntity> listEntity = jpaPagamentoRepository.findAll();

        return listEntity.stream().map(JpaPagamentoEntity::toDomain).toList();
    }

    @Override
    public Pagamento findByPedidoId(UUID pedidoId) {
        JpaPagamentoEntity entity = jpaPagamentoRepository.findByPedidoId(pedidoId);
        return entity.toDomain();
    }

    @Override
    public List<Pagamento> findByStatus(EnumStatusPagamento status) {
        List<JpaPagamentoEntity> entity = jpaPagamentoRepository.findByStatus(status);

        return entity.stream().map(JpaPagamentoEntity::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {

        jpaPagamentoRepository.deleteById(id);
    }
}
