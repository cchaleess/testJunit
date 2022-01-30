package org.carlosparedes.test.pringboot.app;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.carlosparedes.test.pringboot.app.Datos.*;
import org.carlosparedes.test.pringboot.app.exceptions.DineroInsuficienteException;
import org.carlosparedes.test.pringboot.app.models.Banco;
import org.carlosparedes.test.pringboot.app.models.Cuenta;
import org.carlosparedes.test.pringboot.app.repositories.BancoRepository;
import org.carlosparedes.test.pringboot.app.repositories.CuentaRepository;
import org.carlosparedes.test.pringboot.app.services.CuentaService;
import org.carlosparedes.test.pringboot.app.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootTestApplicationTests {

	@MockBean
	CuentaRepository cuentaRepository;
	@MockBean
	BancoRepository bancoRepository;
	@Autowired
	CuentaService service;

	@BeforeEach
	void setUp(){
	//	cuentaRepository = mock(CuentaRepository.class);
	//	bancoRepository = mock(BancoRepository.class);
	//	service = new CuentaServiceImpl(cuentaRepository,bancoRepository);
	//	Datos.CUENTA_001.setSaldo(new BigDecimal("1000")); //Tambien puedo crear metodos estaticos en la clase para reiniciar los valores
	//	Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
	//	Datos.BANCO.setTotalTransferencias(0);

	}

	@Test
	void contextLoads() {

		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal cuentaOrigen = service.revisarSaldo(1L);
		BigDecimal cuentaDestino = service.revisarSaldo(2L);

		assertEquals("1000", cuentaOrigen.toPlainString());
		assertEquals("2000", cuentaDestino.toPlainString());

		service.transferir(1L,2L,new BigDecimal("100"),1L);

		 cuentaOrigen = service.revisarSaldo(1L);
		 cuentaDestino = service.revisarSaldo(2L);

		 assertEquals("900",cuentaOrigen.toPlainString());   //quitar 100 de la cuenta 1
		 assertEquals("2100", cuentaDestino.toPlainString()); //agregar 100 a la cuenta 2

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1,total);

		verify(cuentaRepository,times(3)).findById(1L);
		verify(cuentaRepository,times(3)).findById(2L);
		verify(cuentaRepository,times(2)).save(any(Cuenta.class));

		verify(bancoRepository,times(2)).findById(1L);
		verify(bancoRepository).save(any(Banco.class));


		verify(cuentaRepository,never()).findAll();
		verify(cuentaRepository,times(6)).findById(anyLong());

	}

	@Test
	void contextLoads2() {

		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco());

		BigDecimal cuentaOrigen = service.revisarSaldo(1L);
		BigDecimal cuentaDestino = service.revisarSaldo(2L);

		assertEquals("1000", cuentaOrigen.toPlainString());
		assertEquals("2000", cuentaDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class, () -> {
			service.transferir(1L,2L,new BigDecimal("1200"),1L);

		});

		cuentaOrigen = service.revisarSaldo(1L);
		cuentaDestino = service.revisarSaldo(2L);

		assertEquals("1000",cuentaOrigen.toPlainString());
		assertEquals("2000", cuentaDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(0,total);

		verify(cuentaRepository,times(3)).findById(1L);
		verify(cuentaRepository,times(2)).findById(2L);
		verify(cuentaRepository,never()).save(any(Cuenta.class));

		verify(bancoRepository,times(1)).findById(1L);
		verify(bancoRepository, never()).save(any(Banco.class));

		verify(cuentaRepository,times(5)).findById(anyLong());
		verify(cuentaRepository,never()).findAll();
	}
	@Test
	void contextLoads3(){

		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = service.findById(1L);
		Cuenta cuenta2 = service.findById(1L);

		assertSame(cuenta1,cuenta2);
		assertTrue(cuenta1 == cuenta2);
		assertEquals("Andrés", cuenta1.getPersona());
		assertEquals("Andrés", cuenta2.getPersona());

		verify(cuentaRepository, times(2)).findById(1L);
	}
	@Test
	void testFindAll(){

		//Given
		List<Cuenta> datos = Arrays.asList(crearCuenta001().orElseThrow(), crearCuenta002().orElseThrow());
		when(cuentaRepository.findAll()).thenReturn(datos);
		//When
		List<Cuenta> cuentas = service.findAll();
		//Then
		assertFalse(cuentas.isEmpty());
		assertEquals(2,cuentas.size());
		assertTrue(cuentas.contains(crearCuenta002().orElseThrow()));

		verify(cuentaRepository).findAll();
	}

	@Test
	void testSave() {
		//Given
		Cuenta cuentaPepe = new Cuenta(null, "Pepe", new BigDecimal("3000"));
		when(cuentaRepository.save(any())).then(invocation -> {
			Cuenta c = invocation.getArgument(0);
			c.setId(3L);
			return c;
		});

		//WHen
		Cuenta cuenta = service.save(cuentaPepe);
		//Then
		assertEquals("Pepe", cuenta.getPersona());
		assertEquals(3, cuenta.getId());
		assertEquals("3000", cuenta.getSaldo().toPlainString());
	}
}
