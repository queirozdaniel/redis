package com.danielqueiroz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.After;
import org.junit.Test;

import com.danielqueiroz.config.Conexao;
import com.danielqueiroz.dao.NotaFiscalDAO;
import com.danielqueiroz.model.NotaFiscal;
import com.danielqueiroz.model.Pessoa;

public class NotaFiscalDAOTest {


	private final NotaFiscalDAO notaFiscalDao = new NotaFiscalDAO();
	private final Pessoa pessoa = new Pessoa("Daniel", "Queiroz");
	
	@After
	public void after() {
		new Conexao().getConexao("localhost", 6379).del("notaFiscal:" + pessoa.getNome());
	}
	
	

	@Test
	public void testPersistirNota() throws Exception {
		NotaFiscal nf = new NotaFiscal();
		nf.setCodigo("nota1");
		nf.setValor(20); 
		notaFiscalDao.persistirNota(pessoa, nf);
		
		Set<NotaFiscal> notasPersistidas = notaFiscalDao.getNotasPessoa(pessoa);
		assertNotNull(notasPersistidas);
		assertTrue(notasPersistidas.contains(nf));
		assertEquals(1, notasPersistidas.size());
		notaFiscalDao.persistirNota(pessoa, nf);
		notasPersistidas = notaFiscalDao.getNotasPessoa(pessoa);
		assertEquals(1, notasPersistidas.size());
		
		nf.setValor(10);
		notaFiscalDao.persistirNota(pessoa, nf);
		notasPersistidas = notaFiscalDao.getNotasPessoa(pessoa);
		assertEquals(2, notasPersistidas.size());
	}
	
	@Test
	public void testRemoverNota() throws Exception {
		NotaFiscal nf1 = new NotaFiscal("nota1", 23);
		NotaFiscal nf2 = new NotaFiscal("nota2", 34);
		notaFiscalDao.persistirNota(pessoa, nf1);
		notaFiscalDao.persistirNota(pessoa, nf2);
		Set<NotaFiscal> notas = notaFiscalDao.getNotasPessoa(pessoa);
		assertTrue(notas.contains(nf1));
		assertTrue(notas.contains(nf2));
		assertEquals(2, notas.size());
		notaFiscalDao.removerNota(pessoa, nf1);
		notas = notaFiscalDao.getNotasPessoa(pessoa);
		assertFalse(notas.contains(nf1));
		assertTrue(notas.contains(nf2));
		assertEquals(1, notas.size());
	}
	
	@Test
	public void testeOrdenado() throws Exception {
		NotaFiscal nf1 = new NotaFiscal("nota1", 23);
		NotaFiscal nf2 = new NotaFiscal("nota2", 34);
		NotaFiscal nf3 = new NotaFiscal("nota3", 80);
		NotaFiscal nf4 = new NotaFiscal("nota4", 100);
		
		notaFiscalDao.persistirNotaOrdenada(pessoa, nf1);
		notaFiscalDao.persistirNotaOrdenada(pessoa, nf2);
		notaFiscalDao.persistirNotaOrdenada(pessoa, nf3);
		notaFiscalDao.persistirNotaOrdenada(pessoa, nf4);
		
		Set<NotaFiscal> notas = notaFiscalDao.obterNotasOrdenadas(pessoa, 0, 1000);
		assertTrue(notas.contains(nf1));
		assertTrue(notas.contains(nf2));
		assertTrue(notas.contains(nf3));
		assertTrue(notas.contains(nf4));
		
		notas = notaFiscalDao.obterNotasOrdenadas(pessoa, 34, 80);
		assertTrue(notas.contains(nf2));
		assertTrue(notas.contains(nf3));
		
		notaFiscalDao.removerNotasOrdenadas(pessoa, 33, 81);
		notas = notaFiscalDao.obterNotasOrdenadas(pessoa, 0, 1000);
		assertFalse(notas.contains(nf2));
		assertFalse(notas.contains(nf3));
		assertTrue(notas.contains(nf1));
		assertTrue(notas.contains(nf4));
		
	}

}
	
	
}
