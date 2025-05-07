package com.bank.transacao_api.business.services;

import com.bank.transacao_api.controller.dtos.EstatisticaResponseDTO;
import com.bank.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticaService {
    public final TransacaoService transacaoService;
    public EstatisticaResponseDTO calcularEstatisticasTransacoes(Integer intervaloBuscaTransacao) {

        log.info("Iniciada busca de estatísticas de transações pelo período de tempo" + intervaloBuscaTransacao);
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(intervaloBuscaTransacao);

        if (transacoes.isEmpty()) {
            return new EstatisticaResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream().mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();
        log.info("Estatísticas retornadas com sucesso");
        return new EstatisticaResponseDTO(estatisticasTransacoes.getCount(), estatisticasTransacoes.getSum(), estatisticasTransacoes.getAverage(), estatisticasTransacoes.getMin(), estatisticasTransacoes.getMax());
    }
}
