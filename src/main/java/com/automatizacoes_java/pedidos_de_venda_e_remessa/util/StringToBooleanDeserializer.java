package com.automatizacoes_java.pedidos_de_venda_e_remessa.util;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StringToBooleanDeserializer extends JsonDeserializer<Boolean> {
	@Override
	public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		String value = p.getText();
		if (value == null) {
			return null;
		}
		if (Objects.equals("S", value.trim().toUpperCase())) {
			return Boolean.TRUE;
		}
		if (Objects.equals("N", value.trim().toUpperCase())) {
			return Boolean.FALSE;
		}
		// Retorna null (ou false, se preferir) para outros valores.
		return null;
	}
}