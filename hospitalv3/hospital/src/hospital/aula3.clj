(ns hospital.aula3
	 (:use clojure.pprint)
	 (:require [schema.core :as s])
	 (:import (javax.swing.plaf.basic BasicSplitPaneDivider$MouseHandler)))

(s/set-fn-validation! true)

(def PostInt (s/pred pos-int? 'inteiro-positivo))

(def Paciente
	 {:id PostInt, :nome s/Str})

(s/defn novo-paciente :- Paciente
	 [id :- PostInt
		nome :- s/Str]
	 {:id id, :nome nome})

(pprint (novo-paciente 15 "Polyana"))

(defn maior-ou-igual-a-zero? [x] (>= x 0))

(def ValorFinanceiro (s/constrained s/Num maior-ou-igual-a-zero?))

(def Pedido
	 {:paciente     Paciente
		:valor        ValorFinanceiro
		:procedimento s/Keyword})

(s/defn novo-pedido :- Pedido
	 [paciente :- Paciente,
		valor :- ValorFinanceiro,
		procedimento :- s/Keyword]
	 {:paciente paciente, :valor valor, :procedimento procedimento})

(pprint (novo-pedido (novo-paciente 15, "Guilherme"), 15.53, :raio-x))
;(pprint (novo-pedido (novo-paciente 15, "Guilherme"), -15.53, :raio-x))

(def Numeros [s/Num])
(pprint (s/validate Numeros [15]))
(pprint (s/validate Numeros [15,13]))
(pprint (s/validate Numeros [15,13,123,45,677,88]))
(pprint (s/validate Numeros [0]))
(pprint (s/validate Numeros []))

; nao é um vetor de números
;(pprint (s/validate Numeros [nil]))

; só que nil é considerado que pode ser um vetor de numeros vazio
(pprint (s/validate Numeros nil))

; nil não é s/Num
; (pprint (s/validate s/Num nil))

;nil é [s/Num]
(pprint (s/validate [s/Num] nil))

(def Plano [s/Keyword])

(pprint (s/validate Plano [:raio-x]))

(def Paciente
	 {:id PostInt,
		:nome s/Str,
		:plano Plano})

(pprint (s/validate Paciente {
															:id 15,
															:nome "Polyana",
															:plano [:raio-x, :ultrassom]
															}))


(pprint (s/validate Paciente {
															:id 15,
															:nome "Polyana",
															:plano [:raio-x]
															}))

(pprint (s/validate Paciente {
															:id 15,
															:nome "Polyana",
															:plano []
															}))

(pprint (s/validate Paciente {
															:id 15,
															:nome "Polyana",
															:plano nil
															}))

; plano é uma keyword obrigatoria, nao pode faltar, mas ela pode ter um valor vazio no mapa :plano nil
(pprint (s/validate Paciente {
															:id 15,
															:nome "Polyana"
															}))