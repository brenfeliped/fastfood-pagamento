package com.fastfood.application.usecase;

import com.fastfood.domain.pagamento.EnumStatusPagamento;
import com.fastfood.domain.pagamento.Pagamento;
import com.fastfood.domain.pagamento.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;

    }

    public Pagamento salvarPagamento(Pagamento pagamento){
        return  pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> listarTodosPagamentos(){
        return pagamentoRepository.findAll();
    }

    public Pagamento buscarPorId(UUID id){
        return pagamentoRepository.findById(id).get();
    }

    public Pagamento buscarPorPedidoId(UUID id){
        return pagamentoRepository.findByPedidoId(id).get();
    }

    public List<Pagamento> buscarPorStatus(EnumStatusPagamento status){

        return pagamentoRepository.findByStatus(status);
    }
}
