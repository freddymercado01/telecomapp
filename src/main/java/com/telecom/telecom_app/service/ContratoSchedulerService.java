package com.telecom.telecom_app.service;

import com.telecom.telecom_app.model.Contrato;
import com.telecom.telecom_app.model.EstadoContrato;
import com.telecom.telecom_app.model.EstadoFactura;
import com.telecom.telecom_app.model.Factura;
import com.telecom.telecom_app.repository.ContratoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ContratoSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(ContratoSchedulerService.class);
    private static final String MOTIVO = "INACTIVADO_AUTOMATICO_FACTURA_PENDIENTE_1_MES";

    private final ContratoRepository contratoRepository;

    public ContratoSchedulerService(ContratoRepository contratoRepository) {
        this.contratoRepository = contratoRepository;
    }

    /**
     * Ejecuta diariamente a la 1 AM.
     * Inactiva contratos activos cuya primera factura lleva más de 1 mes pendiente de pago.
     */
    //@Scheduled(cron = "0 0 1 * * *") // a la 1am de cada dia

    @Scheduled(cron = "0 0 * * * *") //cada hora

    
    @Transactional
    public void inactivarPorFacturaPendiente() {
        LocalDate hoy = LocalDate.now();
        List<Contrato> activos = contratoRepository.findByEstado(EstadoContrato.ACTIVO);

        for (Contrato contrato : activos) {
            LocalDate fechaInicio = contrato.getFechaInicio();
            if (fechaInicio == null) continue;

            // Solo aplica si ha pasado más de 1 mes desde la activación
            if (hoy.isBefore(fechaInicio.plusMonths(1))) continue;

            List<Factura> facturas = contrato.getFacturas();
            if (facturas.isEmpty()) continue;

            // Evalúa la primera factura emitida
            Factura primeraFactura = facturas.stream()
                    .min(Comparator.comparing(Factura::getFechaEmision))
                    .orElse(null);

            if (primeraFactura != null && primeraFactura.getEstado() == EstadoFactura.PENDIENTE) {
                contrato.setEstado(EstadoContrato.SUSPENDIDO);
                contrato.setMotivoInactivacion(MOTIVO);
                contratoRepository.save(contrato);
                log.info("Contrato {} inactivado automáticamente: primera factura pendiente con más de 1 mes.",
                        contrato.getIdContrato());
            }
        }
    }
}
