(ns hospital.core
	 (:use clojure.pprint)
	 (:require [hospital.model :as h.model]))

; fila de espera
; fila lab1
; fila lab2
; fila lab3

(let [hospital (h.model/novo-hospital)]
	 (pprint hospital))

(pprint h.model/fila-vazia)