package com.danielqueiroz.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 	Classe responsavel por obter uma conexão com o Redis
 * */

public class Conexao {

	public Jedis getConexao(String host, int port) {
		return new Jedis(host,port);
	}
	
	public Jedis getConexao(String host, int port, int timeout) {
		return new Jedis(host,port,timeout);
	}
	
	
	public JedisPool getJedisPool(String host, int port) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMinIdle(4);
		
		return new JedisPool(config, host, port);
	}
	
}
