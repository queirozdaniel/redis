package com.danielqueiroz.config;

import redis.clients.jedis.Jedis;

public class Configuracao {

	private Jedis jedis;

	private Jedis getConexao() {
		if (jedis == null) {
			jedis = new Conexao().getConexao("localhost", 6379);
		}
		return jedis;
	}

	public String getValor(String chave) {
		return getConexao().get(chave);
	}

	public void setValor(String chave, String valor) {
		getConexao().set(chave, valor);
	}

	public long incrementarChave(String chave) {
		return getConexao().incr(chave);
	}

	public long incrementarPorChave(String chave, long valor) {
		return getConexao().incrBy(chave, valor);
	}

	public long getTamanhoConfig(String chave) {
		return getConexao().strlen(chave);
	}
	
}
