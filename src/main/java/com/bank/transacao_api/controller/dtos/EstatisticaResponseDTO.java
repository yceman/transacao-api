package com.bank.transacao_api.controller.dtos;

public record EstatisticaResponseDTO(Long count, Double sum, Double avg, Double min, Double max) {
}
