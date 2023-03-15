package co.com.neoris.minibank.app.controllers;

import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;
import co.com.neoris.minibank.app.exceptions.ApiResponseException;
import co.com.neoris.minibank.app.services.IMovimientos;
import co.com.neoris.minibank.app.services.impl.MovimientoImpl;
import co.com.neoris.minibank.app.utils.Constantes;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
public class MovimientoControlador {

    private IMovimientos iMovimientos;

    @Autowired
    public MovimientoControlador(MovimientoImpl movimientoImpl) {
        this.iMovimientos = movimientoImpl;
    }

    @PostMapping("/credito")
    public ResponseEntity<?> credito(@RequestBody CuentaDTO cuentaDTO){
        try{
            iMovimientos.credito(cuentaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_SOLICITAR_CREDITO);
        }
    }

    @PostMapping("/debito")
    public ResponseEntity<?> debito(@RequestBody CuentaDTO cuentaDTO){
        try{
            iMovimientos.debito(cuentaDTO);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_SOLICITAR_DEBITO);
        }
    }

    @GetMapping("/reportes")
    public ResponseEntity<?> consultarMovimientosPorNumero(@RequestParam("desde")
                                                           @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate desde,
                                                           @RequestParam("hasta")
                                                           @DateTimeFormat(pattern="dd/MM/yyyy")LocalDate hasta,
                                                           @RequestParam("identificacioncliente") String identificacioncliente){
        try{
            List<MovimientoDTO> resp=iMovimientos.consultarMovimientosPorFechaYCliente(desde,hasta,identificacioncliente);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_CONSULTAR_ESTADO_DE_CUENTA);
        }
    }
}
