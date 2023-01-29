(ns hospital.logic
	(:require [hospital.model :as h.model]
						[schema.core :as s]))

; existe um problema de condicional quando o departamento nao existe
;(defn cabe-na-fila?
;	 [hospital departamento]
;
;	 (-> hospital
;			 departamento
;			 count
;			 (< 5)))

; versao com if-let
; caso a variavel seja definida como nil, nao executa a lógica do if, faz o else que nesse caso nao existe
;(defn cabe-na-fila?
;	 [hospital departamento]
;
;	 (if-let [fila (get hospital departamento)]
;			(-> fila
;					count
;					(< 5))))

; funciona para o caso de nao ter departamento
; nao precisa de comportamento else igual o if
(defn cabe-na-fila?
	[hospital departamento]

	(when-let [fila (get hospital departamento)]
		(-> fila
				count
				(< 5))))

; funciona mas usa o some-> thread
; mesnos explicito
; qualquer um que der nil, devolve nil
;(defn cabe-na-fila?
;	 [hospital departamento]
;
;	 (some-> hospital
;					 departamento
;					 count
;					 (< 5)))

;(defn chega-em
;	 [hospital departamento pessoa]
;	 (if (cabe-na-fila? hospital departamento)
;			(update hospital departamento conj pessoa)
;			(throw (ex-info "Não cabe ninguém neste departamento.", {:paciente pessoa }))))

;(defn chega-em
;	 [hospital departamento pessoa]
;	 (if (cabe-na-fila? hospital departamento)
;			(update hospital departamento conj pessoa)
;			(throw (IllegalStateException. "Não cabe ninguém neste departamento."))))

; nao trata a exception, retorna nil
;(defn chega-em
;	 [hospital departamento pessoa]
;	 (if (cabe-na-fila? hospital departamento)
;			(update hospital departamento conj pessoa)))

; exemplo para extrair com ex-data
;(defn chega-em
;	 [hospital departamento pessoa]
;	 (if (cabe-na-fila? hospital departamento)
;			(update hospital departamento conj pessoa)
;			(throw (ex-info "Não cabe ninguém neste departamento.", {:paciente pessoa, :tipo :impossivel-colocar-pessoa-na-fila }))))

; funcao visivel apenas par este namespace, antigo chega-em funcao pura
;(defn- tenta-colocar-na-fila
;	 [hospital departamento pessoa]
;	 (if (cabe-na-fila? hospital departamento)
;			(update hospital departamento conj pessoa)))

; colocar o tratamento de erro nas funcoes colaterais e nao dentro das funcoes puras (antigo chega-em)
; novo chega-em que causa efeitos colaterais
; desta maneira consegue extrair o hospital e extrair o resultado se deu erro
;(defn chega-em
;	 [hospital departamento pessoa]
;	 (if-let [novo-hospital (tenta-colocar-na-fila hospital, departamento, pessoa)]
;			{:hospital novo-hospital, :resultado :sucesso}
;			{:hospital hospital, :resultado :impossivel-colocar-pessoa-na-fila}))

; antes de fazer swap chega-e vai ter que tratar o resultado
; ou extrair o hospital ou tratar :resultado
; nao dá para fugir de preocupacoes, se o resultado é para ser usado com atomos ou similares
; e ao mesmo tempo tratar erros
;(defn chega-em!
;	 [hospital departamento pessoa]
;	 (chega-em hospital departamento pessoa))

(defn chega-em
	[hospital departamento pessoa]
	(if (cabe-na-fila? hospital departamento)
		(update hospital departamento conj pessoa)
		(throw (ex-info "Não cabe ninguém neste departamento.", {:paciente pessoa}))))

; o pop e o peek depende da abstracao da estrutura de dados
(s/defn atende :- h.model/Hospital
	[hospital :- h.model/Hospital
	 departamento :- s/Keyword]
	(update hospital departamento pop))

(s/defn proxima :- h.model/PacienteID
	"Retorna o próximo paciente da fila"
	[hospital :- h.model/Hospital departamento :- s/Keyword]
	(-> hospital
			departamento
			peek))

(defn mesmo-tamanho?
	[hospital, outro-hospital, de, para]
	(= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
		 (+ (count (get hospital de)) (count (get hospital para))))
	)

(s/defn transfere :- h.model/Hospital
	"Transfere o próximo paciente da fila de para a fila para"
	[hospital :- h.model/Hospital, de :- s/Keyword, para :- s/Keyword]

	; muitas vezes essa programaçao voltada a contratos nao é usada
	;; é favorecido ifs, schemas, testes, etc
	; é opcional a execucao dos assertions erros em produçao, alguém pode desligar
	; mas para as bordas da aplicao e nos seus testes unitarios vc garante essa validaçao de verdade habilitado
	{:pre  [(contains? hospital de), (contains? hospital para)]
	 :post [(mesmo-tamanho? hospital % de para)]}

	(let [pessoa (proxima hospital de)]
		(-> hospital
				(atende de)
				(chega-em para pessoa))))

(defn teste
	[x]
	{:pre [(not (nil? x))]}
	(println "teste 123" x))

;(teste 15)
;(teste nil)
;(transfere {:espera h.model/fila-vazia}, :nao-existe, :raio-x)

; complexidade ciclomática para testes unitários, varios caminhos que uma funcao tem
; funcoes escondem complexidade ciclomática, esconde as condicoes de fazer uma coisa, 2, 3, 4..
; uma funcao pode gerar por exemplo 4, 8 caminhos diferentes só em uma única funcao
; as condicoes do if esconde uma complexidade 2, mas a condicao de um if tmb tem complexidades escondidas
; o >, o <, <=, diversos condicoes ciclomáticas escondidas
; isso cresce muito rápido, pensou em todos os casos de teste quando escreveu um teste para uma funçao?
; humanamente é dificil pensar em todas as bordas, e todas as categorias de testes
; mesmo com a imutabilidade, garantir que uma funcao funciona isoladamente é diferente
; de garantir que duas funoes continuam funcionando em conjunto / uniao
; testar todos os casos matematicamente é humanamente impossivel
; em um checklist fazer listar de maneira sistemica, todos os caminhos possiveis
; de input que tenho geram vários caminhos diferentes, eu testei todos eles?
; isso é a complexidade ciclomática e nao aquele teste de cobertura de linhas
; existe ferramentas para calcular essa complexidade ciclomática
; funçoes, condicoes dos ifs, etc escondem a complexidade ciclomática
; é interessante testar as bordas
; os testes servem para nos lembrar que determinada coisa funciona daquele jeito,
; precisamos dos testes de integracao
; inteligencia artificial para vasculhar / explorar bugs, e comportamentos indesejados dentro do sistema
; garantir que os parametros estao funcionando
; testes generativos que vai gerar testes para agente

