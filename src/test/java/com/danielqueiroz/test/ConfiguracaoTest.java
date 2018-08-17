package com.danielqueiroz.test;

import org.junit.Test;
import static org.junit.Assert.*;
import com.danielqueiroz.config.Configuracao;

public class ConfiguracaoTest {

	Configuracao configuracao = new Configuracao();

	@Test
	public void testGetValor() {
		String chave = java.util.UUID.randomUUID().toString();
		String valor = java.util.UUID.randomUUID().toString();
		System.out.println(chave + ", " + valor);
		configuracao.setValor(chave, valor);
		String valorObtido = configuracao.getValor(chave);
		assertNotNull(valorObtido);
		assertEquals(valorObtido, valor);
	}

	@Test
	public void testIncrementar() {
		String chave = java.util.UUID.randomUUID().toString();
		long valor = configuracao.incrementarChave(chave);
		assertEquals(1l, valor);
		assertEquals(2l, configuracao.incrementarChave(chave));
	}

	@Test
	public void testIncrementarPor() {
		String chave = java.util.UUID.randomUUID().toString();
		long valor = configuracao.incrementarChave(chave);
		assertEquals(1l, valor);
		assertEquals(3l, configuracao.incrementarPorChave(chave, 2l));
	}

	@Test
	public void testGetTamanhoConfig() {
		String valor = java.util.UUID.randomUUID().toString();
		long tamanho = valor.length();
		String chave = java.util.UUID.randomUUID().toString();
		configuracao.setValor(chave, valor);
		assertEquals(tamanho, configuracao.getTamanhoConfig(chave));
	}

}
