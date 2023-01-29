(ns estoque.aula3)

; FUNÇÕES ANÔNIMAS E LAMBDAS

; PREDICATE
(defn aplica-desconto?
	 [valor-bruto]
	 (when (> valor-bruto 100)
			true))

; PREDICATE
(defn aplica-desconto?
	 [valor-bruto]
	 (if (> valor-bruto 100)
			true
			false))

(defn mais-caro-que-100?
	 [valor-bruto]
	 (println "Invocaçao de mais-caro-que-100")
	 (> valor-bruto 100))

(println "##########################")
(println "Invocaçao de valor-descontado")

(defn valor-descontado
	 "Retorna o valor com desconto de 10% se deve aplicar desconto."
	 [aplica? valor-bruto]
	 (if (aplica? valor-bruto)
			(let [taxa-de-desconto (/ 10 100)
						desconto (* valor-bruto taxa-de-desconto)]
				 (println "Calculando desconto de " desconto)
				 (- valor-bruto desconto))
			valor-bruto))

(println "##########################")
(println "Funçao como parametro")

(println
	 (valor-descontado mais-caro-que-100? 1000))

(println
	 (valor-descontado mais-caro-que-100? 100))

(println "##########################")
(println "Aplica desconto?")
(println
	 (aplica-desconto? 1000))

(println
	 (aplica-desconto? 100))

(println "##########################")
(println "Funçao sem nome anonima")
(println
	 (valor-descontado
			(fn [valor-bruto] (> valor-bruto 100))
			1000))

(println
	 (valor-descontado
			(fn [valor-bruto] (> valor-bruto 100))
			100))

(println "##########################")
(println "Mágica da abreviacao das funcoes anonimas, vira funcao lambda")
(println
	 (valor-descontado
			#(> %1 100) 1000))

(println
	 (valor-descontado
			#(> %1 100) 100))

(println
	 (valor-descontado
			#(> % 100) 1000))
(println
	 (valor-descontado
			#(> % 100) 100))

(println "##########################")
(prontln "definindo uma funcao anonima e funcao lambda em simbolo")
(def mais-caro-que-100?
	 (fn [valor-bruto]
			(> valor-bruto 100)))

(def mais-caro-que-100?
	 #(> % 100))

(println
	 (valor-descontado mais-caro-que-100? 1000))

(println
	 (valor-descontado mais-caro-que-100? 100))

(println "##########################")
(println "Tipos dos simbolos")
(println
	 (class
			(valor-descontado mais-caro-que-100? 1000)))

(println
	 (class
			(valor-descontado mais-caro-que-100? 100)))

(println "##########################")
































