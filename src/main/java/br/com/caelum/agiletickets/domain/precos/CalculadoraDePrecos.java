package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;

		if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.CINEMA)
				|| sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.SHOW)) {
			// quando estiver acabando os ingressos...

			if (porcentagemIngresosDisponiveis(sessao) <= 0.05) {
				preco = reajustePrecoIngressoPercentual(0.10, sessao.getPreco());
			} else {
				preco = sessao.getPreco();
			}
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.BALLET)) {
			if (porcentagemIngresosDisponiveis(sessao) <= 0.50) {
				preco = reajustePrecoIngressoPercentual(0.20, sessao.getPreco());
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = reajustePrecoIngressoPercentual(0.10, sessao.getPreco());
			}
		} else if (sessao.getEspetaculo().getTipo().equals(TipoDeEspetaculo.ORQUESTRA)) {
			if (porcentagemIngresosDisponiveis(sessao) <= 0.50) {
				preco = reajustePrecoIngressoPercentual(0.20, sessao.getPreco());
			} else {
				preco = sessao.getPreco();
			}

			if (sessao.getDuracaoEmMinutos() > 60) {
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} else {
			// nao aplica aumento para teatro (quem vai é pobretão :-P )
			preco = sessao.getPreco();
		}

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
   /**
    * @param porcentagem
    * @param precoBase
    * @return
    */
	private static BigDecimal reajustePrecoIngressoPercentual(double porcentagem, BigDecimal precoBase) {
		BigDecimal precoReajustado;
		BigDecimal reajuste = precoBase.multiply(BigDecimal.valueOf(porcentagem));

		precoReajustado = precoBase.add(reajuste);

		return precoReajustado;
	}

	private static double porcentagemIngresosDisponiveis(Sessao sessao) {
		int ingressosLivres = sessao.getTotalIngressos() - sessao.getIngressosReservados();
		double porcentagemIngressosLivres = ingressosLivres / sessao.getTotalIngressos().doubleValue();

		return porcentagemIngressosLivres;
	}
}