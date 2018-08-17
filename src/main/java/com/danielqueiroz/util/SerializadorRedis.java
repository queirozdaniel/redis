package com.danielqueiroz.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.danielqueiroz.config.Conexao;

import redis.clients.jedis.Jedis;

public class SerializadorRedis {

	private Jedis jedis;

	private Jedis getConexao() {
		if (jedis == null) {
			jedis = new Conexao().getConexao("localhost", 6379);
		}
		return jedis;
	}

	private byte[] objetoParaBytes(Serializable serializable) throws IOException {
		ByteArrayOutputStream baOS = null;
		ObjectOutputStream oos = null;
		byte[] resultado = null;
		try {
			baOS = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baOS);
			oos.writeObject(serializable);
			resultado = baOS.toByteArray();
		} finally {
			baOS.close();
			oos.close();
		}
		return resultado;
	}

	private Serializable bytesParaObjeto(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream baIS = null;
		ObjectInputStream ois = null;
		try {
			baIS = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(baIS);
			return (Serializable) ois.readObject();
		} finally {
			baIS.close();
			ois.close();
		}
	}

	public void setObjeto(String chave, Serializable objeto) throws IOException {
		byte[] bytes = objetoParaBytes(objeto);

		getConexao().set(chave.getBytes(), bytes);
	}

	public Serializable getObjeto(String chave) throws ClassNotFoundException, IOException {

		byte[] bytes = getConexao().get(chave.getBytes());
		return (Serializable) bytesParaObjeto(bytes);
	}

}
