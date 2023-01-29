(ns hospital.aula4
	 (:use [clojure pprint])
	 (:require [hospital.logic :as h.logic]
						 [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
	 (swap! hospital h.logic/chega-em :espera pessoa)          ; swap é como se fosse uma transasao, se nao conseguir retenta para nao ter mais o problema da concorrencia, alguem já alterou o valor, retenta para obter esse valor novo.. existe limites nos retries, quebrou a atomicidade retenta, nao precisa se preocupar com mais com lock
	 (println "após inserir" pessoa))

(defn simula-um-dia-em-paralelo-com-map
	 "#Simula um dia em paralelo"
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas ["111", "222", "333", "444", "555", "666"]]

			; nao executa o map (lazy) pq nao está utilizando o valor de retorno do map (nil, nil, nil, nil, nil, nil)
			(map #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)

			(.start (Thread. (fn [] (Thread/sleep 8000)
													(pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-map)

(defn simula-um-dia-em-paralelo-com-mapv
	 "#Simula um dia em paralelo"
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas ["111", "222", "333", "444", "555", "666"]]

			; mapv (eager) vai forçar a execuçao mesmo nao fazendo nada com o retorno do map (nil, nil, nil, nil, nil)
			(mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)

			(.start (Thread. (fn [] (Thread/sleep 8000)
													(pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-mapv)

(defn simula-um-dia-em-paralelo-com-mapv-refatorada
	 "#Simula um dia em paralelo"
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas ["111", "222", "333", "444", "555", "666"]
				 starta-thread-de-chegada #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]

			; mapv para forçar a execiçao
			(mapv starta-thread-de-chegada pessoas)

			(.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-mapv-refatorada)

(defn starta-thread-de-chegada
	 ([hospital]
		(fn [pessoa] (starta-thread-de-chegada hospital pessoa)))

	 ([hospital pessoa]
		(.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida
	 "#Simula um dia em paralelo"
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas ["111", "222", "333", "444", "555", "666"]]

			; mapv para forçar a execiçao
			(mapv (starta-thread-de-chegada hospital) pessoas)

			(.start (Thread. (fn [] (Thread/sleep 4000) (pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-mapv-extraida)


(defn starta-thread-de-chegada
	 ([hospital pessoa]
		(.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-partial
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas ["111", "222", "333", "444", "555", "666"]
				 starta (partial starta-thread-de-chegada hospital)]

			; mapv para forçar a execiçao
			(mapv starta pessoas)

			(.start (Thread. (fn [] (Thread/sleep 4000) (pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-mapv-partial)

(defn simula-um-dia-em-paralelo-com-doseq
	 "#Realmente preocupado em executar os elementos da minha sequencia"
	 []

	 (let [hospital (atom (h.model/novo-hospital))
				 pessoas (range 6)]

			(doseq [pessoa pessoas]
				 (starta-thread-de-chegada hospital pessoa))

			(.start (Thread. (fn [] (Thread/sleep 4000)
													(pprint hospital)))))
	 )

;(simula-um-dia-em-paralelo-com-doseq)

(defn starta-thread-de-chegada
	 ([hospital pessoa]
		(.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-doseq
	 "#Realmente preocupado em executar N vezes"
	 []

	 (let [hospital (atom (h.model/novo-hospital))]

			; dotimes e doseq causam efeitos colaterais, tudo de retorna nil causa efeito colateral, ou não, ou o cód é inútil e tem q ser removido
			(dotimes [pessoa 6]
				 (starta-thread-de-chegada! hospital pessoa))

			(.start (Thread. (fn [] (Thread/sleep 4000)
													(pprint hospital)))))
	 )

(simula-um-dia-em-paralelo-com-doseq)











