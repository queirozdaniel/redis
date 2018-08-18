package com.danielqueiroz.dao;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.danielqueiroz.config.Conexao;
import com.danielqueiroz.model.NotaFiscal;
import com.danielqueiroz.model.Pessoa;

import redis.clients.jedis.Jedis;

public class NotaFiscalDAO {

	private Jedis jedis = new Conexao().getConexao("localhost", 6379);

	@SuppressWarnings("unchecked")
	public String notaFiscalParaJSON(NotaFiscal nota) {
		// {codigo:"nota1", valor:"20.0"}
		if (nota != null) {
			JSONObject json = new JSONObject();
			json.put("codigo", nota.getCodigo());
			json.put("valor", nota.getValor());
			return json.toJSONString();
		}
		return null;
	}

	public NotaFiscal jsonParaNotaFiscal(String valor) throws ParseException {
		if (valor != null) {
			JSONObject json = (JSONObject) new JSONParser().parse(valor);
			NotaFiscal nota = new NotaFiscal();
			nota.setCodigo(json.get("codigo").toString());
			nota.setValor((Double) json.get("valor"));
			return nota;
		}
		return null;
	}

	private String getChave(Pessoa pessoa) {
		return "notaFiscal:" + pessoa.getNome();
	}

	public Set<NotaFiscal> getNotasPessoa(Pessoa pessoa) throws ParseException {
		String chave = getChave(pessoa);

		Set<String> set = jedis.smembers(chave);

		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		for (String nota_str : set) {
			notas.add(jsonParaNotaFiscal(nota_str));
		}
		return notas;
	}

	public void persistirNota(Pessoa pessoa, NotaFiscal nota) {
		String chave = getChave(pessoa);
		jedis.sadd(chave, notaFiscalParaJSON(nota));

	}

	public void removerNota(Pessoa pessoa, NotaFiscal nota) {
		if (pessoa != null && nota != null) {
			String toJSON = notaFiscalParaJSON(nota);
			jedis.srem(getChave(pessoa), toJSON);

		}
	}

	public void persistirNotaOrdenada(Pessoa pessoa, NotaFiscal nota) {
		String chave = getChave(pessoa);
		// score, nota, peso
		jedis.zadd(chave, nota.getValor(), notaFiscalParaJSON(nota));

	}

	public Set<NotaFiscal> obterNotasOrdenadas(Pessoa pessoa, double minimo, double maximo) throws ParseException {
		Set<NotaFiscal> resultado = new HashSet<NotaFiscal>();
		Set<String> rangeByScore = jedis.zrangeByScore(getChave(pessoa), minimo, maximo);
		for (String str : rangeByScore) {
			resultado.add(jsonParaNotaFiscal(str));
		}
		return resultado;
	}

	public void removerNotasOrdenadas(Pessoa pessoa, double minimo, double maximo) {
		jedis.zremrangeByScore(getChave(pessoa), minimo, maximo);

	}

}
