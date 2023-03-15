package co.com.neoris.minibank.app;

import co.com.neoris.minibank.app.dtos.ClienteDTO;
import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.enums.EstadoCliente;
import co.com.neoris.minibank.app.enums.Genero;
import co.com.neoris.minibank.app.utils.Constantes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MinibankApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	ClienteDTO clienteDTO;

	ClienteDTO otherClienteDTO;

	CuentaDTO cuentaDTO;

	CuentaDTO sendCuentaDTO;

	@BeforeEach
	void init(){
		clienteDTO = ClienteDTO.builder()
				.identificacion("8512")
				.nombres("Ranger Okio")
				.genero(Genero.Masculino)
				.edad(25)
				.direccion("Otawa Street 54")
				.telefono("097521489")
				.contrasena("4523")
				.estado(EstadoCliente.True).build();

		otherClienteDTO = ClienteDTO.builder()
				.identificacion("8512")
				.nombres("Pablo pantanela")
				.genero(Genero.Masculino)
				.edad(15)
				.direccion("Otawa Street 51")
				.telefono("09122145")
				.contrasena("3123")
				.estado(EstadoCliente.True).build();

		cuentaDTO=CuentaDTO.builder()
				.estadoCuenta("true")
				.numeroDeCuenta(225487)
				.saldoInicial(new BigDecimal(30000))
				.tipoCuenta("ahorro")
				.cliente(ClienteDTO.builder()
						.identificacion("8512")
						.build())
				.build();

		sendCuentaDTO = CuentaDTO.builder()
				.numeroDeCuenta(225487)
				.credito(new BigDecimal(20000))
				.debito(new BigDecimal(15000)).build();
	}

	@Test
	@Order(value = 1)
	void guardarClienteDeberiaAlmacenarCorrectamente() throws Exception {

		mvc.perform(
				MockMvcRequestBuilders.post("/api/v1/clientes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(clienteDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.clienteId").exists())
				.andReturn();
	}

	@Test
	@Order(value = 2)
	void guardarClienteYaGuaradadoDeberiaRetornarExcepcion() throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/api/v1/clientes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(otherClienteDTO)))
				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.mensaje", is(Constantes.OOPS_ERROR_AL_GUARDAR_USUARIO)));

	}

	@Test
	@Order(value = 3)
	void guardarCuentaDeberiaAlmacenarCorrectamente()throws Exception{

		mvc.perform(
				MockMvcRequestBuilders.post("/api/v1/cuentas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(cuentaDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cuentaId").exists());
	}

	@Test
	void consultarCuentaPorClienteDeberiaRetornaSuCuentaCorrectamente()throws Exception{

		mvc.perform(MockMvcRequestBuilders
				.get("/api/v1/cuentas/cliente/" + clienteDTO.getIdentificacion())
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].cuentaId", is(1)))
				.andExpect(jsonPath("$[0].estadoCuenta", is("True")))
				.andExpect(jsonPath("$[0].numeroDeCuenta", is(225487)))
				.andExpect(jsonPath("$[0].saldoInicial", is(30000.00)))
				.andExpect(jsonPath("$[0].tipoCuenta", is("Ahorro")))
				.andExpect(jsonPath("$[0].cliente.identificacion", is("8512")));

	}

	@Test
	void solicitarCreditoDeberiaRetornarOK()throws Exception{

		mvc.perform(
				MockMvcRequestBuilders.post("/api/v1/movimientos/credito")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(sendCuentaDTO)))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void solicitarDebitoDeberiaRetornarOK()throws Exception{

		mvc.perform(
				MockMvcRequestBuilders.post("/api/v1/movimientos/debito")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(sendCuentaDTO)))
				.andExpect(status().isOk())
				.andReturn();
	}
}
