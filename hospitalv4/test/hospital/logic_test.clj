(ns hospital.logic-test
	(:use clojure.pprint)
	(:require [clojure.test :refer :all]
						[hospital.logic :refer :all]
						[hospital.model :as h.model]
						[schema.core :as s])
	(:import (clojure.lang ExceptionInfo)))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test

	; boundary tests
	; exatamente na borda e one off (bem pertinho da borda). -1, +1, <=, >=, =

	; borda do zero
	(testing "Que cabe na fila vazia"
		(is (cabe-na-fila? {:espera []}, :espera)))               ; caso vazio

	; borda do limite
	(testing "Que não cabe na fila quando a fila está cheia"
		(is (not (cabe-na-fila? {:espera [10 2 35 4 50]}, :espera)))) ; caso cheio, teste de borda

	; one off da borda do limite pra cima
	(testing "Que não cabe na fila quando tem mais do que uma fila cheia"
		(is (not (cabe-na-fila? {:espera [1 23 3 46 5 60]}, :espera)))) ; teste mais do que cheio

	; dentro das bordas
	(testing "Que cabe na fila quando tem gente  mas nao está cheia"
		(is (cabe-na-fila? {:espera [13 2 31 45]}, :espera))
		(is (cabe-na-fila? {:espera [1 21]} :espera)))

	(testing "Que não cabe quando o departamento não existe"
		(is (not (cabe-na-fila? {:espera [11 22 34 4]}, :raio-x)))))


(deftest chega-em-test
	(let [hospital-cheio {:espera [1 35 42 64 21]}]

		(testing "Aceita pessoas enquanto cabem pessoas na fila"
			; classica implementaçao ruim, pois testa que escrevemos o que escrevemos
			; isto é, testa que eramos o que eramos. E que acertamos o que acertamos
			; no fim, nao está testando nada!
			;(is (= (update {:espera [1 32 13 41 5]}, :espera, conj 5)
			;			 (chega-em {:espera [1 32 13 41 5]}, :espera, 5)))

			(is (= {:espera [1 32 13 41 5]}
						 (chega-em {:espera [1 32 13 41]}, :espera, 5)))

			(is (= {:espera [1 7 5]}
						 (chega-em {:espera [1 7]} :espera, 5)))              ; é importante fazer testes não sequenciais

			;(is (= {:hospital hospital-cheio, :resultado :sucesso}
			;			 (chega-em {:espera [1 35 42 64]}, :espera, 21)))
			;
			;(is (= {:hospital {:espera [1 7 5]}, :resultado :sucesso}
			;			 (chega-em {:espera [1 7]} :espera, 5)))
			)

		(testing "Não aceita quando não cabe na fila"
			; verificando que uma exception foi jogada
			; codigo clássico horrível. usamos uma exception GENERICA
			; qualquer outro erro generico vai jogar essa exceptiom, e vamos achar que deu certo o que deu errado
			(is (thrown? ExceptionInfo
									 (chega-em {:espera [1 35 42 64 21]}, :espera 76)))

			; clojure.lang.ExceptionInfo: Não cabe ninguém neste departamento.
			; {:paciente 5}

			; mesmo que eu escolha uma exception do genero, é perigoso!
			;(is (thrown? IllegalStateException
			;						 (chega-em {:espera [1 35 42 64 21]}, :espera 76)))

			; problema! strings de texto solto são super fáceis de quebrar ou de alguém alterar
			;(is (thrown-with-msg? clojure.lang.ExceptionInfo "Não cabe ninguém neste departamento."
			;						 (chega-em {:espera [1 35 42 64 21]}, :espera 76)))


			; nao indicado pq os atom retornam nil tmb quando o swap os atualizam
			; teria que trabalhar em outro ponto a condiçao de erro
			;(is (nil? (chega-em {:espera [1 35 42 64 21]}, :espera 76)))

			; outra maneira de testar
			; onde ao invés de como java, utilizar o tipo da exception para estender
			; o tipo (outro tipo) de erro que ocorreu, estou usando os dados da exception para isso
			; menos sensível que a mensagem de erro (mesmo que usasse regex)
			; mas ainda é uma validaçao trabalhosa
			;(is (try
			;			 (chega-em {:espera [1 35 42 64 21]}, :espera 76)
			;			 (catch clojure.lang.ExceptionInfo e
			;					(= :impossivel-colocar-pessoa-na-fila (:tipo (ex-data e))))))

			;(is (= {:hospital hospital-cheio, :resultado :impossivel-colocar-pessoa-na-fila}
			;			 (chega-em hospital-cheio, :espera, 76)))
			)))

(deftest transfere-test
	(testing "Aceita pessoas se cabe"
		(let [hospital-original {:espera (conj h.model/fila-vazia "5"),
														 :raio-x h.model/fila-vazia}]

			(is (= {:espera h.model/fila-vazia,
							:raio-x (conj h.model/fila-vazia "5")}

						 (transfere hospital-original :espera :raio-x))))

		(let [hospital-original {:espera (conj h.model/fila-vazia "51" "5"),
														 :raio-x (conj h.model/fila-vazia "13")}]

			(is (= {:espera (conj h.model/fila-vazia "5"),
							:raio-x (conj h.model/fila-vazia "13" "51")}

						 (transfere hospital-original :espera :raio-x)))))

	(testing "Recusa pessoas se não cabe"
		(let [hospital-cheio {:espera (conj h.model/fila-vazia "5"),
													:raio-x (conj h.model/fila-vazia "1" "23" "46" "11" "99")}]

			(is (thrown? ExceptionInfo
									 (transfere hospital-cheio :espera :raio-x)))))

	; será que faz sentido eu garantir que o schema está do outro lado?
	; lembrando que este teste nao garante exatamente isso
	; só garante o erro de caso o hospital seja nil
	; .. é obvio que ninguem vai apagar um teste automatizado do nada, vai sentir o peso de apagar
	; mas nao é obvio que ninguem vai apagar uma restriçao de um esquema, porque naquele instante
	; pode fazer sentido na cabeca da pessoa que nao entende o dominio nem as restricoes do schema
	; por isso, tenho uma tendencia de criar testes do genero para validar que algo nao foi removido
	; em situaçoes críticas.
	(testing "Não pode invocar transferência sem hospital"
		(is (thrown? ExceptionInfo (transfere nil :espera :raio-x))))

	(testing "Testando condiçoes obrigatórias"
		(let [hospital {:espera (conj h.model/fila-vazia "51" "5"), :raio-x (conj h.model/fila-vazia "13")}]
			(is (thrown? AssertionError (transfere hospital :nao-existe :raio-x)))
			(is (thrown? AssertionError (transfere hospital :espera :nao-existe)))
			)
		)

	(testing "teste"
		(is (thrown? AssertionError (teste nil))))
	)

