package com.danielqueiroz.dao;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.danielqueiroz.config.Conexao;
import com.danielqueiroz.model.Compra;
import com.danielqueiroz.model.Pessoa;

import redis.clients.jedis.Jedis;

public class CompraDAO {

	private Jedis jedis = new Conexao().getConexao("localhost", 6379);

	public void persistirCompra(Pessoa pessoa, Compra compra) {
		String chave = "pessoa:compras:" + pessoa.getNome();
		String valor = compraParaJson(compra);
		jedis.rpush(chave, valor);
	}

	public List<Compra> obterCompras(Pessoa pessoa, long inicio, long itens) throws ParseException {
		List<String> compras_redis = jedis.lrange("pessoa:compras:" + pessoa.getNome(), inicio, itens);

		List<Compra> compras = new ArrayList<Compra>();
		for (String c : compras_redis) {
			Compra compra = JsonParaCompra(c);
			compras.add(compra);
		}
		return compras;
	}

	@SuppressWarnings("unchecked")
	public String compraParaJson(Compra compra) {
		JSONObject object = new JSONObject();
		object.put("produto", compra.getProduto());
		object.put("valorPago", compra.getValorPago());
		return object.toJSONString();
	}

	public Compra JsonParaCompra(String jsonCompra) throws ParseException {
		JSONObject object = (JSONObject) new JSONParser().parse(jsonCompra);

		Compra compra = new Compra();
		compra.setProduto((String) object.get("produto"));
		compra.setValorPago((Double) object.get("valorPago"));

		return compra;
	}
}
