package com.bank.transacao_api.business.services;

import com.bank.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.bank.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {
    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionarTransacoes(TransacaoRequestDTO dto) {
        log.info("Iniciado o processamento de gravar transações" + dto);
        if (dto.dataHora().isAfter(OffsetDateTime.now())) {
            log.error("Data e hora maiores que o atual");
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atual");
        }
        if (dto.valor() < 0) {
            log.error("Valor não pode ser menor que 0");
            throw new UnprocessableEntity("Valor não pode ser menor que 0");
        }
        listaTransacoes.add(dto);
        log.info("Transações adicionadas com sucesso");
    }

    public void limparTransacoes() {
        log.info("Iniciado processamento para deletar transações");
        listaTransacoes.clear();
        log.info("Transações deletadas com sucesso");
    }

    public List<TransacaoRequestDTO> buscarTransacoes(Integer intervaloBuscaTransacao) {
        log.info("Iniciado as buscas de transações por tempo" + intervaloBuscaTransacao);
        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(intervaloBuscaTransacao);
        log.info("Transações retornadas com sucesso");
        return listaTransacoes.stream().filter(transacoes -> transacoes.dataHora().isAfter(dataHoraIntervalo)).toList();
    }
}
