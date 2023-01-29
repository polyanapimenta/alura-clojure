(ns loja.aula2)

(def vetor ["Marco" "Junior" "Polyana" "Hyago" "Doidinho"])

(defn conta
	[total-ate-agora elementos]
	(if (next elementos)
		(recur (inc total-ate-agora) (next elementos))
		total-ate-agora))

(println (conta 0 vetor))
(println (conta 0 []))

(defn conta
	([elementos]
	 (conta 0 elementos))

	([total-ate-agora elementos]
	 (if (seq elementos)
		 (recur (inc total-ate-agora) (next elementos))
		 total-ate-agora)))

(println (conta 0 vetor))
(println (conta 0 []))

(defn minha-funcao
	([parametro1] (println "p1" parametro1))
	([parametro1 parametro2] (println "p2" parametro1 parametro2)))

(minha-funcao 1)
(minha-funcao 1 2)

; loop, while, for

(defn conta
	[elementos]
	(loop [total-ate-agora 0
			 elementos-restantes elementos]
		(if (seq elementos-restantes)
			(recur (inc total-ate-agora) (next elementos-restantes))
			total-ate-agora)
		))

(println (conta vetor))
(println (conta []))


















