(ns custom-functions.core
  (:gen-class))

;; Defintion of `Sequence`: an ordered collection of elements

;; The function `map`'s essential behaviour is to derive a new value `y` from
;; an existing sequence `x`

;; Using a function `f` I can show this as follows: y1 = f(x1) ... yn = f(xn)

;; Any data structure in Clojure that responds to the functions `first` `rest`
;; and `cons` is considered to implement the sequence abstraction

;; I will implement a simple linked list of nodes to show understanding
(def node-1 {:value "Mary" :next: node-2})
(def node-2 {:value "had" :next: node-3})
(def node-3 {:value "a" :next: node-4})
(def node-4 {:value "little" :next: node-5})
(def node-5 {:value "lamb" :next: null})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
