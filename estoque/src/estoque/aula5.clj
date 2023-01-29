(ns estoque.aula5)

; HASH-MAPS E THREADING

(def estoque {"Mochila" 10 "Camiseta" 5})                   ; hashmap / objeto
(println estoque)

(println "Temos" (count estoque) "elementos")
(println "Chaves são:" (keys estoque))
(println "Valores são" (vals estoque))

; keyword
; :mochila

(def estoque {:mochila  10
							:camiseta 5})

(println (assoc estoque :cadeira 3))
(println estoque)

(println (assoc estoque :mochila 1 :poly 22))
(println estoque)

; update (hash-map keyword e a funcao)
(println (update estoque :mochila inc))

(defn tira-um
	 [valor]
	 (println "Tirando um de" valor)
	 (- valor 1))

(println (update estoque :mochila tira-um))

(println (update estoque :mochila #(- % 3)))

(println (dissoc estoque :mochila))

(def pedido {:mochila  {:quantidade 2, :preco 80, :taxa {:desconto 0}}
						 :camiseta {:quantidade 3, :preco 40}})

(println pedido)

(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10}))
(println pedido)

(println (pedido :mochila))                                 ; pode usar um mapa como funcao, se o pedido é nulo, dá um nullPointerException, raramente utilizado

(println (get pedido :mochila))                             ; pouco utilizado
(println (get pedido :cadeira))
(println (get pedido :cadeira {}))                          ; get com valor default

(println (:mochila pedido))                                 ; bastante utilizado | PIOR LEGIBILIDADE
(println (:cadeira pedido))                                 ; se a keyword for null ou nao existir NÃO dá problema
(println (:cadeira pedido {}))                              ; definindo valor default caso nao exista
(println (:quantidade (:mochila pedido)))                   ; parece uma destructure do JS | const { mochila : { quantidade } } = pedido

(println (update-in pedido [:mochila :quantidade] inc))
(println (update-in pedido [:mochila :taxa :desconto] inc))

; THREADING (ENCADEAMENTO DE CHAMADAS, PASSANDO FIO PELO TECIDO)
(println pedido)
(println (-> pedido :mochila :quantidade))                  ; pedido.get(mochila).get(quantidade) | MELHOR PARA LEGIBILIDADE, MAIS UTILIZADO

; THREADING FIRST
(-> pedido,,, :mochila                                      ; ,,, é para indicar a posicao do argumento,,, :quantidade                                             ; ,,, virgulas vira espaços em brancos,,, println)                                                ; depois que pega esse valor chama uma função

(def clientes {
							 :15 {
										:nome         "Polyana"
										:certificados ["Clojure" "Java" "JS"]}})

(println (-> clientes :15 :certificados count))