(ns hospital.aula4
	 (:use clojure.pprint)
	 (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))

(def Plano [s/Keyword])

(def Paciente
	 {:id                          PosInt,
		:nome                        s/Str,
		:plano                       Plano,
		(s/optional-key :nascimento) s/Str})

(def Pacientes
	 {PosInt Paciente})

(pprint (s/validate Pacientes {}))

(pprint (s/validate Paciente {:id 15, :nome "Polyana", :plano [:raio-x, :ultrassom]}))
(pprint (s/validate Paciente {:id 15, :nome "Polyana", :plano [], :nascimento "12/04/1999"}))

(let [guilherme {:id 15, :nome "Guilherme", :plano [:raio-x, :ultrassom]}
			daniela {:id 20, :nome "Daniela", :plano []}]

	 (pprint (s/validate Pacientes {15 guilherme}))
	 (pprint (s/validate Pacientes {15 guilherme, 20 daniela})))

(def Visita [s/Str])

(def Visitas
	 {PosInt Visita})

; nil nao é um id inteiro positivo
; ganhamos a garantia de ter um id válido
;(pprint (s/validate Paciente {:id nil, :nome "GUilherme", :plano []}))

; removi o if e o throw pq  o shcema garantiu a existencia do id e a validade do id
; se a validacao estiver ativa!
; quem invoca essa funcao tem que seguir o contrato do schema
(s/defn adiciona-paciente :- Pacientes
	 [pacientes :- Pacientes, paciente :- Paciente]
	 (let [id (:id paciente)]
			(assoc pacientes id paciente)))

(s/defn adiciona-visita :- Visitas
	 [visitas :- Visitas, paciente :- PosInt, novas-visitas :- Visita]
	 (if (contains? visitas paciente)
			(update visitas paciente concat novas-visitas)
			(assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-de-paciente [visitas :- Visitas, paciente :- PosInt]
	 (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
	 (let [guilherme {:id 15 :nome "Guilherme", :plano []}
				 daniela {:id 20 :nome "Daniela", :plano []}
				 paulo {:id 33 :nome "Paulo", :plano []}

				 pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

				 visitas {}
				 visitas (adiciona-visita visitas 15 ["01/01/2019"])
				 visitas (adiciona-visita visitas 20 ["01/02/2019", "01/01/2020"])
				 visitas (adiciona-visita visitas 33 ["01/01/2019"])
				 ]

			(pprint pacientes)
			(pprint visitas)

			; está utilizando em diversos lugares p simbolo paciente com significados diferentes;
			(imprime-relatorio-de-paciente visitas 20)))

(testa-uso-de-pacientes)