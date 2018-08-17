package com.danielqueiroz.test;

import org.junit.Test;

import com.danielqueiroz.config.Conexao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ConexaoTest {

	@Test
	public void testObterConexao() {
		Conexao conexao = new Conexao();
		// Obtendo uma conexao normalmente
		Jedis conexaoSimples = conexao.getConexao("localhost", 6379);
		conexaoSimples.set("jedis", "e massa");

		JedisPool pool = conexao.getJedisPool("localhost", 6379);

		Jedis conexaoDoPool = null;
		try {
			conexaoDoPool = pool.getResource();
			conexaoDoPool.set("valorTeste", "Jedis é muito facil");

		} finally {
			pool.returnResource(conexaoDoPool);
		}

	}

}
