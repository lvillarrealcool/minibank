package co.com.neoris.minibank.app.controllers;

import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;
import co.com.neoris.minibank.app.exceptions.ApiResponseException;
import co.com.neoris.minibank.app.services.ICuenta;
import co.com.neoris.minibank.app.services.impl.CuentaImpl;
import co.com.neoris.minibank.app.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cuentas")
public class CuentaControlador {

    private ICuenta iCuenta;

    @Autowired
    public CuentaControlador(CuentaImpl cuentaImpl) {
        this.iCuenta = cuentaImpl;
    }

    @GetMapping("/cliente/{identificacioncliente}")
    public ResponseEntity<?> consultarPorCliente(@PathVariable String identificacioncliente){
        try{
            List<CuentaDTO> cuentas=iCuenta.consultarPorCliente(identificacioncliente);
            return ResponseEntity.status(HttpStatus.OK).body(cuentas);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_CONSULTAR_CUENTA_POR_CLIENTE);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarCuenta(@RequestBody CuentaDTO cuentaDTO){
        try{
            CuentaDTO resp=iCuenta.guardar(cuentaDTO);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_GUARDAR_CUENTA);
        }
    }

    @DeleteMapping("/{numerocuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable long numerocuenta){
        try{
            iCuenta.eliminar(numerocuenta);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_ELIMINAR_CUENTA);
        }
    }

}
