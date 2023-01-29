(ns hospital.model
	(:require [schema.core :as s])
	(:import (clojure.lang PersistentQueue)))

(def fila-vazia PersistentQueue/EMPTY)

(defn novo-hospital []
	{:espera       fila-vazia
	 :laboratorio1 fila-vazia
	 :laboratorio2 fila-vazia
	 :laboratorio3 fila-vazia})

(s/def PacienteID s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital {s/Keyword Departamento})

;(s/validate PacienteID "Guilherme")
;(s/validate PacienteID 15)
;(s/validate Departamento (conj fila-vazia "Polyana" "Hyago"))
;(s/validate Hospital {:espera (conj fila-vazia "Polyana" "Hyago")})