(ns hospital.logic
	 (:use [clojure.pprint]))

(defn cabe-na-fila?
	 [hospital departamento]
	 (-> hospital
			 (get departamento)
			 count
			 (< 5)))

; funcao pura é aquela que retorna o mesmo resultado sempre
(defn chega-em
	 [hospital departamento pessoa]
	 (println "Tentando adicionar pessoa" pessoa)
	 ;(println "cabe-na-fila?" (cabe-na-fila? hospital departamento))
	 (if (cabe-na-fila? hospital departamento)
			(do
				 (println "Dando o update" pessoa)
				 (update hospital departamento conj pessoa))
			(throw
				 (ex-info "Fila já está cheia"
									{:tentando-adicionar pessoa}))))

; funcao nao pura pq tem o random que altera o resultado esperado da funcao, nao sendo igual a cada invocacao
(defn chega-em-pausado
	 [hospital departamento pessoa]
	 ;(println "cabe-na-fila?" (cabe-na-fila? hospital departamento))

	 (if (cabe-na-fila? hospital departamento)
			(do
				 (Thread/sleep (* (rand) 2000))
				 (update hospital departamento conj pessoa))
			(throw
				 (ex-info "Fila já está cheia"
									{:tentando-adicionar pessoa}))))

; funcao nao pura que usa random e altera o estado do ramdom e loga
(defn chega-em-pausado-logando
	 [hospital departamento pessoa]
	 (println "Tentando adicionar pessoa" pessoa)
	 ;(println "cabe-na-fila?" (cabe-na-fila? hospital departamento))

	 (if (cabe-na-fila? hospital departamento)
			(do
				 (Thread/sleep (* (rand) 2000))
				 (println "Dando o update" pessoa)
				 (update hospital departamento conj pessoa))

			(throw
				 (ex-info "Fila já está cheia"
									{:tentando-adicionar pessoa}))))

(defn atende
	 [hospital departamento]
	 (update hospital departamento pop))

(defn atende-completo
	 [hospital departamento]
	 {:paciente (update hospital departamento peek)
		:hospital (update hospital departamento pop)})

; executar as duas funcoes, nao é uma depois a outra é a invocaçao de duas funcoes
; juxt aplica as funções isoladamente com o mesmo valor inicial. comp compõe, pq ele executa uma funcao e executa a segunda com o valor da primeira funcao retornado.

; comp recebe uma sequência de funções e retorna uma função capaz de invocar elas em sequência, da última para a primeira. juxt aplica as funções isoladamente ao mesmo valor.

(defn atende-completo-que-chama-ambos
	 [hospital departamento]
	 (let [fila (get hospital departamento)
				 peek-pop (juxt peek pop)
				 [pessoa fila-atualizada] (peek-pop fila)
				 hospital-atualizado (update hospital assoc departamento fila-atualizada)]

			{:paciente pessoa
			 :hospital hospital-atualizado}))

(defn proxima
	 "Retorna o próximo paciente da fila"
	 [hospital departamento]
	 (-> hospital
			 departamento
			 peek))

(defn transfere
	 "Transfere o próximo paciente da fila de para a fila para"
	 [hospital de para]
	 (let [pessoa (proxima hospital de)]
			(-> hospital
					(atende de)
					(chega-em para pessoa))))


