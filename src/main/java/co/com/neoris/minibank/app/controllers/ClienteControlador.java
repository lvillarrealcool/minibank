package co.com.neoris.minibank.app.controllers;

import co.com.neoris.minibank.app.dtos.ClienteDTO;
import co.com.neoris.minibank.app.exceptions.ApiResponseException;
import co.com.neoris.minibank.app.services.ICliente;
import co.com.neoris.minibank.app.services.impl.ClienteImpl;
import co.com.neoris.minibank.app.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteControlador {

    private ICliente iCliente;

    @Autowired
    public ClienteControlador(ClienteImpl clienteImpl) {
        this.iCliente = clienteImpl;
    }

    @GetMapping
    public ResponseEntity<?> consultarTodos(){
        try{
            List<ClienteDTO> resp= iCliente.consultarTodos();
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_CONSULTAR_TODOS_LOS_USUARIOS);
        }
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<?> consultarPorIdentificacion(@PathVariable String identificacion){
        try{
            ClienteDTO resp=iCliente.buscarPorIdentificacion(identificacion);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_CONSULTAR_USUARIO_POR_IDENTIFICACION);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ClienteDTO clienteDTO){
        try{
            iCliente.guardar(clienteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_GUARDAR_USUARIO);
        }
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody ClienteDTO clienteDTO){
        try{
            iCliente.guardar(clienteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(clienteDTO);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_ACTUALIZAR_USUARIO);
        }
    }

    @DeleteMapping("/{identificacion}")
    public ResponseEntity<?> eliminar(@PathVariable String identificacion){
        try{
            iCliente.eliminarPorIdentificacion(identificacion);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ApiResponseException(Constantes.OOPS_ERROR_AL_ELIMINAR_USUARIO);
        }
    }
}
