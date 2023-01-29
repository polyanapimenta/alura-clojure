(ns hospital.aula5
	 (:use clojure.pprint))

(defn tipo-de-autorizador
	 [pedido]
	 (let [paciente (:paciente pedido)
				 situacao (:situacao paciente)]

			(cond (= :urgente situacao) :sempre-autorizado
						(contains? paciente :plano) :plano-de-saude
						:else :credito-minimo)))

(defmulti deve-assinar-pre-autorizacao? tipo-de-autorizador)

 (defmethod deve-assinar-pre-autorizacao? :sempre-autorizado [pedido]
		false)

(defmethod deve-assinar-pre-autorizacao? :plano-de-saude [pedido]
	 (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(defmethod deve-assinar-pre-autorizacao? :credito-minimo [pedido]
	 (>= (:valor pedido 0) 50))

(let [particular {:id 15, :nome "Polyana", :nascimento "12/03/1998", :situacao :urgente}
			plano {:id 12, :nome "Hyago", :nascimento "23/05/1995", :situacao :urgente, :plano [:raio-x, :ultrassom]}]
	 (pprint (deve-assinar-pre-autorizacao? {:paciente particular, :valor 1000, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao? {:paciente plano, :valor 1000, :procedimento :coleta-sangue})))

(let [particular {:id 15, :nome "Polyana", :nascimento "12/03/1998", :situacao :normal}
			plano {:id 12, :nome "Hyago", :nascimento "23/05/1995", :situacao :normal, :plano [:raio-x, :ultrassom]}]
	 (pprint (deve-assinar-pre-autorizacao? {:paciente particular, :valor 1000, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao? {:paciente plano, :valor 1000, :procedimento :coleta-sangue})))

(let [particular {:id 15, :nome "Polyana", :nascimento "12/03/1998", :situacao :normal}
			plano {:id 12, :nome "Hyago", :nascimento "23/05/1995", :situacao :normal, :plano [:raio-x, :ultrassom]}]
	 (pprint (deve-assinar-pre-autorizacao? {:paciente particular, :valor 40, :procedimento :coleta-sangue}))
	 (pprint (deve-assinar-pre-autorizacao? {:paciente plano, :valor 1000, :procedimento :raio-x})))