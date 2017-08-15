package br.com.caelum.agiletickets;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.caelum.agiletickets.models.Sessao;

public class FirstTest {

	@Test
	public void deveVender2IngerssosSeHa2Vagas() {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);
		assertTrue(sessao.podeReservar(2));
	}
}
