(ns hospital.aula4
	 (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento, situacao])
(defrecord PacientePlanoSaude [id, nome, nascimento, situacao, plano])

(defn nao-eh-urgente?
	 [paciente]
	 (not= :urgente (:situacao paciente :normal)))

(defprotocol Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor])
	 )

(extend-type PacienteParticular
	 Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor]
			(and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoSaude
	 Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor]
			(let [plano (:plano paciente)]
				 (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))

(multi-teste "Polyana")
;(multi-teste :guilherme) // tem que fazer a implementacao para keyword, criar um devmethod


; misturando keyword e classe como chave
(defn tipo-de-autorizador
	 [pedido]
	 (let [paciente (:paciente pedido)
(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :normal)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :normal [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
	 (pprint (deve-assinar-pre-autorizacao? plano :raio-x 4000))
	 (pprint (deve-assinar-pre-autorizacao? plano :coleta-sangue  4000)))

(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :urgente)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :urgente [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
	 (pprint (deve-assinar-pre-autorizacao? plano :raio-x 4000))
	 (pprint (deve-assinar-pre-autorizacao? plano :coleta-sangue  4000)))

; problema copy-paste entre diferentes tipos, abordar comportamentos em diferentes tipos cai no problema de exponenciacao de milhares de tipos
; polimorfismo através de tipos gera problema de exponenciaçao de multiplos tipos, gera mta copy-paste

(println "##########################")

(println "Funcao multipla")                                 ; mesmo problema de implementar o cod todo de copy-paste

(defmulti deve-assinar-pre-autorizacao-multi? class)

(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [paciente]
	 (println "Invocando paciente particular")
	 true)

(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoSaude [paciente]
	 (println "Invocando paciente plano")
	 false)

(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :urgente)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :urgente [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao-multi? particular))
	 (pprint (deve-assinar-pre-autorizacao-multi? plano)))


(defn minha-funcao [p]
	 (println p)
	 (class p))

; defmethod e o extend-type sao implementacoes dos metodos definidos nas interfaces
; defmulti é equivalente a uma interface, assim como defprotocol
(defmulti multi-teste minha-funcao)

(defmethod multi-teste java.lang.String [text]
	 (println "text", text)
	 true)

				 situacao (:situacao paciente)
				 urgencia? (= :urgente situacao)]
			(if urgencia?
				 :sempre-autorizado
				 (class paciente))))

(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [pedido]
	 false)

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
	 (>= (:valor pedido 0) 50))

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoSaude [pedido]
	 (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))


(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :urgente)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :urgente [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-sangue})))


(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :normal)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :normal [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-sangue})))


(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999" :normal)
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" :normal [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 40, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :raio-x})))








