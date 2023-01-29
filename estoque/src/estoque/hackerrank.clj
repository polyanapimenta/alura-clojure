(ns estoque.hackerrank)

(println "###########")
(println "HACKERRANK")
(println "###########")

(println "----------------------------")

(defn hello-word-n-times
	 "Output N lines, each containing 'Hello World'"
	 [n]
	 (let [word "Hello World"]
			(dotimes [_ n]
				 (println word))
			(println "n =" n)))

(def n (rand-int 50))
(hello-word-n-times n)


(println "----------------------------")

(def clone-num-list
	 (fn
			[n num]
			(repeat n num)))

;(println (clone-num-list 1 3))

(def clone-array-elements
	 "Output each element of the original list  times, each on a separate line.
	 You have to return the list/vector/array of  integers. The relative positions
	 of the values should be the same as the original list provided in the input."
	 (fn [num lst]
			(flatten (map #(repeat num %) lst))))

(println (clone-array-elements 3 [1, 2, 3, 4, 5]))

(println "----------------------------")

(def filter-elements-less-than-limit
	 "Print all the integers from the array that are less than the given upper
	 limit  in value on separate lines. The sequence should be the same as it
	 was in the original array."
	 (fn
			[delim lst]
			(filter #(> delim %) lst)))

(println (filter-elements-less-than-limit 3 [90, 54, 34, 2, 1, 0, -1, -40, -7, -3]))


