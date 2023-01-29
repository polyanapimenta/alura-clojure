(ns hospital.aula1
	 (:use clojure.pprint)
	 (:require [schema.core :as s]))

(defn adiciona-paciente
	 [pacientes paciente]
	 (if-let [id (:id paciente)]
			(assoc pacientes id paciente)
			(throw
				 (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn adiciona-visita
	 [visitas, paciente, novas-visitas]
	 (if (contains? visitas paciente)
			(update visitas paciente concat novas-visitas)
			(assoc visitas paciente novas-visitas)))

(s/defn imprime-relatorio-de-paciente [visitas, paciente :- Long]
	 (println "Visitas do paciente" paciente "são" (get visitas paciente)))

(defn testa-uso-de-pacientes []
	 (let [guilherme {:id 15 :nome "Guilherme"}
				 daniela {:id 20 :nome "Daniela"}
				 paulo {:id 33 :nome "Paulo"}

				 pacientes (reduce adiciona-paciente {} [guilherme, daniela, paulo])

				 visitas {}
				 visitas (adiciona-visita visitas 15 ["01/01/2019"])
				 visitas (adiciona-visita visitas 20 ["01/02/2019", "01/01/2020"])
				 visitas (adiciona-visita visitas 33 ["01/01/2019"])
				 ]

			(pprint pacientes)
			(pprint visitas)

			; está utilizando em diversos lugares p simbolo paciente com significados diferentes;
			(imprime-relatorio-de-paciente visitas guilherme)
			))

(testa-uso-de-pacientes)

(pprint (s/validate Long 15))
;(pprint (s/validate Long "Polyana"))
;(pprint (s/validate Long [15,13]))


(s/set-fn-validation! true)

(s/defn teste-simples
	 [x :- Long]
	 (println x))

(teste-simples 15)
;(teste-simples "Polyana")

(s/defn novo-paciente
	 [id :- Long, nome :- s/Str]
	 {:id id, :nome nome})

(pprint (novo-paciente 15 "Guilherme"))
;(pprint (novo-paciente "Guilherme" 15))



















