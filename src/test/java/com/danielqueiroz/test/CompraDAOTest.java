package com.danielqueiroz.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import com.danielqueiroz.dao.CompraDAO;
import com.danielqueiroz.model.Compra;
import com.danielqueiroz.model.Pessoa;

public class CompraDAOTest {
	private Pessoa pessoa = new Pessoa("Daniel", "Queiroz");
	private CompraDAO daoCompra = new CompraDAO();
	
	
	
	@Test
	public void testPersistirCompra() throws Exception {
		Compra compra1 = new Compra("Computador", 1000);
		Compra compra2 = new Compra("Tablet", 800);
		daoCompra.persistirCompra(pessoa, compra1);
		daoCompra.persistirCompra(pessoa, compra2);
		List<Compra> compras = daoCompra.obterCompras(pessoa, 0, 10);
		assertNotNull(compras);
		assertFalse(compras.isEmpty());
		assertTrue(compras.contains(compra1));
		assertTrue(compras.contains(compra2));
	}

}
