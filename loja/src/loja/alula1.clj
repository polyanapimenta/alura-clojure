(ns loja.alula1)

(println "----- Aula 01 -----")

; Coleções

;["Marco" "Heloisa" "Polyana" "Hyago"] - vetor
; {"Polyana" 37, "Paulo"39} - hashmap
; '(1 2 3 4 5) - lista ligada,
; [[0 1]] - enum, ou é uma coisa ou é outra
; #{} - conjunto nao existe a repetiçoes de elementos dentro desta coleção

; map
; reduce
; filter

; loop
; for

(def vetor ["Marco" "Junior" "Polyana" "Hyago" "Doidinho"])
(map println vetor)

(println (first vetor))                                     ; primeiro elemento do vetor
(println (first []))
(println (rest vetor))                                      ; trás o resto dos elementos do vetor
(println (rest []))                                         ; retorna lista vazio
(println (next vetor))
(println (next []))                                         ; retorna null

(println (seq []))                                          ; A sequencia de um vetor vazio é null
(println (seq [1 2 3 4]))

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do (funcao primeiro)
          (meu-mapa funcao (rest sequencia))))))


(meu-mapa println vetor)
(meu-mapa println ["Marco" false "Junior" "Polyana" "Hyago" "Doidinho"])
(meu-mapa println [])
(meu-mapa println nil)

; (meu-mapa println (range 100000)) ; StackOverflowError empilhamento muito extenso, espaço finito

; o for nao estoura a memória pq nao fica empilhando o i a dada recursão
; recur é transformado em um laço em tempo de execuçao

; Recursao de cauda, TAIL Recursion
(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do (funcao primeiro)
          (recur funcao (rest sequencia))))))

(meu-mapa println (range 100000))






















