package com.danielqueiroz.dao;

import java.util.Map;

import com.danielqueiroz.config.Conexao;
import com.danielqueiroz.model.Pessoa;

import redis.clients.jedis.Jedis;

public class PessoaDAO {

	Jedis jedis = new Conexao().getConexao("localhost", 6379);
	
	public void persistirPessoa(Pessoa pessoa) {
		String chave =  "pessoa:" +pessoa.getNome();
		
		jedis.hset(chave, "nome", pessoa.getNome());
		jedis.hset(chave, "sobrenome", pessoa.getSobrenome());
	}

	public Pessoa recuperaPessoa(String chave) {
		if (jedis.exists(chave)) {
			Pessoa pessoa = new Pessoa();
			pessoa.setNome(jedis.hget(chave, "nome"));
			pessoa.setSobrenome(jedis.hget(chave, "sobrenome"));
			
			return pessoa;
		}
		return null;
	}
	
	public Pessoa recuperaPessoaPerformatica(String chave) {
		if (jedis.exists(chave)) {
			Map<String, String> dados = jedis.hgetAll(chave);
			
			Pessoa pessoa = new Pessoa();
			pessoa.setNome(dados.get("nome"));
			pessoa.setSobrenome(dados.get("sobrenome"));
			
			return pessoa;
		}
		return null;
	}
	
}
