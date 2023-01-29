(ns hospital.aula2
	 (:use clojure.pprint))

; equivalente a uma classe em java
(defrecord Paciente [id nome nascimento])

; Pascimente Plano de saude + plano de saude
; Pasciemtne Particular + 0

; caminho horripilante com provaveis problemas horriveis e tipos 2ˆn
;(defrecord PacientePlanoSaude Herda Paciente [plano])

; digitar nao eh o maior problema da nossa vida
; problema ter vários tipos
(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacientePlanoSaude [id, nome, nascimento, plano])

;(defn deve-assinar-pre-autorizacao?
;	 [paciente procedimento valor]
;	 (if (= PacienteParticular (type paciente))
;			(>= valor 50)
;			;assumindo que existe os dois tipos
;			(if (= PacientePlanoSaude (type paciente))
;				 (let [plano (get paciente :plano)]
;						(not (some #(= % procedimento) plano)))
;				 true)))

; implementar uma funcao baseada em um tipo, comportamento de acordo com o tipo
; equivalente a uma interface
(defprotocol Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor])
	 )

; EXTENDER UM TIPO E IMPLEMENTAR O PROTOCOLO COBRAVEL (METODO)
(extend-type PacienteParticular
	 Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor]
			(>= valor 50)))

(extend-type PacientePlanoSaude
	 Cobravel
	 (deve-assinar-pre-autorizacao? [paciente procedimento valor]
			(let [plano (:plano paciente)]
				 (not (some #(= % procedimento) plano)))))

; alternativa, implementando diretamente, quando define um record já pode diretamente falar que ele vai implementar um proctocolo, e tmb já implementar o protocolo
; dá para adicionar comportamento aos records depois que eles foram definidos
;(defrecord PacientePlanoSaude
;	 [id, nome, nascimento, plano]
;	 Cobravel
;	 (deve-assinar-pre-autorizacao? [paciente procedimento valor]
;			(let [plano (:plano paciente)]
;				 (not (some #(= % procedimento) plano)))))

; nao dá para usar contains em vetor, pq verifica o indice , e indice fica dependente da estrutura de dados, e vetor o indice é numerico
(let [particular (->PacienteParticular 15 "Guilherme" "12/02/1999")
			plano (->PacientePlanoSaude 15 "Guilherme" "12/02/1999" [:raio-x :ultrassom])]
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
	 (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
	 (pprint (deve-assinar-pre-autorizacao? plano :raio-x 4000))
	 (pprint (deve-assinar-pre-autorizacao? plano :coleta-sangue  4000))

	 )










































