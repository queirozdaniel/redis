package com.danielqueiroz.test;

import org.junit.Test;
import static org.junit.Assert.*;

import com.danielqueiroz.dao.PessoaDAO;
import com.danielqueiroz.model.Pessoa;

public class PessoaDAOTest {

	private PessoaDAO pessoaDao = new PessoaDAO();
	private Pessoa pessoa = new Pessoa("Daniel", "Queiroz");

	@Test
	public void test() {
		pessoaDao.persistirPessoa(pessoa);

		Pessoa persistida = pessoaDao.recuperaPessoa("pessoa:Daniel");
		assertNotNull(persistida);
		assertEquals(pessoa.getNome(), persistida.getNome());
		assertEquals(pessoa.getSobrenome(), persistida.getSobrenome());
	}
}
