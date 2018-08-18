package com.danielqueiroz.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import com.danielqueiroz.model.Pessoa;
import com.danielqueiroz.util.SerializadorRedis;

public class SerializadorObjetosTest {
	private Pessoa pessoa;
	private SerializadorRedis serializador;

	@Before
	public void before() {
		serializador = new SerializadorRedis();
		pessoa = new Pessoa("Daniel", "Queiroz");
	}

	@Test
	public void testSetObjeto() throws Exception {
		serializador.setObjeto("pessoa:danielQueiroz", pessoa);
		Pessoa serializado = (Pessoa) serializador.getObjeto("pessoa:danielQueiroz");
		assertNotNull(serializado);
		assertEquals(pessoa.getNome(), serializado.getNome());
		assertEquals(pessoa.getSobrenome(), serializado.getSobrenome());
	}

	}
